package com.teamproject.okowan.alert;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.security.UserDetailsImpl;

import java.util.List;

public interface AlertService {

    /* 알림 조회 */
    List<AlertResponseDto> getAlerts(UserDetailsImpl userDetails);

    /* 알림 등록 */
    ApiResponseDto registAlerts(AlertRequestDto alertRequestDto);

    /* 알림 삭제 */
    ApiResponseDto deleteAlerts(Long alertId, UserDetailsImpl userDetails);

    /* 알림 찾기 */
    Alert findAlert(Long alertId);
}
