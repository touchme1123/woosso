package com.shpark.woosso.api.milk.repository;

import com.shpark.woosso.api.milk.domain.MilkRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface MilkRecordRepository extends JpaRepository<MilkRecord, Long> {

    Optional<MilkRecord> findByCow_RegNumberAndTestDate(String regNumber, LocalDate testDate);
}
