package com.tenzo.promote_project.controller;

import com.tenzo.promote_project.domain.Customer;
import com.tenzo.promote_project.mapper.CustomerMapper;
import com.tenzo.promote_project.service.CustomerService;
import com.tenzo.promote_project.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired private CustomerService customerService;

    /**
     * 加入新客户信息
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping("/addCustomer")
    public String addCustomer(String name) {
        try {
            customerService.addCustomer(name);
            return "注册成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "注册时出现问题，请重试";
    }

    /**
     * 设置最大中奖次数
     * @param time
     * @return
     */
    @ResponseBody
    @RequestMapping("/setPromoTime")
    public String setPromoTime(int time) {
        try {
            customerService.setPromoteTime(time);
            return "修改成功！,新的中奖次数为: " + time + "%";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "修改失败";
    }
}
