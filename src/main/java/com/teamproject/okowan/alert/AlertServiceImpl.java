package com.teamproject.okowan.alert;

import com.teamproject.okowan.common.ApiResponseDto;
import com.teamproject.okowan.security.UserDetailsImpl;
import com.teamproject.okowan.user.User;
import com.teamproject.okowan.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {
    private final AlertRepository alertRepository;

    private final UserService userService;

    /* 알림 전체 조회 */
    @Override
    public List<AlertResponseDto> getAlerts(UserDetailsImpl userDetails) {
        User user = userService.findUserById(userDetails.getUser().getId());

        return alertRepository.findByUserId(user.getId()).stream().map(AlertResponseDto::new).toList();
    }

    /* 알림 등록 */
    @Override
    public ApiResponseDto registAlerts(AlertRequestDto alertRequestDto) {
        User user = userService.findUserById(alertRequestDto.getWorkerId());

        Alert alert = new Alert(alertRequestDto);
        alert.setUser(user);

        alertRepository.save(alert);

        return new ApiResponseDto("알림 등록 성공", HttpStatus.OK.value());
    }

    /* 알림 삭제 */
    @Override
    public ApiResponseDto deleteAlerts(Long alertId, UserDetailsImpl userDetails) {
        Alert alert = findAlert(alertId);

        if (alert.getUser().getId() != userDetails.getUser().getId()) {
            throw new IllegalArgumentException("알림 대상이 아닙니다.");
        }

        alertRepository.delete(alert);

        return new ApiResponseDto("알림 삭제 성공", HttpStatus.OK.value());
    }

    /* 알림 찾기 */
    @Override
    public Alert findAlert(Long alertId) {
        return alertRepository.findById(alertId).orElseThrow(() -> new IllegalArgumentException("알림이 존재하지 않습니다."));
    }
}
