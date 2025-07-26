package io.github.yaksenseback.drug.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import io.github.yaksenseback.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "dur_drug_safety")
@JsonIdentityInfo(generator = JSOGGenerator.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DurDrugSafety extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * 성분명
     */
    @Column(name = "ingredient_name", nullable = false, length = 500)
    private String ingredientName;

    /*
     * 성분코드
     */
    @Column(name = "ingredient_code", nullable = false, length = 20)
    private String ingredientCode;

    /*
     * 제품코드
     */
    @Column(name = "product_code", nullable = false, length = 20)
    private String productCode;

    /*
     * 제품명
     */
    @Column(name = "product_name", nullable = false, length = 500)
    private String productName;

    /*
     * 업소명/업체명
     */
    @Column(name = "company_name", nullable = false, length = 500)
    private String companyName;

    /*
     * 급여여부
     */
    @Column(name = "insurance_coverage", length = 10)
    private String insuranceCoverage;

    /*
     * 분류 (elderly_antipyretic, age_contraindication, elderly_caution, pregnancy_contraindication)
     */
    @Column(name = "category", nullable = false, length = 50)
    private String category;

    /*
     * 약품상세정보/상세정보
     */
    @Column(name = "drug_detail_info", columnDefinition = "TEXT")
    private String drugDetailInfo;

    /*
     * 특정연령
     */
    @Column(name = "specific_age")
    private Integer specificAge;

    /*
     * 특정연령단위
     */
    @Column(name = "age_unit", length = 10)
    private String ageUnit;

    /*
     * 연령처리조건
     */
    @Column(name = "age_condition", length = 10)
    private String ageCondition;

    /*
     * 공고일자/고시일자
     */
    @Column(name = "notice_date")
    private LocalDate noticeDate;

    /*
     * 공고번호/고시번호
     */
    @Column(name = "notice_number", length = 20)
    private String noticeNumber;

    /*
     * 금기등급
     */
    @Column(name = "contraindication_grade", length = 5)
    private String contraindicationGrade;

    /*
     * 비고
     */
    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;


}
