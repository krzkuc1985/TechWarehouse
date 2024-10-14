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
        MeasurementUnit savedUnit = measurementUnitRepository.save(measurementUnit);
        Optional<MeasurementUnit> foundUnit = measurementUnitRepository.findById(savedUnit.getId());

        // then
        assertTrue(foundUnit.isPresent());
        assertEquals("T", foundUnit.get().getSymbol());
    }

    @Test
    @DisplayName("should delete MeasurementUnit")
    void deleteMeasurementUnit() {
        // given
        MeasurementUnit savedUnit = measurementUnitRepository.save(measurementUnit);

        // when
        measurementUnitRepository.deleteById(savedUnit.getId());
        Optional<MeasurementUnit> foundUnit = measurementUnitRepository.findById(savedUnit.getId());

        // then
        assertFalse(foundUnit.isPresent());
    }

    @Test
    @DisplayName("should update MeasurementUnit symbol")
    void updateMeasurementUnitSymbol() {
        // given
        MeasurementUnit savedUnit = measurementUnitRepository.save(measurementUnit);

        // when
        savedUnit.setSymbol("lb");
        measurementUnitRepository.save(savedUnit);
        Optional<MeasurementUnit> updatedUnit = measurementUnitRepository.findById(savedUnit.getId());

        // then
        assertTrue(updatedUnit.isPresent());
        assertEquals("lb", updatedUnit.get().getSymbol());
    }

    @Test
    @DisplayName("should return empty when MeasurementUnit not found")
    void findById_NotFound() {
        // when
        Optional<MeasurementUnit> foundUnit = measurementUnitRepository.findById(999L);

        // then
        assertTrue(foundUnit.isEmpty());
    }
}
