package com.github.krzkuc1985.rest.personaldata.mapper;

import com.github.krzkuc1985.dto.personaldata.PersonalDataRequest;
import com.github.krzkuc1985.dto.personaldata.PersonalDataResponse;
import com.github.krzkuc1985.rest.personaldata.PersonalData;
import org.springframework.stereotype.Component;

@Component
public class PersonalDataMapper {

    public PersonalDataResponse mapToResponse(PersonalData personalData) {
        return PersonalDataResponse.builder()
                .firstName(personalData.getFirstName())
                .lastName(personalData.getLastName())
                .phoneNumber(personalData.getPhoneNumber())
                .email(personalData.getEmail())
                .build();
    }

    public PersonalData mapToEntity(PersonalData personalData, PersonalDataRequest personalDataRequest) {
        personalData.setFirstName(personalDataRequest.getFirstName());
        personalData.setLastName(personalDataRequest.getLastName());
        personalData.setPhoneNumber(personalDataRequest.getPhoneNumber());
        personalData.setEmail(personalDataRequest.getEmail());
        return personalData;
    }

    public PersonalData mapToEntity(PersonalDataRequest personalDataRequest) {
        return mapToEntity(new PersonalData(), personalDataRequest);
    }

}
