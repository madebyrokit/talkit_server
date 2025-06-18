package com.talkit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talkit.dto.SignDto;
import com.talkit.service.MemberService;
import com.talkit.service.SignService;
import com.talkit.service.SignServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SignControllerTest.class)
class SignControllerTest {

}