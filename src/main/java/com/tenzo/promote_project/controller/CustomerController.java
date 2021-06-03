package com.tenzo.promote_project.controller;

import com.tenzo.promote_project.domain.Customer;
import com.tenzo.promote_project.mapper.CustomerMapper;
import com.tenzo.promote_project.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private CustomerMapper customerMapper;

    @ResponseBody
    @RequestMapping("/addCustomer")
    public String addCustomer(String name) {
        Customer customer = new Customer();
        customer.setName(name);
        customerMapper.addCustomer(customer);
        return "success";
    }

    @ResponseBody
    @RequestMapping("/setPromoTime")
    public String setPromoTime(int time) {
        List<Customer> customerList = customerMapper.getAll();
        Customer customerObject = new Customer();
        customerObject.setPromoteAvalable(time);
        for (Customer customer : customerList) {
            String name = customer.getName();
            int id = customer.getId();
            /**
             * 如果没有使用过优惠
             */
            if (customer.getPromoteAvalable() == customer.getRemainingPromoteTime()) {
                customerObject.setRemainingPromoteTime(time);
            } else if (customer.getPromoteAvalable() != customer.getRemainingPromoteTime()) {
                /**
                 * 如果使用过优惠
                 * 且优惠次数已经超过新的最大值
                 */
                if (customer.getPromoteAvalable() - customer.getRemainingPromoteTime()>=time) {
                    customerObject.setRemainingPromoteTime(0);
                } else {
                    /**
                     * 如果使用过优惠
                     * 且优惠次数没有超过新的最大值
                     */
                    customerObject.setRemainingPromoteTime(time - (customer.getPromoteAvalable() -
                            customer.getRemainingPromoteTime()));
                }
            }
            customerObject.setId(id);
            customerObject.setName(name);
            customerMapper.deleteCustomer(customer.getId());
            customerMapper.addCustomer(customerObject);
        }
        return "";
    }
}
