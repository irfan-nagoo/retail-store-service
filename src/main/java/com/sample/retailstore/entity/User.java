package com.sample.retailstore.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author irfan.nagoo
 */

@Entity
@Table(name = "rs_user")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String email;
    private String mobile;
    @OneToOne
    @JoinColumn(name = "status")
    private Status status;
    @Version
    private Long version;
    @Column(updatable = false)
    private LocalDateTime createDate;
    @Column(updatable = false)
    private String createdBy;
    private LocalDateTime updateDate;
    private String updatedBy;
}
