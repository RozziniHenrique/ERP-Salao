package erp.Salao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import erp.Salao.domain.agendamento.dto.dtoAgendamentoCadastrar;
import erp.Salao.domain.agendamento.dto.dtoAgendamentoCancelar;
import erp.Salao.service.AgendamentoService;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@Sql(scripts = "/data.sql")
@ActiveProfiles("test")
class AgendamentoControllerTest {

    @Autowired
    AgendamentoService agendamentoService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;
    @MockitoBean
    private JacksonTester<dtoAgendamentoCadastrar> dadosAgendamentoJson;

    @Test
    @DisplayName("Cenário 1: Informações inválidas -> Deve retornar http 400")
    @WithMockUser("CLIENTE")
    void agendar_cenario01() throws Exception {
        var response = mvc.perform(
                        post("/agendamentos")
                                .with(user("usuario-teste").authorities(new SimpleGrantedAuthority("CLIENTE")))
                                .with(csrf())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Cenário 2: Informações válidas -> Deve retornar http 200")
    @WithMockUser(authorities = "CLIENTE")
    void agendar_cenario02() throws Exception {
        var proximaSegunda10am = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .withHour(10).withMinute(0).withSecond(0).withNano(0);
        var dadosAgendamento = new dtoAgendamentoCadastrar(3L, 3L, 3L, proximaSegunda10am);
        String json = objectMapper.writeValueAsString(dadosAgendamento);
        var response = mvc.perform(
                        post("/agendamentos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("Cenário 3: Cancelar agendamento com informações válidas -> Deve retornar http 204")
    @WithMockUser(authorities = "ADMIN")
    void cancelar_cenario01() throws Exception {
        var dadosCancelamento = new dtoAgendamentoCancelar(1L, "O cliente desistiu");
        String json = objectMapper.writeValueAsString(dadosCancelamento);
        var response = mvc.perform(
                        delete("/agendamentos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

}