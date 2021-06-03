package com.tenzo.promote_project.mapper;

import com.tenzo.promote_project.domain.Item;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

public interface ItemMapper {

    /**
     *
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
     *
     * @param id
     * @return
     */
    @Delete({
            "delete from item" +
                    "where id=#{id, jdbcType=INTEGER}"
    })
    int deleteByPk(int id);

    @Select({
            "select id from item where id=#{id, jdbcType=INTEGER}"
    })
    int getId(Item item);


    @Select({
            "select id, name, original_price, promote_price, stock, " +
                    "rate from item where id=#{id, jdbcType=INTEGER}"
    })
    Item getItemByPk(int id);

}
