package com.shpark.woosso.api.milk.controller;

import com.shpark.woosso.api.milk.service.CowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/cow")
public class CowController {

    private final CowService cowService;

    @PostMapping(value = "/upload")
    public List<Map<String, Object>> cowDataUpload(MultipartFile file, String farmName) {
        log.debug("CowController.cowDataUpload Start");
        List<Map<String, Object>> resultData = new ArrayList<>();

        try {
            resultData = cowService.cowDataUpload(file, farmName);
        } catch (Exception e) {
            throw new RuntimeException(e);

        }

        log.debug("CowController.cowDataUpload End");
        return List.of(Map.of("data", resultData));
    }



}
