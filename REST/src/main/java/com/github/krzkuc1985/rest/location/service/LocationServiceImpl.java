package com.github.krzkuc1985.rest.location.service;

import com.github.krzkuc1985.dto.location.LocationRequest;
import com.github.krzkuc1985.dto.location.LocationResponse;
import com.github.krzkuc1985.rest.location.mapper.LocationMapper;
import com.github.krzkuc1985.rest.location.model.Location;
import com.github.krzkuc1985.rest.location.repository.LocationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository repository;
    private final LocationMapper mapper;

    @Override
    public Location findByIdOrThrowException(Long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public LocationResponse findById(Long id) {
        return mapper.mapToResponse(findByIdOrThrowException(id));
    }

    @Override
    public List<LocationResponse> findAll() {
        return mapper.mapToResponse(repository.findAll());
    }

    @Override
    @Transactional
    public LocationResponse create(LocationRequest locationRequest) {
        return mapper.mapToResponse(repository.save(mapper.mapToEntity(locationRequest)));
    }

    @Override
    @Transactional
    public LocationResponse update(Long id, LocationRequest locationRequest) {
        Location location = findByIdOrThrowException(id);
        location.setRack(locationRequest.getRack());
        location.setShelf(locationRequest.getShelf());
        return mapper.mapToResponse(repository.save(location));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Location location = findByIdOrThrowException(id);
        repository.delete(location);
    }

}
