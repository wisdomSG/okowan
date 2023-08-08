package com.teamproject.okowan.category;


import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.board.Board;
import com.teamproject.okowan.board.BoardRepository;
import com.teamproject.okowan.security.UserDetailsImpl;
import com.teamproject.okowan.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;

    /* 카테고리 전체 조회 */
    @Override
    public List<CategoryResponseDto> getCategorys(Long board_id, UserDetailsImpl userDetails) {
        User user = checkUser(userDetails);
        Board board = findByIdBoard(board_id);

        List<CategoryResponseDto> categoryResponseDtoList = board.getCategoryList().stream().map(CategoryResponseDto::new).toList();

        return categoryResponseDtoList;
    }

    /* 카테고리 순서 이동 */
    @Transactional
    @Override
    public ApiResponseDto moveCategory(Long category_id, Long boardId, String move, UserDetailsImpl userDetails) {
        User user = checkUser(userDetails);
        Board board = findByIdBoard(boardId);
        Category category = findByIdCategory(category_id);

        List<Category> categoryList = board.getCategoryList();

        Integer pos = categoryList.indexOf(category);

        if (move.equals("up")) {
            if (pos + 1 >= board.getCategoryList().size()) {
                categoryList.add(0, categoryList.remove((int) pos));
            } else {
                categoryList.add(pos + 1, categoryList.remove((int) pos));
            }
        } else if (move.equals("down")) {
            if (pos <= 0) {
                categoryList.add(categoryList.remove((int) pos));
            } else {
                categoryList.add(pos - 1, categoryList.remove((int) pos));
            }
        } else {
            throw new IllegalArgumentException("올바르지 않은 명령어입니다.");
        }

        return new ApiResponseDto("카테고리 순서 이동 성공", HttpStatus.OK.value());
    }

    /* 카테고리 등록 */
    @Override
    public ApiResponseDto registCategory(Long board_id, CategoryRequestDto categoryRequestDto, UserDetailsImpl userDetails) {
        User user = checkUser(userDetails);

        Category category = new Category(categoryRequestDto);
        Board board = findByIdBoard(board_id);
        category.setBoard(board);

        categoryRepository.save(category);

        return new ApiResponseDto("카테고리 등록 성공", HttpStatus.OK.value());
    }

    /* 카테고리 수정 */
    @Transactional
    @Override
    public ApiResponseDto updateCategory(Long category_id, CategoryRequestDto categoryRequestDto, UserDetailsImpl userDetails) {
        User user = checkUser(userDetails);

        Category category = findByIdCategory(category_id);
        Board board = category.getBoard();

        Integer pos = board.getCategoryList().indexOf(category);
        if(pos < 0) {
            throw new IllegalArgumentException("존재하지 않는 카테고리입니다.");
        }

        category.setTitle(categoryRequestDto.getTitle());

        board.getCategoryList().set(pos,category);

        return new ApiResponseDto("카테고리 수정 성공", HttpStatus.OK.value());
    }

    /* 카테고리 삭제 */
    @Transactional
    @Override
    public ApiResponseDto deleteCategory(Long category_id, UserDetailsImpl userDetails) {
        User user = checkUser(userDetails);

        Category category = findByIdCategory(category_id);

        categoryRepository.delete(category);

        return new ApiResponseDto("카테고리 삭제 성공", HttpStatus.OK.value());
    }

    /* Jwt UserDetails Null Check */
    public User checkUser(UserDetailsImpl userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("올바른 사용자가 아닙니다");
        }

        return userDetails.getUser();
    }

    /* Find Category By Id */
    public Category findByIdCategory(Long category_id) {
        return categoryRepository.findById(category_id).orElseThrow(() ->
                new IllegalArgumentException("카테고리가 존재하지 않습니다."));
    }

    /* Find Board By Id */
    public Board findByIdBoard(Long board_id) {
        return boardRepository.findById(board_id).orElseThrow(() ->
                new IllegalArgumentException("보드가 존재하지 않습니다."));
    }
}
