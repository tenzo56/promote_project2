package com.tenzo.promote_project.mapper;

import com.tenzo.promote_project.domain.Customer;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CustomerMapper {

    /**
     * 添加顾客
     * @param customer
     */
    @Insert({
            "insert into customer (id, name, maxPromoteTime) values " +
                    "(#{id, jdbcType=INTEGER}, #{name, jdbcType=VARCHAR}," +
                    "#{maxPromoteTime, jdbcType=INTEGER})"
    })
    void addCustomer(Customer customer);

    /**
     * 删除
     * @param id
     */
    @Delete({
            "delete from customer where id=#{id, jdbcType=INTEGER}"
    })
    void deleteCustomer(int id);

    /**
     * 选择所有顾客
     * @return
     */
    @Select({
            "select id, name, maxPromoteTime from customer"
    })
    List<Customer> getAll();
}
