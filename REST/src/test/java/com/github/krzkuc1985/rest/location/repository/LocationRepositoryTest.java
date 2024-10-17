package com.github.krzkuc1985.rest.location.repository;

import com.github.krzkuc1985.rest.location.model.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LocationRepositoryTest {

    @Autowired
    private LocationRepository locationRepository;

    private Location location;

    @BeforeEach
    void setUp() {
        location = new Location("A", "1");
    }

    @Test
    @DisplayName("should save and retrieve Location")
    void saveAndFindById() {
        // when
        Location saved = locationRepository.save(location);
        Optional<Location> found = locationRepository.findById(saved.getId());

        // then
        assertTrue(found.isPresent());
        assertEquals("A", found.get().getRack());
        assertEquals("1", found.get().getShelf());
    }

    @Test
    @DisplayName("should delete Location")
    void deleteLocation() {
        // given
        Location saved = locationRepository.save(location);

        // when
        locationRepository.deleteById(saved.getId());
        Optional<Location> found = locationRepository.findById(saved.getId());

        // then
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("should update Location symbol")
    void updateLocationSymbol() {
        // given
        Location saved = locationRepository.save(location);

        // when
        saved.setRack("B");
        saved.setShelf("2");
        locationRepository.save(saved);
        Optional<Location> updated = locationRepository.findById(saved.getId());

        // then
        assertTrue(updated.isPresent());
        assertEquals("B", updated.get().getRack());
        assertEquals("2", updated.get().getShelf());
    }

    @Test
    @DisplayName("should return empty when Location not found")
    void findById_NotFound() {
        // when
        Optional<Location> found = locationRepository.findById(999L);

        // then
        assertTrue(found.isEmpty());
    }

}
