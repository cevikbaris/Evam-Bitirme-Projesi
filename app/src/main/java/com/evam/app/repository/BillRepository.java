package com.evam.app.repository;

import com.evam.app.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BillRepository extends JpaRepository<Bill,Integer> {

    Bill findBySubscriberNo(UUID subscriberNo);
}
