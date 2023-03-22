package com.sample.retailstore.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author irfan.nagoo
 */

@Entity
@Table(name = "rs_item")
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne
    @JoinColumn(name = "category")
    private Category category;
    @OneToOne
    @JoinColumn(name = "type")
    private Type type;
    private String manufacturer;
    private String barcode;
    @OneToOne
    @JoinColumn(name = "status")
    private Status status;
    private BigDecimal price;
    private Long quantity;
    private String unit;
    @Version
    private Long version;
    @Column(updatable = false)
    private LocalDateTime createDate;
    @Column(updatable = false)
    private String createdBy;
    private LocalDateTime updateDate;
    private String updatedBy;
}
