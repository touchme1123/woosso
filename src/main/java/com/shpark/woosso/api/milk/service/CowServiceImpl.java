package com.shpark.woosso.api.milk.service;

import com.shpark.woosso.api.milk.domain.BreedingRecord;
import com.shpark.woosso.api.milk.domain.Cow;
import com.shpark.woosso.api.milk.domain.LactationInfo;
import com.shpark.woosso.api.milk.domain.MilkRecord;
import com.shpark.woosso.api.milk.dto.CowDataDto;
import com.shpark.woosso.api.milk.repository.BreedingRecordRepository;
import com.shpark.woosso.api.milk.repository.CowRepository;
import com.shpark.woosso.api.milk.repository.LactationInfoRepository;
import com.shpark.woosso.api.milk.repository.MilkRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;


@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class CowServiceImpl implements CowService {

    // JPA Repository 주입
    private final CowRepository cowRepository;
    private final BreedingRecordRepository breedingRecordRepository;
    private final MilkRecordRepository milkRecordRepository;
    private final LactationInfoRepository lactationInfoRepository;

    @Override
    public List<Map<String, Object>> cowDataUpload(MultipartFile file, String farmName) throws Exception {
        log.debug("CowServiceImpl.cowDataUpload Start");

        List<CowDataDto> dataList = new ArrayList<>();

        try (InputStream excelFile = file.getInputStream()) {
            Workbook workbook;
            workbook = WorkbookFactory.create(excelFile);

            // 파일 확장자를 통해 Workbook 타입 결정 및 지원하지 않는 파일 형식 예외 처리
            if (file.getOriginalFilename().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(excelFile);
            } else if (file.getOriginalFilename().endsWith(".xls")) {
                throw new IllegalArgumentException("현재 .xls 파일은 지원하지 않습니다. .xlsx 파일을 업로드해주세요.");
            } else {
                throw new IllegalArgumentException("지원하지 않는 파일 형식입니다. 엑셀 파일(.xlsx)을 업로드해주세요.");
            }

            // 첫 번째 시트 가져오기
            Sheet sheet = workbook.getSheetAt(0);

            // 데이터 행 반복 (첫 번째 행은 헤더이므로 인덱스 1부터 시작)
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row currentRow = sheet.getRow(rowNum);

                if (currentRow == null) { // 빈 행 스킵
                    continue;
                }

                CowDataDto dto = new CowDataDto();

                try {
                    // 엑셀 셀 데이터를 DTO에 매핑 (컬럼 순서와 타입에 주의)
                    dto.setName(getCellValueAsString(currentRow.getCell(0)));
                    dto.setShortName(getCellValueAsInteger(currentRow.getCell(1)));
                    dto.setRegNumber(getCellValueAsString(currentRow.getCell(2)));
                    dto.setBirthDate(getCellValueAsLocalDate(currentRow.getCell(3)));
                    dto.setParity(getCellValueAsInteger(currentRow.getCell(4)));
                    dto.setTest_date(getCellValueAsLocalDate(currentRow.getCell(5)));
                    dto.setCalvingDate(getCellValueAsLocalDate(currentRow.getCell(6)));
                    dto.setDryOffDate(getCellValueAsLocalDate(currentRow.getCell(7)));
                    dto.setDaysAtLact(getCellValueAsInteger(currentRow.getCell(8)));
                    dto.setMilkYield(getCellValueAsDouble(currentRow.getCell(9)));
                    dto.setFatPct(getCellValueAsDouble(currentRow.getCell(10)));
                    dto.setProteinPct(getCellValueAsDouble(currentRow.getCell(11)));
                    dto.setSnfPct(getCellValueAsDouble(currentRow.getCell(12)));
                    dto.setScc(getCellValueAsInteger(currentRow.getCell(13)));
                    dto.setMun(getCellValueAsDouble(currentRow.getCell(14)));
                    dto.setYield305(getCellValueAsInteger(currentRow.getCell(15)));
                    dto.setFat305(getCellValueAsInteger(currentRow.getCell(16)));
                    dto.setProtein305(getCellValueAsInteger(currentRow.getCell(17)));
                    dto.setSnf305(getCellValueAsInteger(currentRow.getCell(18)));
                    dto.setMeYield(getCellValueAsInteger(currentRow.getCell(19)));
                    dto.setMeFat(getCellValueAsInteger(currentRow.getCell(20)));
                    dto.setMeProtein(getCellValueAsInteger(currentRow.getCell(21)));
                    dto.setMeSnf(getCellValueAsInteger(currentRow.getCell(22)));

                    dto.setOpenDays(getCellValueAsInteger(currentRow.getCell(28)));
                    dto.setLastBreedingDate(getCellValueAsLocalDate(currentRow.getCell(29)));
                    dto.setLastBreedingCount(getCellValueAsInteger(currentRow.getCell(30)));
                    dto.setLastSemenCode(getCellValueAsString(currentRow.getCell(31)));

                    dto.setDaysToFirstBreeding(getCellValueAsInteger(currentRow.getCell(33)));
                    dto.setPrevLactPersistence(getCellValueAsDouble(currentRow.getCell(34)));
                    dto.setCurrLactPersistenceAtLact(getCellValueAsDouble(currentRow.getCell(35)));
                    dto.setDaysToPeak(getCellValueAsInteger(currentRow.getCell(36)));
                    dto.setLatePeakYield(getCellValueAsDouble(currentRow.getCell(37)));
                    dto.setEarlyAvgFat(getCellValueAsDouble(currentRow.getCell(38)));
                    dto.setEarlyAvgProtein(getCellValueAsDouble(currentRow.getCell(39)));
                    dto.setEarlyAvgMun(getCellValueAsDouble(currentRow.getCell(40)));
                    dto.setPeakScc(getCellValueAsInteger(currentRow.getCell(41)));
                    dto.setLastYieldDryOff(getCellValueAsDouble(currentRow.getCell(42)));
                    dto.setPrevLactDryOffYield(getCellValueAsDouble(currentRow.getCell(43)));

                    dataList.add(dto);

                } catch (Exception e) {
                    log.debug(e.getMessage());
                    return List.of(Map.of("result", "failed"));
                }
            }

            // DTO 리스트를 Cow 엔티티 리스트로 변환
            List<Cow> cowsList = dataList.stream()
                    .map(dto -> {
                        Cow cow = new Cow();

                        cow.setRegNumber(dto.getRegNumber());
                        cow.setName(dto.getName());
                        cow.setShortName(dto.getShortName());
                        cow.setBirthDate(dto.getBirthDate());
                        cow.setFarmName(farmName);

                        return cow;
                    }).collect(Collectors.toList());

            List<Cow> savedCows = cowRepository.saveAll(cowsList);
            Map<String, Cow> savedCowMap = savedCows.stream()
                    .collect(Collectors.toMap(Cow::getRegNumber, cow -> cow));

            // DTO 리스트를 BreedingRecord 엔티티 리스트로 변환
            List<BreedingRecord> breedingRecordList = dataList.stream().map(dto -> {
                BreedingRecord breedingRecord = new BreedingRecord();
                Cow linkedCow = savedCowMap.get(dto.getRegNumber());

                breedingRecord.setCow(linkedCow);
                breedingRecord.setTestDate(dto.getTest_date());
                breedingRecord.setCalvingDate(dto.getCalvingDate());
                breedingRecord.setDryOffDate(dto.getDryOffDate());
                breedingRecord.setOpenDays(dto.getOpenDays());
                breedingRecord.setLastBreedingDate(dto.getLastBreedingDate());
                breedingRecord.setLastBreedingCount(dto.getLastBreedingCount());
                breedingRecord.setLastSemenCode(dto.getLastSemenCode());
                breedingRecord.setDaysToFirstBreeding(dto.getDaysToFirstBreeding());

                return breedingRecord;
            }).collect(Collectors.toList());

            breedingRecordRepository.saveAll(breedingRecordList);

            // DTO 리스트를 milkRecord 엔티티 리스트로 변환
            List<MilkRecord> milkRecordList = dataList.stream().map(dto -> {
                MilkRecord milkRecord = new MilkRecord();
                Cow linkedCow = savedCowMap.get(dto.getRegNumber());

                milkRecord.setCow(linkedCow);
                milkRecord.setTestDate(dto.getTest_date());
                milkRecord.setMilkYield(dto.getMilkYield());
                milkRecord.setFatPct(dto.getFatPct());
                milkRecord.setProteinPct(dto.getProteinPct());
                milkRecord.setSnfPct(dto.getSnfPct());
                milkRecord.setScc(dto.getScc());
                milkRecord.setMun(dto.getMun());
                milkRecord.setYield305(dto.getYield305());
                milkRecord.setFat305(dto.getFat305());
                milkRecord.setProtein305(dto.getProtein305());
                milkRecord.setSnf305(dto.getSnf305());
                milkRecord.setMeYield(dto.getMeYield());
                milkRecord.setMeFat(dto.getMeFat());
                milkRecord.setMeProtein(dto.getMeProtein());
                milkRecord.setMeSnf(dto.getMeSnf());
                milkRecord.setPeakScc(dto.getPeakScc());

                return milkRecord;

            }).collect(Collectors.toList());

            milkRecordRepository.saveAll(milkRecordList);

            // DTO 리스트를 lactationInfo 엔티티 리스트로 변환
            List<LactationInfo> lactationInfoList = dataList.stream().map(dto -> {
                LactationInfo lactationInfo = new LactationInfo();
                Cow linkedCow = savedCowMap.get(dto.getRegNumber());

                lactationInfo.setCow(linkedCow);
                lactationInfo.setTestDate(dto.getTest_date());
                lactationInfo.setParity(dto.getParity());
                lactationInfo.setDaysAtLact(dto.getDaysAtLact());
                lactationInfo.setPrevLactPersistence(dto.getPrevLactPersistence());
                lactationInfo.setCurrLactPersistenceAtLact(dto.getCurrLactPersistenceAtLact());
                lactationInfo.setDaysToPeak(dto.getDaysToPeak());
                lactationInfo.setLatePeakYield(dto.getLatePeakYield());
                lactationInfo.setEarlyAvgFat(dto.getEarlyAvgFat());
                lactationInfo.setEarlyAvgProtein(dto.getEarlyAvgProtein());
                lactationInfo.setEarlyAvgMun(dto.getEarlyAvgMun());
                lactationInfo.setLastYieldDryOff(dto.getLastYieldDryOff());
                lactationInfo.setPrevLactDryOffYield(dto.getPrevLactDryOffYield());

                return lactationInfo;
            }).collect(Collectors.toList());

            lactationInfoRepository.saveAll(lactationInfoList);
        }

        log.debug("CowServiceImpl.cowDataUpload End");
        return List.of(Map.of("data", dataList));
    }

    // --- 헬퍼 메서드들: 셀 값을 타입별로 안전하게 가져오기 ---

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return null;
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCachedFormulaResultType() == CellType.STRING ? cell.getStringCellValue() : String.valueOf(cell.getNumericCellValue());
            default -> null;
        };
    }

    private Integer getCellValueAsInteger(Cell cell) {
        if (cell == null) return null;
        return switch (cell.getCellType()) {
            case NUMERIC -> (int) cell.getNumericCellValue();
            case STRING -> {
                try {
                    yield Integer.parseInt(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    yield null;
                }
            }
            default -> null;
        };
    }

    private Double getCellValueAsDouble(Cell cell) {
        if (cell == null) return null;
        return switch (cell.getCellType()) {
            case NUMERIC -> cell.getNumericCellValue();
            case STRING -> {
                try {
                    yield Double.parseDouble(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    yield null;
                }
            }
            default -> null;
        };
    }

    private LocalDate getCellValueAsLocalDate(Cell cell) {
        if (cell == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ROOT);

        if (cell.getCellType() == CellType.STRING) {
            String cellValue = cell.getStringCellValue().trim();
            try {
                LocalDate parsedDate = LocalDate.parse(cellValue, formatter);
                return parsedDate;
            } catch (DateTimeParseException e) {
            }
        }

        if (DateUtil.isCellDateFormatted(cell)) {
            return cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            String numericString = String.valueOf((long) cell.getNumericCellValue());
            try {
                LocalDate parsedDate = LocalDate.parse(numericString, formatter);
                return parsedDate;
            } catch (DateTimeParseException e) {
                return DateUtil.getJavaDate(cell.getNumericCellValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
        }

        return null;
    }
}