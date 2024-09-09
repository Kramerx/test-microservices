package com.project.transactional.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.project.transactional.dto.AccountStatusReportDTO;
import com.project.transactional.service.ReportService;

public class ReportControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReportService reportService;

    @InjectMocks
    private ReportController reportController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();
    }

    @Test
    public void getAccountStatusReportTest() throws Exception {
        Long idClient = 1L;
        LocalDate initDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();

        when(reportService.generateAccountStatusReport(anyLong(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(new AccountStatusReportDTO(idClient, "New Client", Collections.emptyList()));

        mockMvc.perform(get("/report")
                .param("idClient", idClient.toString())
                .param("initDate", initDate.toString())
                .param("endDate", endDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idClient").value(idClient));
    }
}