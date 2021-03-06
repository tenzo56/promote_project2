package com.tenzo.promote_project.mapper;

import com.tenzo.promote_project.domain.Item;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ItemMapper {

    /**
     * 创建商品
     * @param item
     * @return
     */
    @Insert({
            "insert into item (id, name, original_price," +
                    " promote_price, stock, rate) values" +
                    " (#{id, jdbcType=INTEGER}, #{name, jdbcType=VARCHAR}, " +
                    "#{originalPrice, jdbcType=BIGDECIMAL}, #{promotePrice, jdbcType=BIGDECIMAL}, " +
                    "#{stock,jdbcType=INTEGER}, #{rate, jdbcType=INTEGER})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Item item);

    /**
     * 用主键删除商品
     * @param id
     * @return
     */
    @Delete({
            "delete from item" +
                    "where id=#{id, jdbcType=INTEGER}"
    })
    int deleteByPk(int id);

//    @Select({
//            "select id from item where id=#{id, jdbcType=INTEGER}"
//    })
//    int getId(Item item);

    /**
     * 查找对应商品
     * @param id 商品id
     * @return
     */
    @Select({
            "select id, name, original_price, promote_price, stock, " +
                    "rate from item where id=#{id, jdbcType=INTEGER}"
    })
    Item getItemByPk(int id);

}
