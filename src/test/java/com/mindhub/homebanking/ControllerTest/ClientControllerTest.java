package com.mindhub.homebanking.ControllerTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindhub.homebanking.DTO.RegisterDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void canRegisterOk() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("pepe", "pepe", "pepe@mindhub.com", "pepe");
        mockMvc.perform((RequestBuilder) post("/api/auth/register")
                .contentType(MediaType.valueOf("application/json"))
                .contentType(
                        MediaType.valueOf(objectMapper.writeValueAsString(registerDTO))
                )
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
