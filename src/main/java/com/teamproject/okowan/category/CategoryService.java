package com.teamproject.okowan.category;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.security.UserDetailsImpl;

import java.util.List;

public interface CategoryService {

    /* 전체 조회 */
    List<CategoryResponseDto> getCategorys(Long board_id, UserDetailsImpl userDetails);

    /* 카테고리 순서 이동 */
    ApiResponseDto moveCategory(Long category_id, Long boardId, String move, UserDetailsImpl userDetails);

    /* 카테고리 등록 */
    ApiResponseDto registCategory(Long board_id, CategoryRequestDto categoryRequestDto, UserDetailsImpl userDetails);

    /* 카테고리 수정 */
    ApiResponseDto updateCategory(Long category_id, CategoryRequestDto categoryRequestDto, UserDetailsImpl userDetails);

    /* 카테고리 삭제 */
    ApiResponseDto deleteCategory(Long category_id, UserDetailsImpl userDetails);


}
