package com.xpo.HTEAO.service;

import com.xpo.HTEAO.order.Hteao;
import com.xpo.HTEAO.repository.HteaoRespository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.logging.Logger;

@Service
public class HteaoImpl implements HteaoService {

    Logger log = Logger.getLogger(HteaoImpl.class.getName());

    private final HteaoRespository hteaoRespository;

    public HteaoImpl(HteaoRespository hteaoRespository) {
        this.hteaoRespository = hteaoRespository;
    }

    @Override
    public void order(Hteao hteao) {
        // Implementation of order method
        hteaoRespository.save(hteao);
    }

    @Override
    public void save(Hteao hteao) {
        // Implementation of save method
        log.info("Saving Hteao: " + hteao);
        hteaoRespository.save(hteao);
    }

    @Override
    public BigDecimal getTotalPrice(Integer quantity, BigDecimal price) {
        if (quantity == 0 || price == null) {
            return BigDecimal.ZERO;
        }
        log.info("Getting Total Price: " + price);
        return price.multiply(BigDecimal.valueOf(quantity));

    }

    @Override
    public Hteao getHteaoById(Long id) {
        log.info("Fetching Hteao by ID: {}"+ id);
        return hteaoRespository.findById(String.valueOf(id)).orElse(null);
    }
}