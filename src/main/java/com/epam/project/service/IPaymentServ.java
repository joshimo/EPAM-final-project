package com.epam.project.service;

import com.epam.project.domain.Payment;

public interface IPaymentServ {

    boolean addPayment(Payment payment);

    boolean updatePayment(Payment payment);

}
