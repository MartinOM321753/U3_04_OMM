package utez.edu.mx.U3_04_OMM.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

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
    private String clave; // "[clave de la cede]-A[id]"

    @NotNull(message = "La fecha de registro es obligatoria")
    private LocalDate fechaRegistro;

    @Positive(message = "El precio de venta debe ser mayor que cero")
    private double precioVenta;

    @NotBlank(message = "El tamaño es obligatorio")
    @Pattern(regexp = "G|M|P", message = "El tamaño debe ser 'G', 'M' o 'P'")
    private String tamaño;

    @ManyToOne
    @NotNull(message = "La cede es obligatoria")
    private Cede cede;
}
