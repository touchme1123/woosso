package com.shpark.woosso.api.milk.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "t_milk_records", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"reg_number", "test_date"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MilkRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 ID
    private Long id; // 고유 ID (PK)

    @ManyToOne // MilkRecord는 하나의 Cow에 속함 (다대일 관계)
    @JoinColumn(name = "reg_number", nullable = false) // 외래 키 컬럼 지정
    private Cow cow; // 이 MilkRecord가 속한 Cow 객체

    @Column(name = "test_date", nullable = false)
    private LocalDate testDate; // 검정일

    @Column(name = "milk_yield")
    private Double milkYield; // 유량 (측정일 기준)

    @Column(name = "fat_pct")
    private Double fatPct; // 유지율

    @Column(name = "protein_pct")
    private Double proteinPct; // 유단백질

    @Column(name = "snf_pct")
    private Double snfPct; // 무지고형분율

    @Column(name = "scc")
    private Integer scc; // 체세포수 (천 단위)

    @Column(name = "mun")
    private Double mun; // MUN

    @Column(name = "yield_305")
    private Integer yield305; // 305일유량

    @Column(name = "fat_305")
    private Integer fat305; // 305일유지량

    @Column(name = "protein_305")
    private Integer protein305; // 305일유단백

    @Column(name = "snf_305")
    private Integer snf305; // 305일무지고형분량

    @Column(name = "me_yield")
    private Integer meYield; // 성년형유량

    @Column(name = "me_fat")
    private Integer meFat; // 성년형유지량

    @Column(name = "me_protein")
    private Integer meProtein; // 성년형유단백량

    @Column(name = "me_snf")
    private Integer meSnf; // 성년형무지고형분량

    @Column(name = "peak_scc")
    private Integer peakScc; // 최고유량체세포
}