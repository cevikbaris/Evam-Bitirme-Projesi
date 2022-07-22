package com.evam.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "Bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int billId;

    @Column(nullable = false)
    private UUID subscriberNo;

    @Column(nullable = false)
    @Positive(message = "Bill price must be greater than 0.")
    private BigDecimal billPrice;

    @Column(nullable = false)
    private Date processDate;

    @Column(nullable = false)
    private boolean isPaid;

    @JsonIgnore //to hide in json. Otherwise, it causes infinity loop . Customer and bill try create each other.
    @ManyToOne
    private Customer customer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o != null) {
            Hibernate.getClass(this);
            Hibernate.getClass(o);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
