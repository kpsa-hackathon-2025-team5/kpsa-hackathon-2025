package io.github.yaksenseback.member.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
abstract class RegisterMemberRequest {

    @NotBlank
    private final String username;
    @NotBlank
    private final String password;
    @NotBlank
    private final String name;

    private final String profileImageUrl;

    RegisterMemberRequest(String username, String password, String name, String profileImageUrl) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

}
