package com.tenzo.promote_project.mapper;

import com.tenzo.promote_project.domain.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

@Mapper
public interface OrderMapper {
    /**
     * 插入订单信息
     * @param order
     */
    @Insert("insert into order(uid, iid, create_time) values (#{uid, jdbcType=INTEGER}, #{iid, jdbcType=INTEGER}," +
            "#{createTime, jdbcType=TIMESTAMP}" )
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    public void createOrder(Order order);

    /**
     * 查询订单信息
     * @param id
     * @return
     */
    @Select("select uid, iid, create_time from order where id=#{id}")
    public Order selectById(int id);

}
