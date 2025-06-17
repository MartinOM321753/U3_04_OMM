package utez.edu.mx.U3_04_OMM.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.U3_04_OMM.Config.ApiResponse;
import utez.edu.mx.U3_04_OMM.DTO.SalesDto;
import utez.edu.mx.U3_04_OMM.service.SalesService;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SalesController {

    private final SalesService salesService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse> create(@RequestBody @Valid SalesDto dto) {
        return salesService.create(dto);
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAll() {
        return salesService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getOne(@PathVariable Long id) {
        return salesService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody @Valid SalesDto dto) {
        return salesService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        return salesService.delete(id);
    }
}
