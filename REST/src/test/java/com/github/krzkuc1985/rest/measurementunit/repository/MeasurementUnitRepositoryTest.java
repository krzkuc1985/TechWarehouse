package com.github.krzkuc1985.rest.measurementunit.repository;

import com.github.krzkuc1985.rest.measurementunit.model.MeasurementUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MeasurementUnitRepositoryTest {

    @Autowired
    private MeasurementUnitRepository measurementUnitRepository;

    private MeasurementUnit measurementUnit;

    @BeforeEach
    void setUp() {
        measurementUnit = new MeasurementUnit("T");
    }

    @Test
    @DisplayName("should save and retrieve MeasurementUnit")
    void saveAndFindById() {
        // when
        MeasurementUnit saved = measurementUnitRepository.save(measurementUnit);
        Optional<MeasurementUnit> found = measurementUnitRepository.findById(saved.getId());

        // then
        assertTrue(found.isPresent());
        assertEquals("T", found.get().getSymbol());
    }

    @Test
    @DisplayName("should delete MeasurementUnit")
    void deleteMeasurementUnit() {
        // given
        MeasurementUnit saved = measurementUnitRepository.save(measurementUnit);

        // when
        measurementUnitRepository.deleteById(saved.getId());
        Optional<MeasurementUnit> found = measurementUnitRepository.findById(saved.getId());

        // then
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("should update MeasurementUnit symbol")
    void updateMeasurementUnitSymbol() {
        // given
        MeasurementUnit saved = measurementUnitRepository.save(measurementUnit);

        // when
        saved.setSymbol("lb");
        measurementUnitRepository.save(saved);
        Optional<MeasurementUnit> updated = measurementUnitRepository.findById(saved.getId());

        // then
        assertTrue(updated.isPresent());
        assertEquals("lb", updated.get().getSymbol());
    }

    @Test
    @DisplayName("should return empty when MeasurementUnit not found")
    void findById_NotFound() {
        // when
        Optional<MeasurementUnit> found = measurementUnitRepository.findById(999L);

        // then
        assertTrue(found.isEmpty());
    }
}
