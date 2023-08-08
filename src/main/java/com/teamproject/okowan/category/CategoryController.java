package com.teamproject.okowan.category;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.entity.UserBoard;
import com.teamproject.okowan.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/okw")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServiceImpl categoryService;

    /* 카테고리 전체 조회 */
    @GetMapping("/categorys/{board_id}")
    public List<CategoryResponseDto> getCategorys(@PathVariable Long board_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return categoryService.getCategorys(board_id, userDetails);
    }

    /*
        카테고리 순서 이동
        - Board > CategoryList의 index 조정
        api : /okw/category/{category_id}/move?boardId=?&move="up" or "down"
     */
    @PostMapping("/categorys/{category_id}/move")
    public ResponseEntity<ApiResponseDto> moveCategory(@PathVariable Long category_id, @RequestParam Long boardId, @RequestParam String move, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = categoryService.moveCategory(category_id, boardId, move, userDetails);
        return ResponseEntity.ok().body(apiResponseDto);
    }

    /* 카테고리 등록 */
    @PostMapping("/categorys/{board_id}")
    public ResponseEntity<ApiResponseDto> registCategory(@PathVariable Long board_id, @RequestBody CategoryRequestDto categoryRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = categoryService.registCategory(board_id, categoryRequestDto, userDetails);
        return ResponseEntity.ok().body(apiResponseDto);
    }

    /* 카테고리 수정 */
    @PutMapping("/categorys/{category_id}")
    public ResponseEntity<ApiResponseDto> updateCategory(@PathVariable Long category_id, @RequestBody CategoryRequestDto categoryRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = categoryService.updateCategory(category_id, categoryRequestDto, userDetails);
        return ResponseEntity.ok().body(apiResponseDto);
    }

    /* 카테고리 삭제 */
    @DeleteMapping("/categorys/{category_id}")
    public ResponseEntity<ApiResponseDto> deleteCategory(@PathVariable Long category_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = categoryService.deleteCategory(category_id, userDetails);
        return ResponseEntity.ok().body(apiResponseDto);
    }
}
