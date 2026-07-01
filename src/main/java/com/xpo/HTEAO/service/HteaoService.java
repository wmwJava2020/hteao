package com.xpo.HTEAO.service;

import com.xpo.HTEAO.order.Hteao;
import com.xpo.HTEAO.payment.HteaoPayment;

import java.math.BigDecimal;

public interface HteaoService {
    void order(Hteao hteao);
    void save(Hteao hteao);
    BigDecimal getTotalPrice(Integer quantity, BigDecimal price);
    Hteao getHteaoById(Long id);

}
