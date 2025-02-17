package com.emmizo.repository;

import com.emmizo.modal.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentOrder,Long> {

    PaymentOrder  findByPaymentLinkId(String paymentLinkId);


}
