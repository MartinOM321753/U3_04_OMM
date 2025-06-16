package utez.edu.mx.U3_04_OMM.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cede {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String clave;

    @NotBlank(message = "El estado no puede estar vacío")
    @Size(max = 50, message = "El estado no debe superar los 50 caracteres")
    private String estado;

    @NotBlank(message = "El municipio no puede estar vacío")
    @Size(max = 50, message = "El municipio no debe superar los 50 caracteres")
    private String municipio;


    @OneToMany(mappedBy = "cede", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Almacen> almacenes;
}
