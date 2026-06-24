package com.xpo.HTEAO.order;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tbl_hteao")
@Data
public class Hteao {
    @Id
    private String hteaoId;
    private String hteaoName;
    private Double price;
    private Integer orderQunatity;
    private String hteaoDesc;

}
