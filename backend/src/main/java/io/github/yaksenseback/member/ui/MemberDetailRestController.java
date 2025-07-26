package io.github.yaksenseback.member.ui;

import io.github.yaksenseback.member.application.GetMemberService;
import io.github.yaksenseback.member.domain.Guardian;
import io.github.yaksenseback.member.domain.Pharmacist;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
class MemberDetailRestController {

    private final GetMemberService getMemberService;


    @GetMapping("/guardian/{id}")
    public Guardian getGuardianDetail(@PathVariable Long id) {
        return getMemberService.getGuardianById(id);
    }

    @GetMapping("/pharmacist/{id}")
    public Pharmacist getPharmacistDetail(@PathVariable Long id) {
        return getMemberService.getPharmacistById(id);
    }


}
