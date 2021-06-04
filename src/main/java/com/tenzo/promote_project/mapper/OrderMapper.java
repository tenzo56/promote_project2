package com.tenzo.promote_project.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface OrderMapper {
    /**
     * 插入订单信息
     * @param uid
     * @param iid
     * @param createTime
     */
    @Insert("insert into order(uid, iid, create_time) values (#{uid, jdbcType=INTEGER}, #{iid, jdbcType=INTEGER}," +
            "#{createTime, jdbcType=TIMESTAMP}" )
    public void createOrder(int uid, int iid, Date createTime);
}
