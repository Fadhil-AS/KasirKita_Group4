package com.mycompany.kasirkita.controller;

import com.mycompany.kasirkita.dto.BarangRequest;
import com.mycompany.kasirkita.dto.BarangResponse;
import com.mycompany.kasirkita.service.BarangService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/barang")
public class BarangController {

    private final BarangService service;

    public BarangController(BarangService service) {
        this.service = service;
    }

    @GetMapping
    public List<BarangResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public BarangResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BarangResponse create(@Valid @RequestBody BarangRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public BarangResponse update(@PathVariable Long id, @Valid @RequestBody BarangRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
