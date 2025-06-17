package utez.edu.mx.U3_04_OMM.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class Almacen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100, message = "La clave no debe superar 100 caracteres")
    private String clave;

    @NotNull(message = "La fecha de registro es obligatoria")
    private LocalDate fechaRegistro;

    private Boolean status;

    @NotBlank(message = "El tamaño es obligatorio")
    @Pattern(regexp = "G|M|P", message = "El tamaño debe ser 'G', 'M' o 'P'")
    private String tamaño;

    @ManyToOne
    private Cede cede;

    @OneToMany(mappedBy = "almacen", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sales> ventas;




}
