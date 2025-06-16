package utez.edu.mx.U3_04_OMM.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.U3_04_OMM.Config.ApiResponse;
import utez.edu.mx.U3_04_OMM.model.Cliente;
import utez.edu.mx.U3_04_OMM.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> findById(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            return ResponseEntity.ok(new ApiResponse(cliente.get(), HttpStatus.OK, ""));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse(null, HttpStatus.NOT_FOUND, "Cliente no encontrado"));
    }

    @Transactional
    public ResponseEntity<ApiResponse> save(Cliente cliente) {
        try {
            Optional<Cliente> existing = clienteRepository.findByCorreo(cliente.getCorreo());
            if (existing.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ApiResponse(null, HttpStatus.CONFLICT, "El correo ya está registrado"));
            }

            Cliente saved = clienteRepository.save(cliente);
            return ResponseEntity.ok(new ApiResponse(saved, HttpStatus.OK, "Cliente guardado correctamente"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(null, HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar cliente"));
        }
    }


    @Transactional
    public ResponseEntity<ApiResponse> update(Long id, Cliente cliente) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);
        if (clienteOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(null, HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        }

        Optional<Cliente> existingByCorreo = clienteRepository.findByCorreo(cliente.getCorreo());
        if (existingByCorreo.isPresent() && !existingByCorreo.get().getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse(null, HttpStatus.CONFLICT, "El correo ya está registrado por otro cliente"));
        }

        Cliente existente = clienteOpt.get();
        existente.setNombreCompleto(cliente.getNombreCompleto());
        existente.setTelefono(cliente.getTelefono());
        existente.setCorreo(cliente.getCorreo());

        clienteRepository.save(existente);
        return ResponseEntity.ok(new ApiResponse(existente, HttpStatus.OK, "Cliente actualizado correctamente"));
    }


    @Transactional
    public ResponseEntity<ApiResponse> delete(Long id) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);
        if (clienteOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(null, HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        }
        clienteRepository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse(null, HttpStatus.OK, "Cliente eliminado correctamente"));
    }
}