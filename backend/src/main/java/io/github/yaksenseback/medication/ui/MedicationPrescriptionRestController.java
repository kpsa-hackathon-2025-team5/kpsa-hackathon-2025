package io.github.yaksenseback.medication.ui;

import io.github.yaksenseback.medication.application.MedicationPrescriptionService;
import io.github.yaksenseback.medication.application.dto.RegisterMedicationPrescriptionRequest;
import io.github.yaksenseback.medication.domain.MedicationPrescription;
import io.github.yaksenseback.medication.domain.MedicationPrescriptionRepository;
import io.github.yaksenseback.medication.ui.dto.RegisterMedicationPrescriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medications/prescriptions")
@RequiredArgsConstructor
public class MedicationPrescriptionRestController {

    private final MedicationPrescriptionService prescriptionService;

    private final MedicationPrescriptionRepository medicationPrescriptionRepository;

    @PostMapping
    public RegisterMedicationPrescriptionResponse register(@RequestBody RegisterMedicationPrescriptionRequest request) {
        Long prescriptionId = prescriptionService.createPrescription(request);
        return new RegisterMedicationPrescriptionResponse(prescriptionId);
    }

    @GetMapping
    public List<MedicationPrescription> getMedicationPrescriptions(Long memberId) {
        return prescriptionService.getMedicationPrescriptions(memberId);
    }

/*    @GetMapping("/{prescriptionId}")
    public MedicationPrescription getMedicationPrescription(Long prescriptionId) {
        return medicationPrescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("MedicationPrescription not found with id: " + prescriptionId));
    }*/

}
