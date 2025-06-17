package utez.edu.mx.U3_04_OMM.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Double precio;

    private LocalDate fechaVenta;

    private LocalDate fechaFin;


    private String tipo;

    @ManyToOne
    private Almacen almacen;

    @ManyToOne
    private Cliente cliente;
}
