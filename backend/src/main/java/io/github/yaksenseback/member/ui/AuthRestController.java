package io.github.yaksenseback.member.ui;

import io.github.yaksenseback.common.exception.UnauthorizedException;
import io.github.yaksenseback.member.domain.Member;
import io.github.yaksenseback.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final MemberRepository memberRepository;


    @PostMapping("/login")
    public Member login(@RequestBody LoginRequest request) {
        return memberRepository.findByUsernameAndPassword(request.username(), request.password())
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));
    }


    public record LoginRequest(String username, String password) {
    }


}
