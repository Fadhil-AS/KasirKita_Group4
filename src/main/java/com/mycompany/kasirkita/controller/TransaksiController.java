package com.mycompany.kasirkita.controller;

import com.mycompany.kasirkita.dto.TransaksiRequest;
import com.mycompany.kasirkita.dto.TransaksiResponse;
import com.mycompany.kasirkita.service.TransaksiService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaksi")
public class TransaksiController {

    private final TransaksiService service;

    public TransaksiController(TransaksiService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransaksiResponse create(@Valid @RequestBody TransaksiRequest req) {
        return service.create(req);
    }

    @GetMapping
    public List<TransaksiResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public TransaksiResponse get(@PathVariable Long id) {
        return service.get(id);
    }
}
