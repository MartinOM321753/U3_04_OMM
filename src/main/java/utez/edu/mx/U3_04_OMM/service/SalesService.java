package utez.edu.mx.U3_04_OMM.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import utez.edu.mx.U3_04_OMM.Config.ApiResponse;
import utez.edu.mx.U3_04_OMM.DTO.SalesDto;
import utez.edu.mx.U3_04_OMM.model.Almacen;
import utez.edu.mx.U3_04_OMM.model.Cliente;
import utez.edu.mx.U3_04_OMM.model.Sales;
import utez.edu.mx.U3_04_OMM.repository.AlmacenRepository;
import utez.edu.mx.U3_04_OMM.repository.ClienteRepository;
import utez.edu.mx.U3_04_OMM.repository.SalesRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SalesService {

    private final SalesRepository salesRepository;
    private final ClienteRepository clienteRepository;
    private final AlmacenRepository almacenRepository;

    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<ApiResponse> create(SalesDto dto) {
        try {
            Optional<Cliente> cliente = clienteRepository.findById(dto.getIdCliente());
            if (cliente.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(null, HttpStatus.NOT_FOUND, "Cliente no encontrado"));
            }

            Optional<Almacen> almacenOpt = almacenRepository.findById(dto.getIdAlmacen());
            if (almacenOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(null, HttpStatus.NOT_FOUND, "Almacén no encontrado"));
            }

            Almacen almacen = almacenOpt.get();
            if (!almacen.getStatus()) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(null, HttpStatus.BAD_REQUEST, "El almacén ya está ocupado"));
            }

            if (dto.getTipo().equals("RENTA") && dto.getFechaFin() == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(null, HttpStatus.BAD_REQUEST, "La fecha de fin es obligatoria para rentas"));
            }

            // Marcar almacen como ocupado
            almacen.setStatus(false);
            almacenRepository.saveAndFlush(almacen);

            Sales venta = new Sales();
            venta.setPrecio(dto.getPrecio());
            venta.setFechaVenta(dto.getFechaVenta());
            venta.setFechaFin(dto.getFechaFin());
            venta.setTipo(dto.getTipo());
            venta.setCliente(cliente.get());
            venta.setAlmacen(almacen);

            return ResponseEntity.ok(new ApiResponse(salesRepository.save(venta), HttpStatus.OK, "Venta/Renta creada correctamente"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(null, HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar la venta/renta"));
        }
    }


    // Obtener todas las ventas/rentas
    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<ApiResponse> findAll() {
        List<Sales> list = salesRepository.findAll();
        return ResponseEntity.ok(new ApiResponse(list, HttpStatus.OK, ""));
    }

    // Obtener por ID
    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<ApiResponse> findById(Long id) {
        return salesRepository.findById(id)
                .map(sales -> ResponseEntity.ok(new ApiResponse(sales, HttpStatus.OK, "")))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(null, HttpStatus.NOT_FOUND, "Venta/Renta no encontrada")));
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<ApiResponse> update(Long id, SalesDto dto) {
        Optional<Sales> found = salesRepository.findById(id);
        if (found.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(null, HttpStatus.NOT_FOUND, "Venta/Renta no encontrada"));
        }

        Optional<Cliente> cliente = clienteRepository.findById(dto.getIdCliente());
        if (cliente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(null, HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        }

        Optional<Almacen> almacen = almacenRepository.findById(dto.getIdAlmacen());
        if (almacen.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(null, HttpStatus.NOT_FOUND, "Almacén no encontrado"));
        }

        if (dto.getTipo().equals("RENTA") && dto.getFechaFin() == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(null, HttpStatus.BAD_REQUEST, "La fecha de fin es obligatoria para rentas"));
        }

        Sales venta = found.get();
        venta.setPrecio(dto.getPrecio());
        venta.setFechaVenta(dto.getFechaVenta());
        venta.setFechaFin(dto.getFechaFin());
        venta.setTipo(dto.getTipo());
        venta.setCliente(cliente.get());
        venta.setAlmacen(almacen.get());

        return ResponseEntity.ok(new ApiResponse(salesRepository.save(venta), HttpStatus.OK, "Venta/Renta actualizada correctamente"));
    }

    // Eliminar venta/renta
    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<ApiResponse> delete(Long id) {
        Optional<Sales> venta = salesRepository.findById(id);
        if (venta.isPresent()) {
            salesRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse(null, HttpStatus.OK, "Eliminada correctamente"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(null, HttpStatus.NOT_FOUND, "Venta/Renta no encontrada"));
        }
    }
}
