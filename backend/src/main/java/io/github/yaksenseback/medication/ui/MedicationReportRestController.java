package io.github.yaksenseback.medication.ui;

import io.github.yaksenseback.medication.application.MedicationReportService;
import io.github.yaksenseback.medication.application.dto.DailyComplianceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/medications/reports")
@RequiredArgsConstructor
class MedicationReportRestController {

    private final MedicationReportService complianceService;

    @GetMapping("monthlyCompliance")
    public List<DailyComplianceResponse> getDailyCompliance(@RequestParam Long memberId,
                                                            @RequestParam String startDate,
                                                            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return complianceService.getDailyCompliance(memberId, start, end);
    }


}
