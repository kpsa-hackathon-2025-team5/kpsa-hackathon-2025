package io.github.yaksenseback.medication.application;

import io.github.yaksenseback.medication.application.dto.CreateDailySideEffectRecordRequest;
import io.github.yaksenseback.medication.domain.DailySideEffectRecord;
import io.github.yaksenseback.medication.domain.DailySideEffectRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateDailySideEffectRecordService {


    private final DailySideEffectRecordRepository dailySideEffectRecordRepository;


    public DailySideEffectRecord createDailySideEffectRecord(CreateDailySideEffectRecordRequest request) {
        DailySideEffectRecord dailySideEffectRecord = new DailySideEffectRecord(
                request.memberId(),
                request.sideEffectType(),
                request.sideEffectLevel(),
                request.sideEffectNote()
        );
        return dailySideEffectRecordRepository.save(dailySideEffectRecord);
    }

}
