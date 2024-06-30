package com.study.hotelland.statistic.web.controller;

import com.study.hotelland.statistic.service.StatisticCSVService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotelland/statistic")
@RequiredArgsConstructor
public class StatisticCSVController {

    private final StatisticCSVService statisticCSVService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<byte[]> getStatistics() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDispositionFormData("attachment", "statistic_hotels.csv");

        byte[] statistics = statisticCSVService.getStatisticsInCSV();

        return ResponseEntity.ok().headers(httpHeaders).body(statistics);
    }

}
