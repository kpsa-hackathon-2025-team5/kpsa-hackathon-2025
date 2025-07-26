package io.github.yaksenseback.medication.domain;

import java.time.LocalDate;
import java.util.List;

public interface MedicationScheduleCustomRepository {

    List<MedicationSchedule> findAllMedicationSchedules(Long memberId, TimeLabel timeLabel, LocalDate date);

}
