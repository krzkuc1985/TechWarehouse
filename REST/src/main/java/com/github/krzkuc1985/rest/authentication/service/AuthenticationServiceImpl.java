package com.github.krzkuc1985.rest.authentication.service;

import com.github.krzkuc1985.dto.authentication.AuthenticationRequest;
import com.github.krzkuc1985.dto.authentication.AuthenticationResponse;
import com.github.krzkuc1985.rest.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    public final UserDetailsService service;
    public final AuthenticationManager authenticationManager;
    public final JwtService jwtService;

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        UserDetails userDetails = service.loadUserByUsername(request.getLogin());

        Map<String, Object> claims = Map.of(
                "Authorities", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
        );

        String accessToken = jwtService.generateToken(userDetails);
        return AuthenticationResponse.builder().accessToken(accessToken).build();
    }

}
