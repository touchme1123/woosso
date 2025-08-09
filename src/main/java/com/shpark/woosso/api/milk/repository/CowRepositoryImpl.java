package com.shpark.woosso.api.milk.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shpark.woosso.api.milk.domain.QBreedingRecord;
import com.shpark.woosso.api.milk.domain.QCow;
import com.shpark.woosso.api.milk.domain.QLactationInfo;
import com.shpark.woosso.api.milk.domain.QMilkRecord;
import com.shpark.woosso.api.milk.dto.CowDataDto;
import com.shpark.woosso.api.milk.dto.QCowDataDto;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class CowRepositoryImpl implements CowRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CowDataDto> findAllByFarmNameAndTestDate(String farmName, LocalDate test_date) {
        // Q클래스 인스턴스화
        QCow cow = QCow.cow;
        QBreedingRecord breedingRecord = QBreedingRecord.breedingRecord;
        QMilkRecord milkRecord = QMilkRecord.milkRecord;
        QLactationInfo lactationInfo = QLactationInfo.lactationInfo;

        return queryFactory
                .select(new QCowDataDto(
                        cow.regNumber,
                        cow.name,
                        cow.shortName,
                        cow.birthDate,
                        cow.farmName,

                        breedingRecord.calvingDate,
                        breedingRecord.dryOffDate,
                        breedingRecord.openDays,
                        breedingRecord.lastBreedingDate,
                        breedingRecord.lastBreedingCount,
                        breedingRecord.lastSemenCode,
                        breedingRecord.daysToFirstBreeding,

                        milkRecord.testDate,
                        milkRecord.milkYield,
                        milkRecord.fatPct,
                        milkRecord.proteinPct,
                        milkRecord.snfPct,
                        milkRecord.scc,
                        milkRecord.mun,
                        milkRecord.yield305,
                        milkRecord.fat305,
                        milkRecord.protein305,
                        milkRecord.snf305,
                        milkRecord.meYield,
                        milkRecord.meFat,
                        milkRecord.meProtein,
                        milkRecord.meSnf,
                        milkRecord.peakScc,

                        lactationInfo.parity,
                        lactationInfo.DaysAtLact,
                        lactationInfo.prevLactPersistence,
                        lactationInfo.currLactPersistenceAtLact,
                        lactationInfo.daysToPeak,
                        lactationInfo.latePeakYield,
                        lactationInfo.earlyAvgFat,
                        lactationInfo.earlyAvgProtein,
                        lactationInfo.earlyAvgMun,
                        lactationInfo.lastYieldDryOff,
                        lactationInfo.prevLactDryOffYield
                        ))
                .from(cow)
                .leftJoin(breedingRecord).on(breedingRecord.cow.eq(cow))
                .leftJoin(lactationInfo).on(lactationInfo.cow.eq(cow))
                .leftJoin(milkRecord).on(milkRecord.cow.eq(cow))
                .where(cow.farmName.eq(farmName))
                .where(breedingRecord.testDate.eq(test_date))
                .fetch();
    }
}
