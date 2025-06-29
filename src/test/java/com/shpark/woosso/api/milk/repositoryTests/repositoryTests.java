package com.shpark.woosso.api.milk.repositoryTests;

import com.shpark.woosso.api.milk.repository.CowRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class repositoryTests {

    @Autowired
    CowRepository cowRepository;

    @Test
    public void findCowsByFarmName() {

        System.out.println(cowRepository.findCowsByFarmName("s"));
    }
}
