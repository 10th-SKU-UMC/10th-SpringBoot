package com.example.week07.domain.mission.controller;

import com.example.week07.domain.mission.service.MissionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MissionController.class)
class MissionControllerTest {

    @Autowired MockMvc mockMvc;
    @MockitoBean MissionService missionService;

    // LocalDate 직렬화를 위해 JavaTimeModule 등록
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    @DisplayName("가게 미션 생성 - deadline 누락 시 400 반환")
    void createMission_deadline_누락_검증실패() throws Exception {
        // deadline 없이 요청
        Map<String, Object> body = Map.of(
                "point", 500,
                "conditional", "12,000원 이상 식사"
        );

        mockMvc.perform(post("/api/v1/stores/1/missions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.code").value("COMMON400_1"))
                .andExpect(jsonPath("$.result.deadline").value("미션 기한은 필수입니다."));
    }

    @Test
    @DisplayName("가게 미션 생성 - point 음수 시 400 반환")
    void createMission_point_음수_검증실패() throws Exception {
        Map<String, Object> body = Map.of(
                "deadline", LocalDate.now().plusDays(7).toString(),
                "point", -1,
                "conditional", "12,000원 이상 식사"
        );

        mockMvc.perform(post("/api/v1/stores/1/missions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.result.point").value("포인트는 0 이상이어야 합니다."));
    }

    @Test
    @DisplayName("가게 미션 생성 - conditional 빈값 시 400 반환")
    void createMission_conditional_빈값_검증실패() throws Exception {
        Map<String, Object> body = Map.of(
                "deadline", LocalDate.now().plusDays(7).toString(),
                "point", 500,
                "conditional", ""
        );

        mockMvc.perform(post("/api/v1/stores/1/missions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.result.conditional").value("미션 조건은 필수입니다."));
    }
}
