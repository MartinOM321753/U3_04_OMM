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
            almacen.setFechaRegistro(LocalDate.now());
            almacen.setStatus(true); // Activo por default
            return ResponseEntity.ok(new ApiResponse(almacenRepository.save(almacen), HttpStatus.OK, "Almacén guardado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(null, HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el almacén"));
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<ApiResponse> findById(Long id) {
        return almacenRepository.findById(id)
                .map(almacen -> ResponseEntity.ok(new ApiResponse(almacen, HttpStatus.OK, "")))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(null, HttpStatus.NOT_FOUND, "Almacén no encontrado")));
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<ApiResponse> update(Long id, Almacen almacen) {
        Optional<Almacen> optional = almacenRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(null, HttpStatus.NOT_FOUND, "Almacén no encontrado"));
        }

        Almacen existente = optional.get();
        existente.setTamaño(almacen.getTamaño());
        existente.setStatus(almacen.getStatus());
        existente.setCede(almacen.getCede());
        return ResponseEntity.ok(new ApiResponse(almacenRepository.save(existente), HttpStatus.OK, "Almacén actualizado correctamente"));
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<ApiResponse> delete(Long id) {
        Optional<Almacen> almacenOpt = almacenRepository.findById(id);
        if (almacenOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(null, HttpStatus.NOT_FOUND, "Almacén no encontrado"));
        }

        Almacen almacen = almacenOpt.get();
        if (Boolean.FALSE.equals(almacen.getStatus())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(null, HttpStatus.BAD_REQUEST, "No se puede eliminar un almacén ocupado"));
        }

        almacenRepository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse(null, HttpStatus.OK, "Eliminado correctamente"));
    }

}
