package com.shpark.woosso.api.milk.repository;

import com.shpark.woosso.api.milk.domain.BreedingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface BreedingRecordRepository extends JpaRepository<BreedingRecord, Long> {

    Optional<BreedingRecord> findByCow_RegNumberAndTestDate(String regNumber, LocalDate testDate);
}
