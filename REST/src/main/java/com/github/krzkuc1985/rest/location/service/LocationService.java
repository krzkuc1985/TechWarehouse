package com.github.krzkuc1985.rest.location.service;

import com.github.krzkuc1985.dto.location.LocationRequest;
import com.github.krzkuc1985.dto.location.LocationResponse;
import com.github.krzkuc1985.rest.location.model.Location;

import java.util.List;

public interface LocationService {

    Location findByIdOrThrowException(Long id);

    LocationResponse findById(Long id);

    List<LocationResponse> findAll();

    LocationResponse create(LocationRequest locationRequest);

    LocationResponse update(Long id, LocationRequest locationRequest);

    void delete(Long id);

}
