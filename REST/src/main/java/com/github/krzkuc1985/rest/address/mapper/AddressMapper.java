package com.github.krzkuc1985.rest.address.mapper;

import com.github.krzkuc1985.dto.address.AddressRequest;
import com.github.krzkuc1985.dto.address.AddressResponse;
import com.github.krzkuc1985.rest.address.Address;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AddressMapper {

    public AddressResponse mapToResponse(Address address) {
        return AddressResponse.builder()
                .country(address.getCountry())
                .city(address.getCity())
                .street(address.getStreet())
                .streetNumber(address.getStreetNumber())
                .flatNumber(Optional.ofNullable(address.getFlatNumber()).orElse(""))
                .postalCode(address.getPostalCode())
                .build();
    }

    public List<AddressResponse> mapToResponse(Collection<Address> addresses) {
        return addresses.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Address mapToEntity(Address address, AddressRequest addressRequest) {
        address.setCountry(addressRequest.getCountry());
        address.setCity(addressRequest.getCity());
        address.setStreet(addressRequest.getStreet());
        address.setStreetNumber(addressRequest.getStreetNumber());
        address.setFlatNumber(addressRequest.getFlatNumber());
        address.setPostalCode(addressRequest.getPostalCode());
        return address;
    }

    public Address mapToEntity(AddressRequest addressRequest) {
        return mapToEntity(new Address(), addressRequest);
    }
}
