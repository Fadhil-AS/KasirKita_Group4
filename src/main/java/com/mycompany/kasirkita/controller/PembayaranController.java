package com.mycompany.kasirkita.controller;

import com.mycompany.kasirkita.dto.PembayaranRequest;
import com.mycompany.kasirkita.dto.PembayaranResponse;
import com.mycompany.kasirkita.service.PembayaranService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pembayaran")
public class PembayaranController {

    private final PembayaranService service;

    public PembayaranController(PembayaranService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PembayaranResponse bayar(@Valid @RequestBody PembayaranRequest req) {
        return service.bayar(req);
    }
}
