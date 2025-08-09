package com.shpark.woosso.api.milk.repository;

import com.shpark.woosso.api.milk.domain.Cow;
import com.shpark.woosso.api.milk.domain.LactationInfo;
import com.shpark.woosso.api.milk.dto.CowDataDto;

import java.time.LocalDate;
import java.util.List;


//QueryDSL 사용
public interface CowRepositoryCustom {

    List<CowDataDto> findAllByFarmNameAndTestDate(String farmName, LocalDate test_date);




}
