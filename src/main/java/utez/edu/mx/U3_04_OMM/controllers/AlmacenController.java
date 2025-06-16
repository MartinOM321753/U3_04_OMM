package utez.edu.mx.U3_04_OMM.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.U3_04_OMM.Config.ApiResponse;
import utez.edu.mx.U3_04_OMM.model.Almacen;
import utez.edu.mx.U3_04_OMM.service.AlmacenService;

import java.util.List;


@RestController
@RequestMapping("/api/almacen")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AlmacenController {

    private final AlmacenService almacenService;

    @GetMapping("/")
    public List<Almacen> getAll() {
        return almacenService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
        return almacenService.findById(id);
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> create(@RequestBody Almacen almacen) {
        return almacenService.save(almacen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody Almacen almacen) {
        return almacenService.update(id, almacen);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        return almacenService.delete(id);
    }
}