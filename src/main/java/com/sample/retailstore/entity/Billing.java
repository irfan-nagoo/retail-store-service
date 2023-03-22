package com.sample.retailstore.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author irfan.nagoo
 */

@Entity
@Table(name = "rs_billing")
@Getter
@Setter
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private String transId;
    @OneToOne
    @JoinColumn(name = "status")
    private Status status;
    private BigDecimal shippingCharges;
    private BigDecimal discount;
    private BigDecimal tax;
    private BigDecimal totalCharged;
}
