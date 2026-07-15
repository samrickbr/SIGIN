package br.com.inova.sigin.fluxo;

import br.com.inova.sigin.dev.service.DevService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PedidoProducaoFluxoTest {

    @Autowired
    private DevService devService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void devePopularAmbienteDev() {

        List<String> resultado = devService.popular();

        assertTrue(resultado.contains("Produto"));
        assertTrue(resultado.contains("Material"));
    }

    @Test
    void deveResponderEndpointPedidos() throws Exception {

        mockMvc.perform(get("/pedidos"))
                .andExpect(status().isOk());

    }

}