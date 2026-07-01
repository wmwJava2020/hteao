package com.xpo.HTEAO.service;

import com.xpo.HTEAO.order.Hteao;
import com.xpo.HTEAO.repository.HteaoRespository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HteaoImplTest {

    Logger log = Logger.getLogger(HteaoImplTest.class.getName());

    @Mock
    private HteaoRespository hteaoRespository;

    @InjectMocks
    private HteaoImpl hteaoImpl;

    private Hteao hteao;

    @BeforeEach
    void setUp() {
        hteao = new Hteao();
        hteao.setHteaoId(1L);
        hteao.setHteaoName("Classic Milk Tea");
        hteao.setPrice(new BigDecimal("3.50"));
        hteao.setQuantity(2);
        hteao.setHteaoDesc("A classic milk tea");
    }

    @Test
    void order_savesHteao() {
        hteaoImpl.order(hteao);

        ArgumentCaptor<Hteao> captor = ArgumentCaptor.forClass(Hteao.class);
        verify(hteaoRespository, times(1)).save(captor.capture());
        assertThat(captor.getValue()).isSameAs(hteao);
        log.info("Hteao Saved to the Database: " + captor.getValue().getHteaoName());
    }

    @Test
    void save_savesHteao() {
        hteaoImpl.save(hteao);
        verify(hteaoRespository, times(1)).save(hteao);
        log.info("Hteao Saved to the Database: " + hteao.getHteaoName());
    }

    @Test
    void getTotalPrice_returnsPriceTimesQuantity() {
        BigDecimal total = hteaoImpl.getTotalPrice(3, new BigDecimal("3.50"));
        log.info("Total Price: " + total);
        assertThat(total).isEqualByComparingTo("10.50");
    }

    @Test
    void getTotalPrice_returnsZero_whenPriceIsNull() {
        BigDecimal total = hteaoImpl.getTotalPrice(3, null);
        assertThat(total).isEqualTo(BigDecimal.ZERO);
        log.info("Total Price: " + total);
    }

    @Test
    void getTotalPrice_returnsZero_whenQuantityIsZero() {
        BigDecimal total = hteaoImpl.getTotalPrice(0, new BigDecimal("3.50"));
        log.info("Total Price: " + total);
        assertThat(total).isEqualByComparingTo("0");
    }

    @Test
    void getHteaoById_returnsHteao_whenFound() {
        when(hteaoRespository.findById("1")).thenReturn(Optional.of(hteao));

        Hteao result = hteaoImpl.getHteaoById(1L);

        assertThat(result).isSameAs(hteao);
        verify(hteaoRespository).findById("1");
    }

    @Test
    void getHteaoById_returnsNull_whenNotFound() {
        when(hteaoRespository.findById("99")).thenReturn(Optional.empty());

        Hteao result = hteaoImpl.getHteaoById(99L);

        assertThat(result).isNull();
        verify(hteaoRespository).findById("99");
    }
}
