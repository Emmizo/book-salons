package com.emmizo.controller;

import com.emmizo.domain.PaymentMethod;
import com.emmizo.modal.PaymentOrder;
import com.emmizo.payload.dto.BookingDTO;
import com.emmizo.payload.dto.UserDTO;
import com.emmizo.payload.response.PaymentLinkResponse;
import com.emmizo.service.PaymentService;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @RequestBody BookingDTO booking,
            @RequestParam PaymentMethod paymentMethod
            ) throws StripeException, RazorpayException {
        UserDTO user = new UserDTO();
        user.setFullName("Emmizo");
        user.setEmail("emmizo@emmizo.com");
        PaymentLinkResponse res = paymentService.createOrder(user,booking,paymentMethod);
        return ResponseEntity.ok(res);
    }
    @GetMapping("/{paymentOrderId}")
    public ResponseEntity<PaymentOrder> getPaymentOrderById(
           @PathVariable Long paymentOrderId
    ) throws Exception {
        PaymentOrder res = paymentService.getPaymentOrderById(paymentOrderId);
        return ResponseEntity.ok(res);
    }
    @PatchMapping("/proceed")
    public ResponseEntity<Boolean> proceedPayment(
            @RequestParam String paymentId,
            @RequestParam String paymentLinkId
           ) throws Exception {

        PaymentOrder paymentOrder= paymentService.getPaymentOrderByPaymentId(paymentLinkId);
        Boolean res = paymentService.proceedPayment(paymentOrder,paymentId,paymentLinkId);
        return ResponseEntity.ok(res);
    }


}
