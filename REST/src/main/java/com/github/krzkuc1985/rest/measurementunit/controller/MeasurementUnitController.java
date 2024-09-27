package com.github.krzkuc1985.rest.measurementunit.controller;

import com.github.krzkuc1985.dto.measurementunit.MeasurementUnitRequest;
import com.github.krzkuc1985.dto.measurementunit.MeasurementUnitResponse;
import com.github.krzkuc1985.rest.measurementunit.service.MeasurementUnitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/measurement-units")
public class MeasurementUnitController {

    private final MeasurementUnitService service;

    @GetMapping
    public ResponseEntity<List<MeasurementUnitResponse>> getAll() {
        List<MeasurementUnitResponse> responses = service.findAll();
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeasurementUnitResponse> getById(@PathVariable Long id) {
        MeasurementUnitResponse response = service.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<MeasurementUnitResponse> create(@Valid @RequestBody MeasurementUnitRequest measurementUnitRequest) {
        MeasurementUnitResponse response = service.create(measurementUnitRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MeasurementUnitResponse> update(@PathVariable Long id,@Valid @RequestBody MeasurementUnitRequest measurementUnitRequest) {
        MeasurementUnitResponse response = service.update(id, measurementUnitRequest);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
