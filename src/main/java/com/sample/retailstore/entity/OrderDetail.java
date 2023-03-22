package com.sample.retailstore.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author irfan.nagoo
 */

@Entity
@Table(name = "rs_order_detail")
@Getter
@Setter
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private Item item;
    private Long quantity;
}
