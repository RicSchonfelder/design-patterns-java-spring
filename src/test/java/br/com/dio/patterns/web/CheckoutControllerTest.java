package br.com.dio.patterns.web;

import br.com.dio.patterns.DesignPatternsApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = DesignPatternsApplication.class)
@AutoConfigureMockMvc
class CheckoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void finalizaCheckoutVip() throws Exception {
        String body = """
                {
                  "tipoCliente": "VIP",
                  "itens": [
                    {"descricao": "Curso de Java", "precoUnitario": 250.00, "quantidade": 2}
                  ],
                  "promocaoForcada": false
                }
                """;

        mockMvc.perform(post("/api/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.totalBruto").value(500.00))
                .andExpect(jsonPath("$.totalLiquido").value(450.00))
                .andExpect(jsonPath("$.politicaAplicada").value("DESCONTO_VIP_10"));
    }

    @Test
    void rejeitaPedidoSemItens() throws Exception {
        String body = """
                {"tipoCliente": "COMUM", "itens": [], "promocaoForcada": false}
                """;

        mockMvc.perform(post("/api/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }
}
