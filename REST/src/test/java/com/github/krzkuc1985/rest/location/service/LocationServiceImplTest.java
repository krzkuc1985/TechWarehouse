package com.github.krzkuc1985.rest.location.service;

import com.github.krzkuc1985.dto.location.LocationRequest;
import com.github.krzkuc1985.dto.location.LocationResponse;
import com.github.krzkuc1985.rest.location.mapper.LocationMapper;
import com.github.krzkuc1985.rest.location.model.Location;
import com.github.krzkuc1985.rest.location.repository.LocationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private LocationMapper locationMapper;

    @InjectMocks
    private LocationServiceImpl locationService;

    private Location location;
    private LocationRequest locationRequest;
    private LocationResponse locationResponse;

    @BeforeEach
    void setUp() {
        location = new Location("A", "1");
        locationRequest = new LocationRequest("A", "1");
        locationResponse = new LocationResponse(1L, "A", "1");
    }

    @Test
    @DisplayName("findById should return LocationResponse when found")
    void findById_ReturnsLocationResponse() {
        when(locationRepository.findById(eq(1L))).thenReturn(Optional.of(location));
        when(locationMapper.mapToResponse(location)).thenReturn(locationResponse);

        LocationResponse result = locationService.findById(1L);

        assertEquals(locationResponse, result);
        verify(locationRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById should throw EntityNotFoundException when not found")
    void findById_ThrowsEntityNotFoundException() {
        when(locationRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> locationService.findById(1L));
        verify(locationRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findAll should return list of LocationResponses")
    void findAll_ReturnsListOfLocationResponses() {
        List<Location> locations = List.of(location);
        List<LocationResponse> responses = List.of(locationResponse);

        when(locationRepository.findAll()).thenReturn(locations);
        when(locationMapper.mapToResponse(locations)).thenReturn(responses);

        List<LocationResponse> result = locationService.findAll();

        assertEquals(responses, result);
        verify(locationRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("create should save and return LocationResponse")
    void create_SavesAndReturnsLocationResponse() {
        when(locationRepository.save(any(Location.class))).thenReturn(location);
        when(locationMapper.mapToEntity(locationRequest)).thenReturn(location);
        when(locationMapper.mapToResponse(location)).thenReturn(locationResponse);

        LocationResponse result = locationService.create(locationRequest);

        assertEquals(locationResponse, result);
        verify(locationRepository, times(1)).save(location);
    }

    @Test
    @DisplayName("update should update and return LocationResponse")
    void update_UpdatesAndReturnsLocationResponse() {
        when(locationRepository.findById(eq(1L))).thenReturn(Optional.of(location));
        when(locationRepository.save(any(Location.class))).thenReturn(location);
        when(locationMapper.mapToResponse(location)).thenReturn(locationResponse);

        LocationResponse result = locationService.update(1L, locationRequest);

        assertEquals(locationResponse, result);
        verify(locationRepository, times(1)).findById(1L);
        verify(locationRepository, times(1)).save(location);
    }

    @Test
    @DisplayName("update should throw EntityNotFoundException when not found")
    void update_ThrowsEntityNotFoundException() {
        when(locationRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> locationService.update(1L, locationRequest));
        verify(locationRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("delete should remove Location")
    void delete_RemovesLocation() {
        when(locationRepository.findById(eq(1L))).thenReturn(Optional.of(location));
        doNothing().when(locationRepository).delete(location);

        locationService.delete(1L);

        verify(locationRepository, times(1)).findById(1L);
        verify(locationRepository, times(1)).delete(location);
    }

    @Test
    @DisplayName("delete should throw EntityNotFoundException when not found")
    void delete_ThrowsEntityNotFoundException() {
        when(locationRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> locationService.delete(1L));
        verify(locationRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("update should throw ObjectOptimisticLockingFailureException on optimistic lock failure")
    void update_ThrowsOptimisticLockException() {
        when(locationRepository.findById(eq(1L))).thenReturn(Optional.of(location));
        when(locationRepository.save(any(Location.class)))
                .thenThrow(ObjectOptimisticLockingFailureException.class);

        assertThrows(ObjectOptimisticLockingFailureException.class, () -> locationService.update(1L, locationRequest));
        verify(locationRepository, times(1)).findById(1L);
        verify(locationRepository, times(1)).save(location);
    }
}
