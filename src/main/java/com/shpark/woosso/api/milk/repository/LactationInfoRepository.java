package com.shpark.woosso.api.milk.repository;

import com.shpark.woosso.api.milk.domain.LactationInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface LactationInfoRepository extends JpaRepository<LactationInfo, Long> {

    Optional<LactationInfo> findByCow_RegNumberAndTestDate(String regNumber, LocalDate testDate);
}
