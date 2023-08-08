package com.teamproject.okowan.category;


import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.board.Board;
import com.teamproject.okowan.board.BoardRepository;
import com.teamproject.okowan.board.BoardServiceImpl;
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
    private final BoardService boardService;
    private final CategoryRepository categoryRepository;

    /* 카테고리 전체 조회 */
    @Override
    public List<CategoryResponseDto> getCategories(Long boardId, UserDetailsImpl userDetails) {
        User user = checkUser(userDetails);
        Board board = boardService.findBoard(boardId);

        return board.getCategoryList().stream().map(CategoryResponseDto::new).toList();
    }

    /* 카테고리 순서 이동 */
    @Transactional
    @Override
    public ApiResponseDto moveCategory(Long categoryId, Long boardId, String move, UserDetailsImpl userDetails) {
        User user = checkUser(userDetails);
        Board board = boardService.findBoard(boardId);
        Category category = findCategory(categoryId);

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
    public ApiResponseDto registCategory(Long boardId, CategoryRequestDto categoryRequestDto, UserDetailsImpl userDetails) {
        User user = checkUser(userDetails);

        Category category = new Category(categoryRequestDto);
        Board board = boardService.findBoard(boardId);
        category.setBoard(board);

        categoryRepository.save(category);

        return new ApiResponseDto("카테고리 등록 성공", HttpStatus.OK.value());
    }

    /* 카테고리 수정 */
    @Transactional
    @Override
    public ApiResponseDto updateCategory(Long categoryId, CategoryRequestDto categoryRequestDto, UserDetailsImpl userDetails) {
        User user = checkUser(userDetails);

        Category category = findCategory(categoryId);
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
    public ApiResponseDto deleteCategory(Long categoryId, UserDetailsImpl userDetails) {
        User user = checkUser(userDetails);

        Category category = findCategory(categoryId);

        categoryRepository.delete(category);

        return new ApiResponseDto("카테고리 삭제 성공", HttpStatus.OK.value());
    }

    /* 카테고리 찾기 */
    @Override
    public Category findCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("카테고리가 존재하지 않습니다."));
    }

    /* Jwt UserDetails Null Check */
    public User checkUser(UserDetailsImpl userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("올바른 사용자가 아닙니다");
        }

        return userDetails.getUser();
    }
}
