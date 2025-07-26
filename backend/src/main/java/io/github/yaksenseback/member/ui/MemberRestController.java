package io.github.yaksenseback.member.ui;

import io.github.yaksenseback.member.application.GetMemberService;
import io.github.yaksenseback.member.application.RegisterMemberService;
import io.github.yaksenseback.member.application.dto.MemberDto;
import io.github.yaksenseback.member.application.dto.RegisterGuardianRequest;
import io.github.yaksenseback.member.application.dto.RegisterPatientRequest;
import io.github.yaksenseback.member.application.dto.RegisterPharmacistRequest;
import io.github.yaksenseback.member.domain.MemberRepository;
import io.github.yaksenseback.member.ui.dto.RegisterMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
class MemberRestController {

    private final RegisterMemberService registerMemberService;
    private final GetMemberService getMemberService;
    private final MemberRepository memberRepository;

    @PostMapping("/guardians")
    RegisterMemberResponse registerMember(@RequestBody RegisterGuardianRequest request) {
        return new RegisterMemberResponse(registerMemberService.registerGuardian(request));
    }

    @PostMapping("/patients")
    RegisterMemberResponse registerPatient(@RequestBody RegisterPatientRequest request) {
        return new RegisterMemberResponse(registerMemberService.registerPatient(request));
    }

    @PostMapping("/pharmacists")
    RegisterMemberResponse registerPharmacist(@RequestBody RegisterPharmacistRequest request) {
        return new RegisterMemberResponse(registerMemberService.registerPharmacist(request));
    }

    @GetMapping("/{memberId}")
    MemberDto getMemberById(@PathVariable Long memberId) {
        return getMemberService.getMemberById(memberId);
    }

    @GetMapping
    List<MemberDto> getMembers() {
        return memberRepository.findAll().stream()
                .map(MemberDto::from)
                .toList();
    }

}
