package io.github.yaksenseback.member.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("GUARDIAN")
@Getter
@JsonIdentityInfo(generator = JSOGGenerator.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Guardian extends Member {

    /**
     * 환자의 보호자 관계
     */
    private String relationship;

    @Builder
    public Guardian(String username,
                    String password,
                    String name,
                    String profileImageUrl,
                    String relationship) {
        super(username, password, profileImageUrl, name, MemberType.GUARDIAN);
        this.relationship = relationship;
    }

}
