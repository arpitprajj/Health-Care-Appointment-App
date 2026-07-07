package com.hca.payment_service.util;

import org.springframework.stereotype.Component;

@Component
public class FakePaymentGateway {

    public boolean processPayment()  {
       // Thread.sleep(2000);
        return true;

    }

}