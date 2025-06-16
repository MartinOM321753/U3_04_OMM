package utez.edu.mx.U3_04_OMM.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import utez.edu.mx.U3_04_OMM.Config.ApiResponse;
import utez.edu.mx.U3_04_OMM.model.Almacen;
import utez.edu.mx.U3_04_OMM.repository.AlmacenRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AlmacenService {

    private final AlmacenRepository almacenRepository;

    @Transactional(rollbackOn = Exception.class)
    public List<Almacen> findAll() {
        return almacenRepository.findAll();
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<ApiResponse> save(Almacen almacen) {
        try {
            Optional<Almacen> findAlmacen = almacenRepository.findById(almacen.getId());
            if (findAlmacen.isPresent()) {
                return new ResponseEntity<>(new ApiResponse("", HttpStatus.CONFLICT, "El almacén ya existe"), HttpStatus.CONFLICT);
            }
            almacen.setFechaRegistro(LocalDate.now());
            return new ResponseEntity<>(new ApiResponse(almacenRepository.save(almacen), HttpStatus.OK, ""), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse("", HttpStatus.INTERNAL_SERVER_ERROR, "Algo salió mal"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<ApiResponse> findById(Long id) {
        Optional<Almacen> almacen = almacenRepository.findById(id);
        if (almacen.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(almacen.get(), HttpStatus.OK, ""), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse("", HttpStatus.NOT_FOUND, "Almacén no encontrado"), HttpStatus.NOT_FOUND);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<ApiResponse> update(Long id, Almacen almacen) {
        Optional<Almacen> findAlmacen = almacenRepository.findById(id);
        if (findAlmacen.isPresent()) {
            Almacen actualizado = findAlmacen.get();
            actualizado.setClave(almacen.getClave());
            actualizado.setFechaRegistro(almacen.getFechaRegistro());
            actualizado.setPrecioVenta(almacen.getPrecioVenta());
            actualizado.setTamaño(almacen.getTamaño());
            actualizado.setCede(almacen.getCede());
            return new ResponseEntity<>(new ApiResponse(almacenRepository.save(actualizado), HttpStatus.OK, "Actualizado correctamente"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse("", HttpStatus.NOT_FOUND, "Almacén no encontrado"), HttpStatus.NOT_FOUND);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<ApiResponse> delete(Long id) {
        Optional<Almacen> almacen = almacenRepository.findById(id);
        if (almacen.isPresent()) {
            almacenRepository.deleteById(id);
            return new ResponseEntity<>(new ApiResponse("", HttpStatus.OK, "Eliminado correctamente"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse("", HttpStatus.NOT_FOUND, "Almacén no encontrado"), HttpStatus.NOT_FOUND);
        }
    }
}

