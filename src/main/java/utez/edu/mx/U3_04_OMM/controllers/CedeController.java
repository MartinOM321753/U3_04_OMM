package utez.edu.mx.U3_04_OMM.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.U3_04_OMM.Config.ApiResponse;
import utez.edu.mx.U3_04_OMM.model.Cede;
import utez.edu.mx.U3_04_OMM.service.CedeService;

import java.util.List;

@RestController
@RequestMapping("/api/cedes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CedeController {

    private final CedeService cedeService;

    @GetMapping("/")
    public List<Cede> getAll() {
        return cedeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
        return cedeService.findById(id);
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> create(@RequestBody Cede cede) {
        return cedeService.save(cede);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody Cede cede) {
        return cedeService.update(id, cede);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        return cedeService.delete(id);
    }
}