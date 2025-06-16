package utez.edu.mx.U3_04_OMM.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.U3_04_OMM.model.Cliente;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCorreo(String correo);

}
