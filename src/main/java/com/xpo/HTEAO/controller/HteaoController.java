package com.xpo.HTEAO.controller;

import com.xpo.HTEAO.order.Hteao;
import com.xpo.HTEAO.service.HteaoImpl;
import com.xpo.HTEAO.service.HteaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/tea")
public class HteaoController {

    //private HteaoImpl hteaoImpl;
    private HteaoService hteaoService;

    public HteaoController(HteaoService hteaoService) {
        this.hteaoService = hteaoService;
    }

    @PostMapping("/order")
    public ResponseEntity<?> orderTea(@RequestBody Hteao hteao) {
        // Implementation of orderTea method
        hteaoService.order(hteao);
        return ResponseEntity.ok("Order placed successfully");
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveTea(@RequestBody Hteao hteao) {
        for(int i = 0; i < 5; i++) {
            hteao.setHteaoId(hteao.getHteaoId() * 2L);
            hteao.setQuantity(hteao.getQuantity() + 1);
            hteao.setPrice(hteao.getPrice().multiply(BigDecimal.valueOf(hteao.getQuantity().doubleValue())));
            hteaoService.save(hteao);
        }
        hteaoService.save(hteao);
        return ResponseEntity.ok("Tea saved successfully");
    }

    @GetMapping("/quantity")
    public BigDecimal getTotalPrice(@RequestParam(defaultValue = "0") Integer quantity,
                                    @RequestParam(defaultValue = "0") BigDecimal price) {
        return hteaoService.getTotalPrice(quantity, price);
    }

    @GetMapping("/search")
    public Hteao findProduct(Long id) {

        return hteaoService.getHteaoById(id);
    }
}

