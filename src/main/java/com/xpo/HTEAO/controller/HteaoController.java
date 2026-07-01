package com.xpo.HTEAO.controller;

import com.xpo.HTEAO.order.Hteao;
import com.xpo.HTEAO.service.HteaoImpl;
import com.xpo.HTEAO.service.HteaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.xpo.HTEAO.service.HteaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/tea")
public class HteaoController {

    Logger log = LoggerFactory.getLogger(HteaoController.class);
    private HteaoImpl hteaoImpl;
    //Logger log = LoggerFactory.getLogger(HteaoController.class);
    //private HteaoImpl hteaoImpl;
    private HteaoService hteaoService;

    public HteaoController(HteaoImpl hteaoImpl) {
        this.hteaoImpl = hteaoImpl;
    }

    @PostMapping("/order")
    public ResponseEntity<?> orderTea(@RequestBody Hteao hteao) {
        // Implementation of orderTea method
        hteaoImpl.order(hteao);
        log.info("Order placed successfully for tea: {}", hteao);
        hteaoService.order(hteao);
        log.info("Order placed for Tea: " + hteao.getHteaoName() + ", Quantity: " + hteao.getQuantity() + ", Total Price: " + hteao.getPrice());
        return ResponseEntity.ok("Order placed successfully");
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveTea(@RequestBody Hteao hteao) {
        for(int i = 0; i < 5; i++) {
            hteao.setHteaoId(hteao.getHteaoId() + i);
            //hteao.setPrice(hteao.getPrice() + i * 2.99);
            hteaoImpl.save(hteao);
            log.info("Saved tea: {}", hteao);
            log.info("Saved tea quantity: {}", hteao.getQuantity());
            hteao.setHteaoId(hteao.getHteaoId() * 2L);
            hteao.setQuantity(hteao.getQuantity() + 1);
            hteao.setPrice(hteao.getPrice().multiply(BigDecimal.valueOf(hteao.getQuantity().doubleValue())));
            log.info("Saving Tea: " + hteao.getHteaoName() + ", Quantity: " + hteao.getQuantity() + ", Price: " + hteao.getPrice());
            hteaoService.save(hteao);
        }
        hteaoImpl.save(hteao);
        return ResponseEntity.ok("Tea saved successfully");
    }

    @GetMapping("/quantity")
    public BigDecimal getTotalPrice(@RequestParam(defaultValue = "0") Integer quantity,
                                    @RequestParam(defaultValue = "0") BigDecimal price) {
        return hteaoService.getTotalPrice(quantity, price);
    }

    @GetMapping("/search")
    public Hteao findProduct(Long id) {
        log.info("Searching for Tea with ID: " + id);
        return hteaoService.getHteaoById(id);

    }
}

