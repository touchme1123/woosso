package com.shpark.woosso.api.milk.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shpark.woosso.api.milk.domain.*;

import java.util.List;

public class CowRepositoryImpl implements CowRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CowRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    //농장의 소들 데이터 조회
    @Override
    public List<Cow> findCowsByFarmName(String farmName) {
        // Q클래스 인스턴스화
        QCow cow = QCow.cow;
        QBreedingRecord breedingRecord = QBreedingRecord.breedingRecord;
        QMilkRecord milkRecord = QMilkRecord.milkRecord;
        QLactationInfo lactationInfo = QLactationInfo.lactationInfo;

        return queryFactory
                .selectFrom(cow)
                .from(cow)
                .join(breedingRecord).on(breedingRecord.cow.eq(cow))
                .join(milkRecord).on(milkRecord.cow.eq(cow))
                .join(lactationInfo).on(lactationInfo.cow.eq(cow))
                .where(cow.farmName.eq(farmName))
                .fetch();
    }
}
