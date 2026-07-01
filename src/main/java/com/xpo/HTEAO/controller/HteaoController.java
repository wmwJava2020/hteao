package com.xpo.HTEAO.controller;

import com.xpo.HTEAO.order.Hteao;
import com.xpo.HTEAO.service.HteaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tea")
public class HteaoController {

    Logger log  = LoggerFactory.getLogger(HteaoController.class);
    Hteao hteao;
    private HteaoImpl hteaoImpl;

    public HteaoController(HteaoImpl hteaoImpl) {
        log.info("HteaoController initialized with HteaoImpl");
        this.hteaoImpl = hteaoImpl;
    }

    @PostMapping("/order")
    public ResponseEntity<?> orderTea(@RequestBody Hteao hteao) {
        // Implementation of orderTea method
        log.info("Received order request for tea: {}", hteao);
        hteaoImpl.order(hteao);
        log.info("Order placed successfully for tea: {}", hteao);
        return ResponseEntity.ok("Order placed successfully");
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveTea(@RequestBody Hteao hteao) {
        log.info("Received save request for tea: {}", hteao);
        for(int i = 0; i < 5; i++) {
            hteao.setHteaoId(hteao.getHteaoId() + i);
            //hteao.setPrice(hteao.getPrice() + i * 2.99);
            hteaoImpl.save(hteao);
            log.info("Saved tea with ID: {} and Price: {}", hteao.getHteaoId(), hteao.getPrice());
        }
        hteaoImpl.save(hteao);
        log.info("Saved tea successfully for tea: {}", hteao);
        return ResponseEntity.ok("Tea saved successfully");
    }
}
