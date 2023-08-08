package com.teamproject.okowan.category;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/okw/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServiceImpl categoryService;

    /* 카테고리 전체 조회 */
    @GetMapping("/{boardId}")
    public List<CategoryResponseDto> getCategories(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return categoryService.getCategories(boardId, userDetails);
    }

    /*
        카테고리 순서 이동
        - Board > CategoryList의 index 조정
        api : /okw/category/{categoryId}/move?boardId=?&move="up" or "down"
     */
    @PostMapping("/{categoryId}/move")
    public ResponseEntity<ApiResponseDto> moveCategory(@PathVariable Long categoryId, @RequestParam Long boardId, @RequestParam String move, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = categoryService.moveCategory(categoryId, boardId, move, userDetails);
        return ResponseEntity.ok().body(apiResponseDto);
    }

    /* 카테고리 등록 */
    @PostMapping("/{boardId}")
    public ResponseEntity<ApiResponseDto> registCategory(@PathVariable Long boardId, @RequestBody CategoryRequestDto categoryRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = categoryService.registCategory(boardId, categoryRequestDto, userDetails);
        return ResponseEntity.ok().body(apiResponseDto);
    }

    /* 카테고리 수정 */
    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponseDto> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryRequestDto categoryRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = categoryService.updateCategory(categoryId, categoryRequestDto, userDetails);
        return ResponseEntity.ok().body(apiResponseDto);
    }

    /* 카테고리 삭제 */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseDto> deleteCategory(@PathVariable Long categoryId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = categoryService.deleteCategory(categoryId, userDetails);
        return ResponseEntity.ok().body(apiResponseDto);
    }
}
