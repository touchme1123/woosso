package com.shpark.woosso.api.milk.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface CowService {

    List<Map<String, Object>> cowDataUpload(MultipartFile file, String farmName) throws Exception;

}
