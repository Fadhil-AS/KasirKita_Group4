package com.mycompany.kasirkita.controller;

import com.mycompany.kasirkita.dto.PembeliRequest;
import com.mycompany.kasirkita.dto.PembeliResponse;
import com.mycompany.kasirkita.service.PembeliService;
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
@RequestMapping("/api/pembeli")
public class PembeliController {

    private final PembeliService service;

    public PembeliController(PembeliService service) {
        this.service = service;
    }

    @GetMapping
    public List<PembeliResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public PembeliResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PembeliResponse create(@Valid @RequestBody PembeliRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public PembeliResponse update(@PathVariable Long id, @Valid @RequestBody PembeliRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
