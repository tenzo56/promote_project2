package com.tenzo.promote_project.service;

import com.tenzo.promote_project.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {

    private OrderMapper orderMapper;

    /**
     * 创建订单信息
     * @param uid 买家id
     * @param iid 商品id
     */
    public void setOrder(int uid, int iid) {
        orderMapper.createOrder(uid, iid, new Date());
    }
}
