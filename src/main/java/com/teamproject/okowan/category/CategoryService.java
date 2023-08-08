package com.teamproject.okowan.category;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.security.UserDetailsImpl;

import java.util.List;

public interface CategoryService {

    /* 전체 조회 */
    List<CategoryResponseDto> getCategories(Long boardId, UserDetailsImpl userDetails);

    /* 카테고리 순서 이동 */
    ApiResponseDto moveCategory(Long categoryId, Long boardId, String move, UserDetailsImpl userDetails);

    /* 카테고리 등록 */
    ApiResponseDto registCategory(Long boardId, CategoryRequestDto categoryRequestDto, UserDetailsImpl userDetails);

    /* 카테고리 수정 */
    ApiResponseDto updateCategory(Long categoryId, CategoryRequestDto categoryRequestDto, UserDetailsImpl userDetails);

    /* 카테고리 삭제 */
    ApiResponseDto deleteCategory(Long categoryId, UserDetailsImpl userDetails);


    /* 카테고리 찾기 */
    Category findCategory(Long categoryId);
}
