package com.xpo.HTEAO.service;

import com.xpo.HTEAO.order.Hteao;
import com.xpo.HTEAO.repository.HteaoRespository;
import org.springframework.stereotype.Service;

@Service
public class HteaoImpl implements HteaoService {

    public HteaoImpl(HteaoRespository hteaoRespository) {
        this.hteaoRespository = hteaoRespository;
    }

    private final HteaoRespository hteaoRespository;


    @Override
    public void order(Hteao hteao) {
        // Implementation of order method
        hteaoRespository.save(hteao);
    }

    @Override
    public void save(Hteao hteao) {
        // Implementation of save method
        hteaoRespository.save(hteao);
    }
}