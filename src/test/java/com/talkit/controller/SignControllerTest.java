package com.talkit.controller;

import com.talkit.configuration.jwt.JwtProvider;
import com.talkit.dto.SignDto;
import com.talkit.service.SignServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(SignController.class)
class SignControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SignServiceImpl signService;

    //@WebMvcTest는 컨트롤러 관련 빈만 주입해서... 따로 작성
    @MockitoBean
    private JwtProvider jwtProvider;

    @Test
    void register() throws Exception {
        //given
        given(signService.register(any()))
                .willReturn("test@test.com");

        //when, then
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "email" : "test@test.com",
                                "password" : "1234",
                                "confirm_password" : "1234",
                                "username" : "testUser",
                                "mbti_type" : "MBTI"
                                }
                                """))
                .andExpect(status().isOk());

    }
    @Test
    void loginTest() throws Exception {
        String token = "mocked-jwt-token";
        given(signService.login(any(SignDto.LoginRequest.class))).willReturn(token);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "email": "test@test.com",
                        "password": "1234"
                    }
                """))
                .andExpect(status().isOk());
    }
}
