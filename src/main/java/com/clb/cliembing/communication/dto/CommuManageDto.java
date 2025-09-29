package com.clb.cliembing.communication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Getter
public class CommuManageDto {

    @Getter
    @Builder
    @Schema(description = "공지/게시글 조회 요청 DTO")
    public static class PostSearchInDto {

        @Schema(description = "크루 ID", example = "101")
        @NotNull
        private Long crewId;

        @Schema(description = "게시글 타입", example = "notice", allowableValues = {"notice", "post"})
        @NotBlank
        private String type;
    }

    @Getter
    @Builder
    @Schema(description = "공지/게시글 조회 응답 DTO")
    public static class PostSearchOutDto {

        @Schema(description = "게시글 ID", example = "1")
        private Long communicationId;

        @Schema(description = "제목", example = "다음 주 정모 일정 안내")
        private String title;

        @Schema(description = "내용", example = "일정은 다음 주 토요일입니다.")
        private String content;

        @Schema(description = "작성자 이름", example = "Yoyo")
        private String createdBy;

        @Schema(description = "작성 시간", example = "2025-09-21T10:30:00")
        private Timestamp createdAt;

        @Schema(description = "게시글 타입", example = "notice", allowableValues = {"notice", "post"})
        private String type;

        @Schema(description = "상단 고정 여부", example = "true")
        private boolean isPinned;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "공지/게시글 생성 요청 DTO")
    public static class PostCreateInDto {

        @Schema(description = "크루 ID", example = "101")
        @NotNull
        private Long crewId;

        @Schema(description = "작성자 ID", example = "202")
        @NotNull
        private Long userId;

        @Schema(description = "제목", example = "정모 일정 안내", maxLength = 100)
        @NotBlank
        private String title;

        @Schema(description = "내용", example = "다음 주 토요일 2시에 만나요.", maxLength = 1000)
        @NotBlank
        private String content;

        @Schema(description = "게시글 타입", example = "notice", allowableValues = {"notice", "post"})
        @NotBlank
        private String type;

        @Schema(description = "상단 고정 여부", example = "false")
        private boolean isPinned;
    }

    @Getter
    @Builder
    @Schema(description = "공지/게시글 생성 응답 DTO")
    public static class PostCreateOutDto {

        @Schema(description = "게시글 ID", example = "1")
        private Long communicationId;

        @Schema(description = "메시지", example = "게시글이 성공적으로 작성되었습니다.")
        private String message;
    }

    @Getter
    @Builder
    @Schema(description = "뉴스피드 조회 응답 DTO")
    public static class NewsfeedOutDto {

        @Schema(description = "게시글 ID", example = "1")
        private Long communicationId;

        @Schema(description = "제목", example = "이번 주 모임 후기")
        private String title;

        @Schema(description = "내용", example = "재미있었어요!")
        private String content;

        @Schema(description = "작성자 이름", example = "Yoyo")
        private String createdBy;

        @Schema(description = "작성일시", example = "2025-09-20T16:00:00")
        private Timestamp createdAt;

        @Schema(description = "게시글 타입", example = "post", allowableValues = {"notice", "post"})
        private String type;

        @Schema(description = "댓글 수", example = "5")
        private int commentCount;

        @Schema(description = "좋아요 수", example = "12")
        private int likeCount;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "댓글 작성 요청 DTO")
    public static class CommentCreateInDto {

        @Schema(description = "부모 게시글 ID", example = "1")
        @NotNull
        private Long parentId;

        @Schema(description = "작성자 ID", example = "202")
        @NotNull
        private Long userId;

        @Schema(description = "댓글 내용", example = "재밌었어요!", maxLength = 300)
        @NotBlank
        private String content;
    }

    @Getter
    @Builder
    @Schema(description = "댓글 작성 응답 DTO")
    public static class CommentCreateOutDto {

        @Schema(description = "댓글 ID", example = "10")
        private Long commentId;

        @Schema(description = "메시지", example = "댓글이 등록되었습니다.")
        private String message;
    }

    @Getter
    @Builder
    @Schema(description = "댓글 조회 응답 DTO")
    public static class CommentOutDto {

        @Schema(description = "댓글 ID", example = "10")
        private Long commentId;

        @Schema(description = "부모 게시글 ID", example = "1")
        private Long parentId;

        @Schema(description = "댓글 내용", example = "정말 즐거운 시간이었어요")
        private String content;

        @Schema(description = "작성자 이름", example = "Yoyo")
        private String createdBy;

        @Schema(description = "작성일시", example = "2025-09-21T12:30:00")
        private Timestamp createdAt;
    }
}
