package com.clb.cliembing.communication.controller;

import com.clb.cliembing.communication.dto.CommuManageDto;
import com.clb.cliembing.communication.service.CommuManageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/communications")
@RequiredArgsConstructor
@Tag(name = "공지사항 / 게시글 / 댓글 관리", description = "공지사항, 게시글 및 댓글 관련 API")
public class CommuManageController {

    private final CommuManageService commuManageService;

    @GetMapping("/posts")
    @Operation(
            summary = "공지사항 또는 게시글을 조회하는 API",
            description = "크루 ID와 게시글 타입(notice 또는 post)을 전달하면 해당 게시글 리스트를 반환합니다."
    )
    public List<CommuManageDto.PostSearchOutDto> getPosts(@Valid CommuManageDto.PostSearchInDto postSearchInDto) {
        return commuManageService.getPosts(postSearchInDto);
    }

    @PostMapping("/posts")
    @Operation(
            summary = "공지사항 또는 게시글을 생성하는 API",
            description = "공지사항 또는 게시글을 생성합니다."
    )
    public CommuManageDto.PostCreateOutDto createPost(@Valid @RequestBody CommuManageDto.PostCreateInDto postCreateInDto) {
        return commuManageService.createPost(postCreateInDto);
    }

    @GetMapping("/newsfeed")
    @Operation(
            summary = "뉴스피드 조회 API",
            description = "크루 ID를 전달하면 해당 크루의 최신 공지 및 게시글 리스트를 반환합니다."
    )
    public List<CommuManageDto.NewsfeedOutDto> getNewsfeed(@RequestParam Long crewId) {
        return commuManageService.getNewsfeed(crewId);
    }

    @PostMapping("/comments")
    @Operation(
            summary = "댓글 생성 API",
            description = "부모 게시글 ID와 작성자 ID, 댓글 내용을 전달하여 댓글을 생성합니다."
    )
    public CommuManageDto.CommentCreateOutDto createComment(@Valid @RequestBody CommuManageDto.CommentCreateInDto commentCreateInDto) {
        return commuManageService.createComment(commentCreateInDto);
    }
}
