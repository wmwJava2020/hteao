package com.xpo.HTEAO.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xpo.HTEAO.order.Hteao;
import com.xpo.HTEAO.service.HteaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HteaoControllerTest {
    static Logger log = LoggerFactory.getLogger(HteaoControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HteaoService hteaoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Hteao hteao;

    @BeforeEach
    void setUp() {
        hteao = new Hteao();
        hteao.setHteaoId(1L);
        hteao.setHteaoName("Premium Oolong Tea");
        hteao.setPrice(new BigDecimal("5.99"));
        hteao.setQuantity(1);
        hteao.setHteaoDesc("Premium quality oolong tea");
    }

    @Test
    void orderTea_successfully_placesOrder() throws Exception {
        mockMvc.perform(post("/api/v1/tea/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hteao)))
                .andExpect(status().isOk())
                .andExpect(content().string("Order placed successfully"));

        verify(hteaoService, times(1)).order(any(Hteao.class));
       log.info("orderTea_successfully_placesOrder test passed...........");
    }

    @Test
    void orderTea_callsServiceOnce() throws Exception {
        mockMvc.perform(post("/api/v1/tea/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hteao)))
                .andExpect(status().isOk());

        verify(hteaoService).order(any(Hteao.class));
        log.info("orderTea_callsServiceOnce test passed...........");
    }

    @Test
    void saveTea_successfully_savesTea() throws Exception {
        mockMvc.perform(post("/api/v1/tea/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hteao)))
                .andExpect(status().isOk())
                .andExpect(content().string("Tea saved successfully"));

        verify(hteaoService, times(6)).save(any(Hteao.class));
        log.info("saveTea_successfully_savesTea test passed...........");
    }

    @Test
    void saveTea_callsServiceMultipleTimes() throws Exception {
        mockMvc.perform(post("/api/v1/tea/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hteao)))
                .andExpect(status().isOk());

        verify(hteaoService, times(6)).save(any(Hteao.class));
        log.info("saveTea_callsServiceMultipleTimes test passed...........");
    }

    @Test
    void getTotalPrice_withValidQuantityAndPrice() throws Exception {
        when(hteaoService.getTotalPrice(2, new BigDecimal("10.00")))
                .thenReturn(new BigDecimal("20.00"));

        mockMvc.perform(get("/api/v1/tea/quantity")
                .param("quantity", "2")
                .param("price", "10.00"))
                .andExpect(status().isOk())
                .andExpect(content().string("20.00"));
        log.info("getTotalPrice_withValidQuantityAndPrice test passed...........");
    }

    @Test
    void getTotalPrice_withZeroQuantity() throws Exception {
        when(hteaoService.getTotalPrice(0, new BigDecimal("10.00")))
                .thenReturn(new BigDecimal("0"));

        mockMvc.perform(get("/api/v1/tea/quantity")
                .param("quantity", "0")
                .param("price", "10.00"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
        log.info("getTotalPrice_withZeroQuantity test passed...........");
    }

    @Test
    void getTotalPrice_withDefaultValues() throws Exception {
        when(hteaoService.getTotalPrice(0, new BigDecimal("0")))
                .thenReturn(new BigDecimal("0"));

        mockMvc.perform(get("/api/v1/tea/quantity"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
        log.info("getTotalPrice_withDefaultValues test passed...........");
    }

    @Test
    void getTotalPrice_withLargeQuantity() throws Exception {
        when(hteaoService.getTotalPrice(100, new BigDecimal("5.50")))
                .thenReturn(new BigDecimal("550.00"));

        mockMvc.perform(get("/api/v1/tea/quantity")
                .param("quantity", "100")
                .param("price", "5.50"))
                .andExpect(status().isOk())
                .andExpect(content().string("550.00"));
        log.info("getTotalPrice_withLargeQuantity test passed...........");
    }

    @Test
    void findProduct_returnsTeaWhenFound() throws Exception {
        when(hteaoService.getHteaoById(1L)).thenReturn(hteao);

        mockMvc.perform(get("/api/v1/tea/search")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hteaoId").value(1))
                .andExpect(jsonPath("$.hteaoName").value("Premium Oolong Tea"))
                .andExpect(jsonPath("$.price").value(5.99));
        log.info("findProduct_returnsTeaWhenFound test passed...........");
    }

    @Test
    void findProduct_returnsNullWhenNotFound() throws Exception {
        when(hteaoService.getHteaoById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/tea/search")
                .param("id", "99"))
                .andExpect(status().isOk());
        log.info("findProduct_returnsNullWhenNotFound test passed...........");
    }

    @Test
    void findProduct_verifyRepositoryCall() throws Exception {
        when(hteaoService.getHteaoById(1L)).thenReturn(hteao);

        mockMvc.perform(get("/api/v1/tea/search")
                .param("id", "1"))
                .andExpect(status().isOk());

        verify(hteaoService).getHteaoById(1L);
        log.info("findProduct_verifyRepositoryCall test passed...........");
    }
}