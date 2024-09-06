package com.github.krzkuc1985.rest.measurementunit.controller;

import com.github.krzkuc1985.dto.measurementunit.MeasurementUnitRequest;
import com.github.krzkuc1985.dto.measurementunit.MeasurementUnitResponse;
import com.github.krzkuc1985.rest.measurementunit.service.MeasurementUnitService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/measurement-units")
public class MeasurementUnitController {

    private final MeasurementUnitService measurementUnitService;

    @GetMapping
    public ResponseEntity<List<MeasurementUnitResponse>> getAll() {
        List<MeasurementUnitResponse> responses = measurementUnitService.findAll();
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeasurementUnitResponse> getById(@PathVariable Long id) {
        MeasurementUnitResponse response = measurementUnitService.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<MeasurementUnitResponse> create(@Valid @RequestBody MeasurementUnitRequest measurementUnitRequest) {
        MeasurementUnitResponse response = measurementUnitService.create(measurementUnitRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<MeasurementUnitResponse> update(@PathVariable Long id,@Valid @RequestBody MeasurementUnitRequest measurementUnitRequest) {
        MeasurementUnitResponse response = measurementUnitService.update(id, measurementUnitRequest);
        return ResponseEntity.ok().body(response);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        measurementUnitService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
