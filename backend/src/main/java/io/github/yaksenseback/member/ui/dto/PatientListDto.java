package io.github.yaksenseback.member.ui.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.yaksenseback.member.domain.Patient;
import lombok.Getter;
import lombok.experimental.Delegate;

@Getter
public class PatientListDto {

    @Delegate
    @JsonIgnore
    private Patient patient;

    public PatientListDto(Patient patient) {
        this.patient = patient;
    }

}
