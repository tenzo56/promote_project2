package com.tenzo.promote_project.service;

import com.tenzo.promote_project.domain.Customer;
import com.tenzo.promote_project.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 增加顾客
     * @param name
     */
    public void addCustomer(String name) {
        Customer customer = new Customer();
        customer.setName(name);
        customerMapper.addCustomer(customer);
    }

    /**
     * 修改中奖率
     * @param time
     */
    public void setPromoteTime(int time) {
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
    }
}
