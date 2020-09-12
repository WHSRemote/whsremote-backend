package com.whsremote.api.security;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

public class JWTAudienceValidator implements OAuth2TokenValidator<Jwt> {
    private final String audience;

    public JWTAudienceValidator(String audience) {
        this.audience = audience;
    }

    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        System.out.println("Validating! JWT audience: " + jwt.getAudience());
        OAuth2Error error = new OAuth2Error("invalid_token", "The required audience is missing", null);
        if (jwt.getAudience().contains(audience)) {
            return OAuth2TokenValidatorResult.success();
        }
        return OAuth2TokenValidatorResult.failure(error);
    }
}
