package lk.ijse.backenddemo.controller;

import lk.ijse.backenddemo.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.UUID;


public class PaymentController {
    @PostMapping("/payment/initiate")
    public ResponseEntity<?> initiatePayment(@RequestBody UserDTO userDTO) {
        String orderId = UUID.randomUUID().toString();


       // tempUserService.storeTempUser(orderId, userDTO); // You'll implement this

        // Build PayHere redirect URL
        String payHereUrl = "https://sandbox.payhere.lk/pay/checkout";
        String redirectUrl = payHereUrl + "?" +
                "merchant_id=YOUR_MERCHANT_ID" +
                "&return_url=http://localhost:8080/api/v1/payment/success?order_id=" + orderId +
                "&cancel_url=http://localhost:8080/payment-fail.html" +
                "&notify_url=http://localhost:8080/api/v1/payment/callback" +
                "&order_id=" + orderId +
                "&items=Vendor+Subscription" +
                "&amount=1000.00" +
                "&currency=LKR" +
                "&first_name=" + userDTO.getName() +
                "&email=" + userDTO.getEmail();

        return ResponseEntity.ok(Map.of("redirectUrl", redirectUrl));
    }

}
