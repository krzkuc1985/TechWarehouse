package com.github.krzkuc1985.rest;

import com.github.krzkuc1985.rest.config.JwtService;
import com.github.krzkuc1985.rest.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

@Import(SecurityConfig.class)
@SpringBootTest
class RestApplicationTests {

    @MockBean
    private JwtService jwtService;

    @Test
    void contextLoads() {
    }

}
