package com.tenzo.promote_project.mapper;

import com.tenzo.promote_project.domain.Customer;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CustomerMapper {

    @Insert({
            "insert into customer (id, name, maxPromoteTime) values " +
                    "(#{id, jdbcType=INTEGER}, #{name, jdbcType=VARCHAR}," +
                    "#{maxPromoteTime, jdbcType=INTEGER})"
    })
    void addCustomer(Customer customer);

    @Delete({
            "delete from customer where id=#{id, jdbcType=INTEGER}"
    })
    void deleteCustomer(int id);

    @Select({
            "select id, name, maxPromoteTime from customer"
    })
    List<Customer> getAll();
}
