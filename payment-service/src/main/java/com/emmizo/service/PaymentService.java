package com.emmizo.service;

import com.emmizo.domain.PaymentMethod;
import com.emmizo.modal.PaymentOrder;
import com.emmizo.payload.dto.BookingDTO;
import com.emmizo.payload.dto.UserDTO;
import com.emmizo.payload.response.PaymentLinkResponse;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

public interface PaymentService {
    PaymentLinkResponse createOrder(UserDTO user,
                                   BookingDTO booking,
                                   PaymentMethod paymentMethod) throws RazorpayException, StripeException;
    PaymentOrder getPaymentOrderById(Long id) throws Exception;
    PaymentOrder getPaymentOrderByPaymentId(String paymentId);
    PaymentLink createRozarpayPaymentLink(UserDTO user,
                                          Long amount,
                                          Long orderId) throws RazorpayException;
    String createStripePaymentLink(UserDTO user,
                                  Long amount,
                                  Long orderId) throws StripeException;

    String createPayPalLink(UserDTO user,
                            Long amount,
                            Long orderId);

    Boolean proceedPayment(PaymentOrder paymentOrder,String paymentId, String paymentLinkId) throws RazorpayException;

}
