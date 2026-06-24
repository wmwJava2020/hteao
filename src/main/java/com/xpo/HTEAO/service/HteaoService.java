package com.xpo.HTEAO.service;

import com.xpo.HTEAO.order.Hteao;
import com.xpo.HTEAO.payment.HteaoPayment;

public interface HteaoService {
    void order(Hteao hteao);
    void save(Hteao hteao);
    //Double orderQuantity(Integer quantity, Double price);


}
