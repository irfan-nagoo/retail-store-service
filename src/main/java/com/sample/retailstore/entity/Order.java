package com.sample.retailstore.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author irfan.nagoo
 */

@Entity
@Table(name = "rs_order")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;
    @OneToOne
    @JoinColumn(name = "status")
    private Status status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private Set<OrderDetail> orderDetails;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "order")
    private Billing billing;
    @Version
    private Long version;
    @Column(updatable = false)
    private LocalDateTime createDate;
    @Column(updatable = false)
    private String createdBy;
    private LocalDateTime updateDate;
    private String updatedBy;

}
