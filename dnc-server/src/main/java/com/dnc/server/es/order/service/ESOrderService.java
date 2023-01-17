package com.dnc.server.es.order.service;

import com.dnc.server.es.order.ESOrderDto;
import com.dnc.server.es.order.ESOrderRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Service
public class ESOrderService {

    private Logger logger = Logger.getLogger(ESOrderService.class);

    @Resource
    ESOrderRepository repo;


    public void addOrder(ESOrderDto order){
        if(logger.isDebugEnabled()){
            logger.debug("save order function called : " + order);
        }
        order.setUuid(UUID.randomUUID().toString());
        order.setDate(new Date());
        repo.save(order);
    }

    public void deleteOrder(String uuid){
        if(logger.isDebugEnabled()){
            logger.debug("delete order function called : " + uuid);
        }

        repo.deleteByUuid(uuid);
    }

    public Iterable<ESOrderDto> getOrderList(){
        return repo.findAll();
    }

    public void run(ESOrderDto order){
        //TODO: 스비행님 소스

    }
}
