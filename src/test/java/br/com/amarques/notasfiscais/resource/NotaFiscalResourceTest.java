package br.com.amarques.notasfiscais.resource;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import br.com.amarques.notasfiscais.dto.NotaFiscalDTO;
import br.com.amarques.notasfiscais.dto.SimpleEntityDTO;
import br.com.amarques.notasfiscais.dto.createupdare.CreateUpdateNotaFiscalDTO;
import br.com.amarques.notasfiscais.service.NotaFiscalService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = NotaFiscalResource.class)
class NotaFiscalResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NotaFiscalService notaFiscalService;

    @Test
    void shouldReturnAllNotasFiscais() throws Exception {
        NotaFiscalDTO nota1 = new NotaFiscalDTO(100L, 1, DATA_NF_TEXT, VALOR_NF, TOMADOR_ID, PRESTADOR_ID);
        NotaFiscalDTO nota2 = new NotaFiscalDTO(200L, 2, DATA_NF_TEXT, VALOR_NF, TOMADOR_ID, PRESTADOR_ID);
        NotaFiscalDTO nota3 = new NotaFiscalDTO(300L, 3, DATA_NF_TEXT, VALOR_NF, TOMADOR_ID, PRESTADOR_ID);

        when(notaFiscalService.getAll()).thenReturn(List.of(nota1, nota2, nota3));

        MvcResult mvcResult = mockMvc.perform(get("/notas-fiscais"))
                .andExpect(status().isOk())
                .andReturn();

        List<NotaFiscalDTO> notasFiscais = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, NotaFiscalDTO.class));

        assertNotNull(notasFiscais);
        assertEquals(3, notasFiscais.size());
    }

    @Test
    void shouldReturnNotaFiscalWhenFindByID() throws Exception {
        NotaFiscalDTO notaFiscal = new NotaFiscalDTO(ID, NUMERO_NF, DATA_NF_TEXT, VALOR_NF, TOMADOR_ID, PRESTADOR_ID);

        when(notaFiscalService.getById(ID)).thenReturn(notaFiscal);

        MvcResult mvcResult = mockMvc.perform(get("/notas-fiscais/{id}", ID))
                .andExpect(status().isOk())
                .andReturn();

        NotaFiscalDTO notaFiscalDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                NotaFiscalDTO.class);

        assertNotNull(notaFiscalDTO);
        assertThat(notaFiscalDTO.id, is(equalTo(ID)));
        assertThat(notaFiscalDTO.numero, is(equalTo(NUMERO_NF)));
        assertThat(notaFiscalDTO.data, is(equalTo(DATA_NF_TEXT)));
        assertThat(notaFiscalDTO.valor, is(equalTo(VALOR_NF)));
        assertThat(notaFiscalDTO.tomadorId, is(equalTo(TOMADOR_ID)));
        assertThat(notaFiscalDTO.prestadorId, is(equalTo(PRESTADOR_ID)));
    }

    @Test
    void shouldCreate() throws Exception {
        CreateUpdateNotaFiscalDTO dto = new CreateUpdateNotaFiscalDTO(NUMERO_NF, DATA_NF_TEXT, VALOR_NF, TOMADOR_ID,
                PRESTADOR_ID);

        when(notaFiscalService.create(dto)).thenReturn(new SimpleEntityDTO(ID));

        MvcResult mvcResult = mockMvc.perform(post("/notas-fiscais")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn();

        SimpleEntityDTO simpleEntityDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                SimpleEntityDTO.class);

        assertNotNull(simpleEntityDTO);
        assertThat(simpleEntityDTO.id, is(equalTo(ID)));
    }

    @Test
    void shouldReturnAllNotasFiscaisRelatedWithEmpresa() throws Exception {
        NotaFiscalDTO nota1 = new NotaFiscalDTO(100L, 1, DATA_NF_TEXT, VALOR_NF, TOMADOR_ID, PRESTADOR_ID);
        NotaFiscalDTO nota2 = new NotaFiscalDTO(200L, 2, DATA_NF_TEXT, VALOR_NF, TOMADOR_ID, PRESTADOR_ID);
        NotaFiscalDTO nota3 = new NotaFiscalDTO(300L, 3, DATA_NF_TEXT, VALOR_NF, TOMADOR_ID, PRESTADOR_ID);

        when(notaFiscalService.getAllByEmpresa(TOMADOR_ID)).thenReturn(List.of(nota1, nota2, nota3));

        MvcResult mvcResult = mockMvc.perform(get("/notas-fiscais/empresas/{id}", TOMADOR_ID))
                .andExpect(status().isOk())
                .andReturn();

        List<NotaFiscalDTO> notasFiscais = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, NotaFiscalDTO.class));

        assertNotNull(notasFiscais);
        assertEquals(3, notasFiscais.size());
    }

    private static final Long ID = 1000L;
    private static final Integer NUMERO_NF = 12345;
    private static final String DATA_NF_TEXT = "18/08/2020";
    private static final BigDecimal VALOR_NF = BigDecimal.TEN;
    private static final Long TOMADOR_ID = 444L;
    private static final Long PRESTADOR_ID = 555L;
}
