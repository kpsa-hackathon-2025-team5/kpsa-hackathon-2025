package io.github.yaksenseback.drug.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "drug_product_info")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class DrugProductInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * 품목기준코드
     */
    @Column(name = "item_code", nullable = false, length = 20)
    private String itemCode;

    /*
     * 제품명
     */
    @Column(name = "product_name", nullable = false, length = 1000)
    private String productName;

    /*
     * 제품영문명
     */
    @Column(name = "product_name_eng", length = 1000)
    private String productNameEng;

    /*
     * 업체명
     */
    @Column(name = "company_name", nullable = false, length = 255)
    private String companyName;

    /*
     * 업체영문명
     */
    @Column(name = "company_name_eng", length = 255)
    private String companyNameEng;

    /*
     * 허가일
     */
    @Column(name = "approval_date")
    private LocalDate approvalDate;

    /*
     * 품목구분
     */
    @Column(name = "item_category", length = 50)
    private String itemCategory;

    /*
     * 허가번호
     */
    @Column(name = "approval_number", length = 50)
    private String approvalNumber;

    /*
     * 취소/취하
     */
    @Column(name = "cancel_withdraw", length = 20)
    private String cancelWithdraw;

    /*
     * 취소/취하일자
     */
    @Column(name = "cancel_withdraw_date")
    private LocalDate cancelWithdrawDate;

    /*
     * 주성분
     */
    @Column(name = "main_ingredient", columnDefinition = "TEXT")
    private String mainIngredient;

    /*
     * 첨가제
     */
    @Column(name = "additives", columnDefinition = "TEXT")
    private String additives;

    /*
     * 주성분영문
     */
    @Column(name = "main_ingredient_eng", columnDefinition = "TEXT")
    private String mainIngredientEng;

    /*
     * 품목분류
     */
    @Column(name = "item_classification", length = 255)
    private String itemClassification;

    /*
     * 전문의약품
     */
    @Column(name = "prescription_drug", length = 20)
    private String prescriptionDrug;

    /*
     * 완제/원료
     */
    @Column(name = "finished_raw", length = 20)
    private String finishedRaw;

    /*
     * 허가/신고
     */
    @Column(name = "approval_report", length = 20)
    private String approvalReport;

    /*
     * 제조/수입
     */
    @Column(name = "manufacture_import", length = 20)
    private String manufactureImport;

    /*
     * 마약구분
     */
    @Column(name = "narcotic_classification", length = 20)
    private String narcoticClassification;

    /*
     * 모양
     */
    @Column(name = "shape", length = 255)
    private String shape;

    /*
     * 색상
     */
    @Column(name = "color", length = 255)
    private String color;

    /*
     * 제형
     */
    @Column(name = "dosage_form", length = 50)
    private String dosageForm;

    /*
     * 장축
     */
    @Column(name = "major_axis", length = 20)
    private String majorAxis;

    /*
     * 단축
     */
    @Column(name = "minor_axis", length = 20)
    private String minorAxis;

    /*
     * 신약구분
     */
    @Column(name = "new_drug_classification", length = 20)
    private String newDrugClassification;

    /*
     * 표준코드명
     */
    @Column(name = "standard_code_name", columnDefinition = "TEXT")
    private String standardCodeName;

    /*
     * ATC코드
     */
    @Column(name = "atc_code", length = 20)
    private String atcCode;

    /*
     * 묶음의약품정보
     */
    @Column(name = "bundle_drug_info", columnDefinition = "TEXT")
    private String bundleDrugInfo;

    /*
     * e은약요
     */
    @Column(name = "e_drug_book", length = 20)
    private String eDrugBook;

    /*
     * 수입제조국
     */
    @Column(name = "import_manufacture_country", length = 255)
    private String importManufactureCountry;

}
