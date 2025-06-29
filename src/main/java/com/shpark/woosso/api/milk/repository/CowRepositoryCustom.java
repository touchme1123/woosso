package com.shpark.woosso.api.milk.repository;

import com.shpark.woosso.api.milk.domain.Cow;

import java.util.List;


//QueryDSL 사용
public interface CowRepositoryCustom {

    List<Cow> findCowsByFarmName(String farmName);
}
