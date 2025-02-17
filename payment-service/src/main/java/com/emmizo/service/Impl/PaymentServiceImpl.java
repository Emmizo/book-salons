package com.emmizo.service.Impl;

import com.emmizo.domain.PaymentMethod;
import com.emmizo.domain.PaymentOrderStatus;
import com.emmizo.modal.PaymentOrder;
import com.emmizo.payload.dto.BookingDTO;
import com.emmizo.payload.dto.UserDTO;
import com.emmizo.payload.response.PaymentLinkResponse;
import com.emmizo.repository.PaymentRepository;
import com.emmizo.service.PaymentService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Value("${razorpay.api.key}")
    private String  razorpayApiKey;

    @Value("${razorpay.api.secret}")
    private String  razorpayApiSecretKey;

    @Value("${stripe.api.key}")
    private String stripeSecretKey;


    @Override
    public PaymentLinkResponse createOrder(UserDTO user, BookingDTO booking, PaymentMethod paymentMethod) throws RazorpayException, StripeException {
        Long amount =(long) booking.getTotalPrice();
        PaymentOrder order = new PaymentOrder();
        order.setAmount(amount);
        order.setPaymentMethod(paymentMethod);
        order.setBookingId(booking.getId());
        order.setSalonId(booking.getSalonId());
        PaymentOrder savedOrder = paymentRepository.save(order);

        PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();

        if(paymentMethod.equals(PaymentMethod.RAZORPAY)) {
            PaymentLink payment = createRozarpayPaymentLink(user,
                    savedOrder.getAmount(),
                    savedOrder.getId());
            String paymentUrl = payment.get("short_url");
            String paymentId = payment.get("id");
            paymentLinkResponse.setPayment_link_url(paymentUrl);
            paymentLinkResponse.setPayment_link_id(paymentId);

            paymentLinkResponse.setPayment_link_url(paymentUrl);
            paymentRepository.save(savedOrder);

        }else if(paymentMethod.equals(PaymentMethod.STRIPE)) {
           String paymentUrl = createStripePaymentLink(user,
                   savedOrder.getAmount(),
                   savedOrder.getId());
           paymentLinkResponse.setPayment_link_url(paymentUrl);
        }else{
            String paymentUrl = createPayPalLink(user,
                    savedOrder.getAmount(),
                    savedOrder.getId());
            paymentLinkResponse.setPayment_link_url(paymentUrl);
        }
        return paymentLinkResponse;
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {
        PaymentOrder paymentOrder = paymentRepository.findById(id).orElse(null);
        if(paymentOrder == null) {
            throw  new Exception("payment order not found");
        }
        return paymentOrder;
    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String paymentId) {

        return paymentRepository.findByPaymentLinkId(paymentId);
    }

    @Override
    public PaymentLink createRozarpayPaymentLink(UserDTO user,
                                                 Long Amount,
                                                 Long orderId) throws RazorpayException {
        Long amount  =  Amount*100;

            RazorpayClient razorpay = new RazorpayClient(razorpayApiKey, razorpayApiSecretKey);
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount",amount);
            paymentLinkRequest.put("currency","RWF");

            JSONObject customers = new JSONObject();
            customers.put("name",user.getFullName());
            customers.put("email",user.getEmail());

            paymentLinkRequest.put("customer",customers);

            JSONObject notify = new JSONObject();
            notify.put("email",true);
            paymentLinkRequest.put("notify",notify);

            paymentLinkRequest.put("reminder_enable",true);
            paymentLinkRequest.put("callback_url","http://localhost:3000/payment-success"+orderId);
            paymentLinkRequest.put("callback_method","get");

        return razorpay.paymentLink.create(paymentLinkRequest);
    }

    @Override
    public String createStripePaymentLink(UserDTO user, Long amount, Long orderId) throws StripeException {

        Stripe.apiKey = stripeSecretKey;
        SessionCreateParams params = SessionCreateParams.builder().addPaymentMethodType(SessionCreateParams
                        .PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/pay,emt-success"+orderId)
                .setCancelUrl("http://localhost:3000/payment-cancel"+orderId).addLineItem(SessionCreateParams
                        .LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(amount*100)
                                .setProductData(SessionCreateParams
                                        .LineItem
                                        .PriceData
                                        .ProductData
                                        .builder().setName("Salon appointment booking").build()
                                ).build()
                        ).build()
                ).build();
        Session session = Session.create(params);
        return session.getUrl();
    }

    @Override
    public String createPayPalLink(UserDTO user, Long amount, Long orderId) {
        return "";
    }

    @Override
    public Boolean proceedPayment(PaymentOrder paymentOrder,
                                  String paymentId,
                                  String paymentLinkId) throws RazorpayException {
        if(paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)){
           if(paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY)){
               RazorpayClient razorpay = new RazorpayClient(razorpayApiKey,razorpayApiSecretKey);
               Payment payment = razorpay.payments.fetch(paymentId);
               Integer amount = payment.get("amount");
               String status =  payment.get("status");
               if(status.equals("captured")){
                   //produce kafka event
                   paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                   paymentRepository.save(paymentOrder);
                   return true;
               }
               return false;
           }else{
               paymentOrder.setStatus(PaymentOrderStatus.FAILED);
               paymentRepository.save(paymentOrder);
               return true;
           }
        }
        return false;
    }
}
