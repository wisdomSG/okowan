package com.teamproject.okowan.alert;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/okw/alerts")
@RequiredArgsConstructor
public class AlertController {
    private final AlertServiceImpl alertService;

    /* 알림 전체 조회 */
    @GetMapping("")
    public List<AlertResponseDto> getAlerts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return alertService.getAlerts(userDetails);
    }


    /* 알림 등록 */
    @PostMapping("")
    public ResponseEntity<ApiResponseDto> registAlerts(@RequestBody AlertRequestDto alertRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = alertService.registAlerts(alertRequestDto, userDetails);
        return ResponseEntity.ok().body(apiResponseDto);
    }

    /* 알림 삭제 */
    @DeleteMapping("/{alertId}")
    public ResponseEntity<ApiResponseDto> deleteAlerts(@PathVariable Long alertId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = alertService.deleteAlerts(alertId, userDetails);
        return ResponseEntity.ok().body(apiResponseDto);
    }
}
