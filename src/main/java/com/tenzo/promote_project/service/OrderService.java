package com.tenzo.promote_project.service;

import com.tenzo.promote_project.domain.Order;
import com.tenzo.promote_project.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedisService redisService;

    private Order order;
    /**
     * 创建订单信息
     */
    public void setOrder(int uid, int iid) {
        order.setUid(uid);
        order.setIid(iid);
        order.setCreateTime(new Date());
        orderMapper.createOrder(order);

        redisService.set(order.getId()+"", orderMapper.selectById(order.getId()));
    }
}
