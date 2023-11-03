package com.childsplay.pawfectly.functions.auth.controller;

import com.childsplay.pawfectly.common.dto.ApiResultModel;
import com.childsplay.pawfectly.functions.auth.dto.UserLoginProviderRequest;
import com.childsplay.pawfectly.functions.auth.dto.UserLoginRequest;
import com.childsplay.pawfectly.functions.auth.dto.UserRegisterRequest;
import com.childsplay.pawfectly.functions.auth.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final UserAuthService userAuthService;

    @GetMapping("hello-world")
    @ResponseStatus(HttpStatus.OK)
    public String helloWorld() {
        return "hello world";
    }

    /**
     * Handles the login request and returns the result of the login operation.
     *
     * @param userLoginRequest The user login request object containing the login credentials.
     * @return An ApiResultModel object containing the result of the login operation.
     */
    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResultModel login(@RequestBody UserLoginRequest userLoginRequest) {
        return ApiResultModel.builder()
                .message("Login Successfully")
                .status(HttpStatus.OK.value())
                .resultData(userAuthService.login(userLoginRequest))
                .build();
    }

    /**
     * Controller method for verifying an email address.
     *
     * @param email The email address to be verified, provided in the request body.
     * @return An ApiResultModel containing the result of the email verification process, including a message, status code,
     *         and additional result data.
     */
    @PostMapping("verify-email")
    @ResponseStatus(HttpStatus.OK)
    public ApiResultModel verifyEmail(@RequestBody String email) {
        return ApiResultModel.builder()
                .message("Email Verification")
                .status(HttpStatus.OK.value())
                .resultData(userAuthService.verifyEmail(email))
                .build();
    }

    /**
     * Controller method for user login using a third-party provider.
     *
     * @param userLoginProviderRequest The request containing user login information for the provider.
     * @return An ApiResultModel containing the result of the login process, including a message, status code, and additional
     *         result data.
     */
    @PostMapping("login-using-provider")
    @ResponseStatus(HttpStatus.OK)
    public ApiResultModel loginUsingProvider(@RequestBody UserLoginProviderRequest userLoginProviderRequest) {
        return ApiResultModel.builder()
                .message("Login Using Provider Successfully")
                .status(HttpStatus.OK.value())
                .resultData(userAuthService.loginUsingProvider(userLoginProviderRequest))
                .build();
    }

    /**
     * Registers a new user with the provided registration request.
     *
     * @param registerRequest The registration request containing user details.
     * @return An ApiResultModel object with the registration result.
     */
    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResultModel register(@RequestBody UserRegisterRequest registerRequest) {
        return ApiResultModel.builder()
                .message("Register Successfully")
                .status(HttpStatus.CREATED.value())
                .resultData(userAuthService.register(registerRequest))
                .build();
    }

}
