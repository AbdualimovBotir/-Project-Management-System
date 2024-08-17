package com.pms.controller;

import com.pms.modal.PlanType;
import com.pms.modal.User;
import com.pms.response.PaymentLinkResponse;
import com.pms.service.UserService;
import com.razorpay.RazorpayClient;
import com.razorpay.PaymentLink;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Value("${razorpay.api.key}")
    private String apiKey;

    @Value("${razorpay.api.secret}")
    private String apiSecret;

    @Autowired
    private final UserService userService;

    public PaymentController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/{planType}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @PathVariable PlanType planType,
            @RequestHeader("Authorization")String jwt
            )throws Exception{
        User user=userService.findUserProfileByJwt(jwt);
        int amount=799*100;
        if(planType.equals(PlanType.ANNULLY)){
            amount=amount*12;
            amount=(int)(amount*0.7);
        }

        RazorpayClient razorpayClient=new RazorpayClient(apiKey,apiSecret);
        JSONObject paymentLinkRequest=new JSONObject();
        paymentLinkRequest.put("amount",amount);
        paymentLinkRequest.put("currency","INR");

        JSONObject customer=new JSONObject();
        customer.put("name",user.getFullName());
        customer.put("email",user.getEmail());
        paymentLinkRequest.put("customer",customer);

        JSONObject notiyf=new JSONObject();
        notiyf.put("email",true);
        paymentLinkRequest.put("notify",notiyf);

        paymentLinkRequest.put("callback_url","http://localhost:5173/upgrade_plan/success?planType"+planType);

        PaymentLink payment=razorpayClient.paymentLink.create(paymentLinkRequest);

        String paymentLinkId=payment.get("id");
        String paymentLinkUrl=payment.get("short_url");

        PaymentLinkResponse res=new PaymentLinkResponse();
        res.setPayment_link_url(paymentLinkUrl);
        res.setPayment_link_id(paymentLinkId);
        return new ResponseEntity<>(res, HttpStatus.CREATED);

    }

}
