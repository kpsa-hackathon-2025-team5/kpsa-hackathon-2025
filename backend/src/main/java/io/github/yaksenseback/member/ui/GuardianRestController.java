package io.github.yaksenseback.member.ui;

import io.github.yaksenseback.member.application.GuardianService;
import io.github.yaksenseback.member.domain.Guardian;
import io.github.yaksenseback.member.ui.dto.PatientListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/guardians")
@RequiredArgsConstructor
class GuardianRestController {

    private final GuardianService guardianService;


    @PostMapping("/{guardianId}/patients")
    Guardian addPatientToGuardian(@PathVariable Long guardianId,
                                  @RequestBody AddPatientToGuardianRequest request) {
        return guardianService.addPatientToGuardian(request.guardianId(), request.patientId());
    }


    @GetMapping("/{guardianId}/patients")
    List<PatientListDto> getPatientsByGuardian(@PathVariable Long guardianId) {
        return guardianService.getPatientsByGuardian(guardianId);
    }

    public record AddPatientToGuardianRequest(Long guardianId, Long patientId) {
    }

}
