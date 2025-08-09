package com.shpark.woosso.api.milk.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
public class CowDataDto {
    //Cow
    private String regNumber; // 젖소의 등록 번호 (고유 식별자)
    private String name; // 젖소의 명호
    private Integer shortName; // 젖소의 단축 명호
    private LocalDate birthDate; // 젖소의 생년월일
    private String farmName; // 농가이름

    //BreedingRecord
    private LocalDate calvingDate; // 최근 분만일
    private LocalDate dryOffDate; // 최근 건유일
    private Integer openDays; // 공태 일수
    private LocalDate lastBreedingDate; // 최종수정일자
    private Integer lastBreedingCount; // 최종 수정 횟수
    private String lastSemenCode; // 최종수정정액코드
    private Integer daysToFirstBreeding; // 분만후첫수정일까지일수

    //MilkRecord
    private LocalDate test_date; // 최신 검정일
    private Double milkYield; // 최신 유량
    private Double fatPct; // 최신 유지율
    private Double proteinPct; // 최신 유단백질
    private Double snfPct; // 무지고형분율
    private Integer scc; // 최신 체세포수
    private Double mun; // 최신 MUN (Milk Urea Nitrogen) 값
    private Integer yield305; // 305일 유량
    private Integer fat305; // 305일유지량
    private Integer protein305; // 305일유단백
    private Integer snf305; // 305일무지고형분량
    private Integer meYield; // 성년형유량
    private Integer meFat; // 성년형유지량
    private Integer meProtein; // 성년형유단백량
    private Integer meSnf; // 성년형무지고형분량
    private Integer peakScc; // 최고유량체세포

    //LactationInfo
    private Integer parity; // 산차
    private Integer DaysAtLact; // 현재 산차의 누적 착유 일수
    private Double prevLactPersistence; // 전산차 비유 지속성
    private Double currLactPersistenceAtLact; // 해당 산차의 비유 지속성
    private Integer daysToPeak; // 비유 최고 도달일수
    private Double latePeakYield; // 비유 후기 최고 유량
    private Double earlyAvgFat; // 비유 초기 평균 유지율
    private Double earlyAvgProtein; // 비유 초기 평균 단백율
    private Double earlyAvgMun; // 비유 초기 평균 MUN
    private Double lastYieldDryOff; // 건유 전 마지막 유량
    private Double prevLactDryOffYield; // 전산차 건유 전 유량

    //전체 필드 생성자
    @QueryProjection
    public CowDataDto(String regNumber, String name, Integer shortName, LocalDate birthDate, String farmName,
                      LocalDate calvingDate, LocalDate dryOffDate, Integer openDays, LocalDate lastBreedingDate,
                      Integer lastBreedingCount, String lastSemenCode, Integer daysToFirstBreeding, LocalDate test_date,
                      Double milkYield, Double fatPct, Double proteinPct, Double snfPct, Integer scc, Double mun,
                      Integer yield305, Integer fat305, Integer protein305, Integer snf305, Integer meYield,
                      Integer meFat, Integer meProtein, Integer meSnf, Integer peakScc, Integer parity,
                      Integer DaysAtLact, Double prevLactPersistence, Double currLactPersistenceAtLact,
                      Integer daysToPeak, Double latePeakYield, Double earlyAvgFat, Double earlyAvgProtein,
                      Double earlyAvgMun, Double lastYieldDryOff, Double prevLactDryOffYield) {
        this.regNumber = regNumber;
        this.name = name;
        this.shortName = shortName;
        this.birthDate = birthDate;
        this.farmName = farmName;

        this.calvingDate = calvingDate;
        this.dryOffDate = dryOffDate;
        this.openDays = openDays;
        this.lastBreedingDate = lastBreedingDate;
        this.lastBreedingCount = lastBreedingCount;
        this.lastSemenCode = lastSemenCode;
        this.daysToFirstBreeding = daysToFirstBreeding;

        this.test_date = test_date;
        this.milkYield = milkYield;
        this.fatPct = fatPct;
        this.proteinPct = proteinPct;
        this.snfPct = snfPct;
        this.scc = scc;
        this.mun = mun;
        this.yield305 = yield305;
        this.fat305 = fat305;
        this.protein305 = protein305;
        this.snf305 = snf305;
        this.meYield = meYield;
        this.meFat = meFat;
        this.meProtein = meProtein;
        this.meSnf = meSnf;
        this.peakScc = peakScc;

        this.parity = parity;
        this.DaysAtLact = DaysAtLact;
        this.prevLactPersistence = prevLactPersistence;
        this.currLactPersistenceAtLact = currLactPersistenceAtLact;
        this.daysToPeak = daysToPeak;
        this.latePeakYield = latePeakYield;
        this.earlyAvgFat = earlyAvgFat;
        this.earlyAvgProtein = earlyAvgProtein;
        this.earlyAvgMun = earlyAvgMun;
        this.lastYieldDryOff = lastYieldDryOff;
        this.prevLactDryOffYield = prevLactDryOffYield;
    }


    //cow 와 breedingRecord join 테이블 생성자
    @QueryProjection
    public CowDataDto(String regNumber, String name, Integer shortName, LocalDate birthDate, String farmName,
                      LocalDate calvingDate, LocalDate dryOffDate, Integer openDays,
                      LocalDate lastBreedingDate, Integer lastBreedingCount, String lastSemenCode, Integer daysToFirstBreeding,
                      LocalDate test_date) {
        this.regNumber = regNumber;
        this.name = name;
        this.shortName = shortName;
        this.birthDate = birthDate;
        this.farmName = farmName;

        this.calvingDate = calvingDate;
        this.dryOffDate = dryOffDate;
        this.openDays = openDays;
        this.lastBreedingDate = lastBreedingDate;
        this.lastBreedingCount = lastBreedingCount;
        this.lastSemenCode = lastSemenCode;
        this.daysToFirstBreeding = daysToFirstBreeding;

        this.test_date = test_date;

    }

}