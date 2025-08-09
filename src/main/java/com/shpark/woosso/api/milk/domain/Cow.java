package com.shpark.woosso.api.milk.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "t_cows")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cow {

    @Id // 기본 키 (Primary Key)
    @Column(name = "reg_number", unique = true, nullable = false)
    private String regNumber; // 등록번호 (RegistrationNumber)

    @Column(name = "name")
    private String name; // 명호

    @Column(name = "short_name")
    private Integer shortName; // 단축명호

    @Column(name = "birth_date")
    private LocalDate birthDate; // 생년월일

    @Column(name = "farm_name")
    private String farmName; // 농가이름

    @OneToMany(mappedBy = "cow", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LactationInfo> lactationInfo;

    @OneToMany(mappedBy = "cow", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BreedingRecord> breedingRecord;
}