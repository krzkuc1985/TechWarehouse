package com.github.krzkuc1985.rest.authentication.service;

import com.github.krzkuc1985.dto.authentication.AuthenticationRequest;
import com.github.krzkuc1985.dto.authentication.AuthenticationResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.AuthenticationException;

public interface AuthenticationService {

    AuthenticationResponse login(@Valid AuthenticationRequest request) throws AuthenticationException;

}
