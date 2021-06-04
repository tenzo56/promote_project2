package com.tenzo.promote_project.controller;

import com.tenzo.promote_project.service.ItemService;
import com.tenzo.promote_project.service.OrderService;
import com.tenzo.promote_project.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderService orderService;

    private static final String STOCK_PREFIX = "_cachedStock";

    private static final String RATE_PREFIX = "_cachedRate";

    /**
     * 加入新商品
     * @param name
     * @param originalPrice
     * @param promotePrice
     * @param stock
     * @param rate
     * @return
     */
    @ResponseBody
    @RequestMapping("/insertItem")
    public String insertItem(String name, BigDecimal originalPrice,
                             BigDecimal promotePrice, Integer stock, Integer rate)
    {
        try {
            itemService.insertItem(name, originalPrice, promotePrice, stock, rate);
            return "添加商品"+name+"成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "添加时遇到错误";
    }

    /**
     * 尝试购买商品
     * @param uid
     * @param itemId
     * @return
     */
    @ResponseBody
    @RequestMapping("/getItem")
    public String getItem(int uid, int itemId) throws Exception {
        /**
         * 创建订单信息
         */
        orderService.setOrder(uid, itemId);
        return itemService.getItem(uid, itemId);
    }

    /**
     * 修改商品信息
     * @param id
     * @param name
     * @param originalPrice
     * @param promotePrice
     * @param stock
     * @param rate
     * @return
     */
    @ResponseBody
    @RequestMapping("/modifyItem")
    public String modifyItem(Integer id, String name, BigDecimal originalPrice,
                             BigDecimal promotePrice, Integer stock, Integer rate) {


        try {
            itemService.modifyItem(id, name, originalPrice, promotePrice, stock, rate);
            return "修改成功！";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "修改失败，请重试";
    }
}
