package com.github.krzkuc1985.rest.location.mapper;

import com.github.krzkuc1985.dto.location.LocationRequest;
import com.github.krzkuc1985.dto.location.LocationResponse;
import com.github.krzkuc1985.rest.location.model.Location;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LocationMapper {

    public LocationResponse mapToResponse(Location location) {
        return LocationResponse.builder()
                .id(location.getId())
                .rack(location.getRack())
                .shelf(location.getShelf())
                .build();
    }

    public List<LocationResponse> mapToResponse(Collection<Location> locations) {
        return locations.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Location mapToEntity(Location location, LocationRequest locationRequest) {
        location.setRack(locationRequest.getRack());
        location.setShelf(locationRequest.getShelf());
        return location;
    }

    public Location mapToEntity(LocationRequest locationRequest) {
        return mapToEntity(new Location(), locationRequest);
    }

}
