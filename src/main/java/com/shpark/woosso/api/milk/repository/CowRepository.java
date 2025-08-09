package com.shpark.woosso.api.milk.repository;

import com.shpark.woosso.api.milk.domain.Cow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CowRepository extends JpaRepository<Cow, String>, CowRepositoryCustom {
}
