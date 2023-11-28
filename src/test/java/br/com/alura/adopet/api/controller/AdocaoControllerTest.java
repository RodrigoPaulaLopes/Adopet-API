package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.service.AdocaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class AdocaoControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    private AdocaoService adocaoService;
    @Test
    void deveDevolverError400() throws Exception {
        //arrange
        String json = "{}";
        //act
        var response = mvc.perform(
                post("/adocoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andReturn().getResponse();
        //assert
        assertEquals(400, response.getStatus());
    }

    @Test
    void deveDevolver200() throws Exception {
        //arrange
        String json = """
                {
                    "idPet": 1,
                    "idTutor": 1,
                    "motivo": "motivo qualquer"
                }
                """;
        //act
        var response = mvc.perform(
                post("/adocoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andReturn().getResponse();
        //assert
        assertEquals(200, response.getStatus());
    }
}