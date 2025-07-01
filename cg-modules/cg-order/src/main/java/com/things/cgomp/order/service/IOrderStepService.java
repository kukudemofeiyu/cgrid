package com.things.cgomp.order.service;

/**
 * @author janson
 */
public interface IOrderStepService {

    boolean checkAndProcessOrderStep();

    boolean checkAndProcessOrderStep(Long orderId);
}
