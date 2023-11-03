package com.childsplay.pawfectly.functions.auth.dto;

import com.childsplay.pawfectly.common.enums.LoginProviderEnum;

public record UserLoginProviderRequest(String email,
                                       String name,
                                       LoginProviderEnum providerName,
                                       String providerId,
                                       String accessToken,
                                       Boolean isActive) {
}
