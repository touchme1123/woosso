package com.shpark.woosso.api.milk.repository;

import com.shpark.woosso.api.milk.domain.MilkRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilkRecordRepository extends JpaRepository<MilkRecord, Long> {
}
