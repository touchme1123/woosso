package com.shpark.woosso.api.milk.repository;

import com.shpark.woosso.api.milk.domain.LactationInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LactationInfoRepository extends JpaRepository<LactationInfo, Long> {

}
