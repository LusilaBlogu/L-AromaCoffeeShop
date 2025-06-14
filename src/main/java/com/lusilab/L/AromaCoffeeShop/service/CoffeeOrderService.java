package com.lusilab.L.AromaCoffeeShop.service;

import com.lusilab.L.AromaCoffeeShop.entity.Coffee;
import com.lusilab.L.AromaCoffeeShop.entity.CoffeeOrder;
import com.lusilab.L.AromaCoffeeShop.entity.CoffeeSize;
import com.lusilab.L.AromaCoffeeShop.entity.PaymentMethod;
import com.lusilab.L.AromaCoffeeShop.repository.CoffeeOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoffeeOrderService {

    private CoffeeOrderRepository coffeeOrderRepository;
    private CoffeeService coffeeService;

    public CoffeeOrderService(CoffeeOrderRepository coffeeOrderRepository,
                              CoffeeService coffeeService) {
        this.coffeeOrderRepository = coffeeOrderRepository;
        this.coffeeService = coffeeService;
    }

    public List<CoffeeOrder> getAllOrder(){
        return coffeeOrderRepository.findAll();
    }

    public CoffeeOrder chooseCoffee(Long coffeeId) {
        Coffee coffee = coffeeService.getCoffeeById(coffeeId);
        CoffeeOrder order = new CoffeeOrder();
        order.setCoffee(coffee);
        return order;
    }
    public CoffeeOrder chooseSize(CoffeeOrder order, CoffeeSize size) {
        order.setCoffeeSize(size);
        return order;
    }
    public CoffeeOrder chooseQuantity(CoffeeOrder order, int quantity) {
        order.setQuantity(quantity);
        return order;
    }

    public CoffeeOrder addExtras(CoffeeOrder order, Boolean extraSugar,
                                 Boolean extraChocolate, Boolean extraCinnamon,
                                 Boolean extraMilk, Boolean extraCookies, Boolean extraCaramel,
                                 Boolean extraVanillie, Boolean extraCacao, Boolean extraWhippedCream){
        order.setExtraSugar(extraSugar);
        order.setExtraChocolate(extraChocolate);
        order.setExtraCinnamon(extraCinnamon);
        order.setExtraMilk(extraMilk);
        order.setExtraCookies(extraCookies);
        order.setExtraCaramel(extraCaramel);
        order.setExtraVanillie(extraVanillie);
        order.setExtraCacao(extraCacao);
        order.setExtraWhippedCream(extraWhippedCream);
        return order;
    }
    public CoffeeOrder choosePayment(CoffeeOrder order, PaymentMethod paymentMethod) {
        order.setPaymentMethod(paymentMethod);
        return coffeeOrderRepository.save(order);
    }
    public CoffeeOrder getOrderById(Long id) {
        Optional<CoffeeOrder> optional = coffeeOrderRepository.findById(id);
        return optional.orElse(null);
    }

    public CoffeeOrder saveOrder(CoffeeOrder order) {
        return coffeeOrderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        coffeeOrderRepository.deleteById(id);
    }
    public CoffeeOrder createNewOrder(Coffee coffee, CoffeeSize coffeeSize, int quantity,
                                      PaymentMethod paymentMethod,
                                      Boolean extraSugar, Boolean extraChocolate, Boolean extraCinnamon,
                                      Boolean extraMilk, Boolean extraVanillie, Boolean extraCookies,
                                      Boolean extraCaramel, Boolean extraCacao, Boolean extraWhippedCream) {

        CoffeeOrder order = new CoffeeOrder(
                coffee,
                coffeeSize,
                quantity,
                paymentMethod,
                extraSugar,
                extraChocolate,
                extraCinnamon,
                extraMilk,
                extraVanillie,
                extraCookies,
                extraCaramel,
                extraCacao,
                extraWhippedCream
        );

        return coffeeOrderRepository.save(order);
    }
}

