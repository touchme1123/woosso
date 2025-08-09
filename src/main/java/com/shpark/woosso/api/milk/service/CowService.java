package com.shpark.woosso.api.milk.service;

import com.shpark.woosso.api.milk.dto.CowDataDto;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface CowService {

    List<Map<String, Object>> cowDataUpload(MultipartFile file, String farmName) throws Exception;

    List<CowDataDto> findAllByFarmNameAndTestDate(String farmName, LocalDate testDate);
}
