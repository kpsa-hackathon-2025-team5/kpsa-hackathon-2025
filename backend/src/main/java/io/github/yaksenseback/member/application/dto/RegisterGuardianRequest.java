package io.github.yaksenseback.member.application.dto;

import lombok.Getter;

@Getter
public class RegisterGuardianRequest extends RegisterMemberRequest {

    private final String relationship;

    public RegisterGuardianRequest(String username, String password, String name, String profileImageUrl, String relationship) {
        super(username, password, name, profileImageUrl);
        this.relationship = relationship;
    }

}
