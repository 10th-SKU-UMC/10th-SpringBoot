package com.example.week08.domain.review.controller;

import com.example.week08.domain.review.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired MockMvc mockMvc;
    @MockitoBean ReviewService reviewService;

    // ObjectMapper 직접 생성 (빈으로 등록 안 되는 경우 대비)
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("리뷰 작성 - rating 누락 시 400 반환")
    void createReview_rating_누락_검증실패() throws Exception {
        // rating 없이 요청
        Map<String, Object> body = Map.of(
                "content", "맛있어요!"
        );

        mockMvc.perform(post("/missions/1/reviews")
                        .param("memberId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.code").value("COMMON400_1"))
                .andExpect(jsonPath("$.result.rating").value("별점은 필수입니다."));
    }

    @Test
    @DisplayName("리뷰 작성 - content 빈값 시 400 반환")
    void createReview_content_빈값_검증실패() throws Exception {
        Map<String, Object> body = Map.of(
                "rating", 4,
                "content", ""
        );

        mockMvc.perform(post("/missions/1/reviews")
                        .param("memberId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.result.content").value("리뷰 내용은 필수입니다."));
    }

    @Test
    @DisplayName("리뷰 작성 - rating 범위 초과(6점) 시 400 반환")
    void createReview_rating_범위초과_검증실패() throws Exception {
        Map<String, Object> body = Map.of(
                "rating", 6,
                "content", "맛있어요!"
        );

        mockMvc.perform(post("/missions/1/reviews")
                        .param("memberId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.result.rating").value("별점은 최대 5점입니다."));
    }
}
