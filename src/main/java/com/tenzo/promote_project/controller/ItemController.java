package com.tenzo.promote_project.controller;

import com.tenzo.promote_project.domain.Item;
import com.tenzo.promote_project.mapper.ItemMapper;
import com.tenzo.promote_project.service.RedisService;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Random;

@RestController
public class ItemController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private ItemMapper itemMapper;

    private static final String STOCK_PREFIX = "_cachedStock";

    private static final String RATE_PREFIX = "_cachedRate";

    /**
     *
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
        Item item = new Item();
        item.setName(name);
        item.setOriginalPrice(originalPrice);
        item.setPromotePrice(promotePrice);
        item.setStock(stock);
        item.setRate(rate);
        itemMapper.insert(item);
        /**
         * shujuku zijian id
         * snow_flake algo
         */
        redisService.set(STOCK_PREFIX + itemMapper.getId(item), stock);
        redisService.set(RATE_PREFIX + itemMapper.getId(item), rate);
        return "Insert success!";
    }

    /**
     *
     * @param uid
     * @param itemId
     * @return
     */
    @ResponseBody
    @RequestMapping("/getItem")
    public String getItem(int uid, int itemId) {
        String itemStock = STOCK_PREFIX + itemId;
        String itemRate = RATE_PREFIX + itemId;

        String stock = (String) redisService.get(itemStock);
        String rate = (String) redisService.get(itemRate);

        if (StringUtils.isBlank(stock) || Integer.parseInt(stock) < 1) {
            return "";
        }

        int rateNum = Integer.parseInt(rate);
        Random random = new Random();
        int randNum = random.nextInt(rateNum);

        if (randNum<rateNum) {
            return "";
        }
        redisService.decr(itemStock, 1);

        Item item = itemMapper.getItemByPk(itemId);

        itemMapper.deleteByPk(itemId);
        int currStock = item.getStock();
        item.setStock(currStock-1);
        itemMapper.insert(item);
        return "";
    }

    /**
     *
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
    public String modifyItem(Integer id, String name, BigDecimal originalPrice, BigDecimal promotePrice, Integer stock, Integer rate) {
        Item item = itemMapper.getItemByPk(id);
        Item newItem = new Item();
        newItem.setName(name);
        newItem.setOriginalPrice(originalPrice);
        newItem.setPromotePrice(promotePrice);
        newItem.setStock(stock);
        newItem.setRate(rate);

        itemMapper.deleteByPk(id);
        itemMapper.insert(newItem);

        return "";
    }
}
