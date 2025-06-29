package com.shpark.woosso.api.milk.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "t_lactation_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LactationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 ID (PK)

    @ManyToOne // LactationInfo는 하나의 Cow에 속함 (다대일 관계)
    @JoinColumn(name = "reg_number", nullable = false)
    private Cow cow; // 이 LactationInfo가 속한 Cow 객체

    @Column(name = "test_date", nullable = false)
    private LocalDate testDate; // 검정일

    @Column(name = "parity")
    private Integer parity; // 산차

    @Column(name = "days_at_lact")
    private Integer DaysAtLact; // 누적 착유일수

    @Column(name = "prev_lact_persistence")
    private Double prevLactPersistence; // 전산차 비유 지속성

    @Column(name = "curr_lact_persistence")
    private Double currLactPersistenceAtLact; // 해당 산차의 비유 지속성

    @Column(name = "days_to_peak")
    private Integer daysToPeak; // 비유 최고 도달일수

    @Column(name = "late_peak_yield")
    private Double latePeakYield; // 비유 후기 최고 유량

    @Column(name = "early_avg_fat")
    private Double earlyAvgFat; // 비유 초기 평균 유지율

    @Column(name = "early_avg_protein")
    private Double earlyAvgProtein; // 비유 초기 평균 단백율

    @Column(name = "early_avg_mun")
    private Double earlyAvgMun; // 비유 초기 평균 MUN

    @Column(name = "last_yield_dry_off")
    private Double lastYieldDryOff; // 건유 전 마지막 유량

    @Column(name = "prev_lact_dry_off_yield")
    private Double prevLactDryOffYield; // 전산차 건유 전 유량
}