package com.github.krzkuc1985.rest.logindata.mapper;

import com.github.krzkuc1985.dto.logindata.LoginDataRequest;
import com.github.krzkuc1985.dto.logindata.LoginDataResponse;
import com.github.krzkuc1985.rest.logindata.LoginData;
import org.springframework.stereotype.Component;

@Component
public class LoginDataMapper {

    public LoginDataResponse mapToResponse(LoginData loginData) {
        return LoginDataResponse.builder()
                .login(loginData.getLogin())
                .build();
    }

    public LoginData mapToEntity(LoginDataRequest loginDataRequest) {
        LoginData loginData = new LoginData();
        loginData.setLogin(loginDataRequest.getLogin());
        loginData.setPassword(loginDataRequest.getPassword());
        return loginData;
    }

}
