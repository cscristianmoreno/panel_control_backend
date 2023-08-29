package com.example.myapp.services.tokenService.build;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public class TokenBuild {
    @JsonProperty(value="access_token")
    private String access_token;

    @JsonProperty(value="token_type") 
    private String token_type;

    @JsonProperty(value="expires_in")
    private Long expires_in;
}
