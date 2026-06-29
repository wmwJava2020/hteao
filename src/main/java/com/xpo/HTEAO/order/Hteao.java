package com.xpo.HTEAO.order;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "tbl_hteao")
@Data
public class Hteao {
    @Id
    private Long hteaoId;
    private String hteaoName;
    private BigDecimal price;
    private Integer quantity;
    private String hteaoDesc;

}
