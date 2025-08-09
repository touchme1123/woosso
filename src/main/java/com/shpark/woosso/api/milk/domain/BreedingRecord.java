package com.shpark.woosso.api.milk.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_breeding_records", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"reg_number", "test_date"})
})
public class BreedingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 ID (PK)

    @ManyToOne // BreedingRecord는 하나의 Cow에 속함 (다대일 관계)
    @JoinColumn(name = "reg_number", nullable = false)
    private Cow cow; // 이 BreedingRecord가 속한 Cow 객체

    @Column(name = "test_date", nullable = false)
    private LocalDate testDate; // 검정일

    @Column(name = "calving_date")
    private LocalDate calvingDate; // 최근분만일 (이벤트 기록)

    @Column(name = "dry_off_date")
    private LocalDate dryOffDate; // 최근건유일 (이벤트 기록)

    @Column(name = "open_days_at_event") // 해당 이벤트 발생 시점의 공태일수
    private Integer openDays;

    @Column(name = "last_breeding_date")
    private LocalDate lastBreedingDate; // 최종수정일자

    @Column(name = "last_breeding_count")
    private Integer lastBreedingCount; // 최종수정횟수

    @Column(name = "last_semen_code")
    private String lastSemenCode; // 최종수정정액코드

    @Column(name = "days_to_first_breeding")
    private Integer daysToFirstBreeding; // 분만후첫수정일까지일수
}