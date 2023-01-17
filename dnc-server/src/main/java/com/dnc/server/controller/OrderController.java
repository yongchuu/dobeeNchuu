package com.dnc.server.controller;

import com.dnc.server.es.order.ESOrderDto;
import com.dnc.server.es.order.service.ESOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/order") // http://20.196.196.177:8081/dnc/order/getOrders.do
@CrossOrigin("*")
public class OrderController {

    @Autowired
    ESOrderService service;

    // http://20.196.196.177:8081/dnc/order/addOrder.do
    @RequestMapping(value="/addOrder.do", method = RequestMethod.POST)
    public void addOrder(ESOrderDto order){

        service.addOrder(order);
    }

    // http://20.196.196.177:8081/dnc/order/deleteOrder.do
    @RequestMapping(value="/deleteOrder.do", method = RequestMethod.POST)
    public void deleteOrder(String uuid){

        service.deleteOrder(uuid);
    }

    // http://20.196.196.177:8081/dnc/order/getOrders.do
    @RequestMapping(value="/getOrders.do", method = RequestMethod.POST)
    public Iterable<ESOrderDto> getOrderList(){

        return service.getOrderList();
    }

    // http://20.196.196.177:8081/dnc/order/run.do
    @RequestMapping(value="/run.do", method = RequestMethod.POST)
    public void run(ESOrderDto order){

        service.run(order);
    }

}
