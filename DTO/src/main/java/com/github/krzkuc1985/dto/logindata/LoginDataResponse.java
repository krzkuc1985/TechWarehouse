package com.github.krzkuc1985.dto.logindata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginDataResponse {

    @JsonProperty("login")
    @Schema(description = "Login of the user", example = "user")
    private String login;

}
