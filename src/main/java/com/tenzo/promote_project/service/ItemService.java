package com.tenzo.promote_project.service;

import com.tenzo.promote_project.domain.Item;
import com.tenzo.promote_project.domain.Order;
import com.tenzo.promote_project.mapper.ItemMapper;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class ItemService {

    @Autowired
    ItemMapper itemMapper;

    @Autowired
    RedisService redisService;

    @Autowired
    OrderService orderService;

    private static final String STOCK_PREFIX = "_cachedStock";

    private static final String RATE_PREFIX = "_cachedRate";

    private static final String ORDER_PREFIX = "_cachedOrder";

    /**
     * 新建商品
     * @param name
     * @param originalPrice
     * @param promotePrice
     * @param stock
     * @param rate
     */
    public void insertItem(String name, BigDecimal originalPrice,
                             BigDecimal promotePrice, Integer stock, Integer rate) {
        Item item = new Item();
        item.setName(name);
        item.setOriginalPrice(originalPrice);
        item.setPromotePrice(promotePrice);
        item.setStock(stock);
        item.setRate(rate);
        itemMapper.insert(item);
        /**
         * 数据库自建id
         * TODO: snow_flake algo
         */
        redisService.set(STOCK_PREFIX + item.getId(), stock);
        redisService.set(RATE_PREFIX + item.getId(), rate);
    }

    /**
     * 尝试购买商品
     * @param uid 购买用户id
     * @param itemId
     * @return
     * @throws Exception
     */
    public String getItem(int uid, int itemId) throws Exception {
        String itemStock = STOCK_PREFIX + itemId;
        String itemRate = RATE_PREFIX + itemId;
        //创建重试策略， 最多尝试重连10次每次超时时间为2s
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(2000,10);

        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("192.168.243.11").retryPolicy(retryPolicy).build();
        curatorFramework.start();
        String rate = (String) redisService.get(itemRate);

        final InterProcessMutex mutex = new InterProcessMutex(curatorFramework, "/myMutex");

            /**
             * 验证是否中奖(可以购买)
             */
            int rateNum = Integer.parseInt(rate);
            Random random = new Random();
            int randNum = random.nextInt(rateNum);
            if (randNum>rateNum) {
                return "请换个姿势再试一次";
            }
            // 验证是否还有库存
            if (redisService.isSoldOut(itemId+"")) {
                return "来晚一步~商品已经被抢空了";
            }
            try {
                // 获取锁
                mutex.acquire();
                /**
                 * 扣减缓存库存
                 */
                redisService.decr(itemStock, 1);
                Item item = itemMapper.getItemByPk(itemId);
                // 开始访问共享资源
                itemMapper.deleteByPk(itemId);
                int currStock = item.getStock();
                item.setStock(currStock-1);
                itemMapper.insert(item);
                orderService.setOrder(uid, itemId);
                return "购买成功！";
            } catch (Exception e) {
                // 如果出现异常， 把库存补回
                redisService.incr(itemStock, 1);
                e.printStackTrace();
            } finally {
                try {
                    mutex.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        return "购买时出现问题";
    }

    /**
     * 修改商品信息
     * @param id
     * @param name
     * @param originalPrice
     * @param promotePrice
     * @param stock
     * @param rate
     */
    public void modifyItem(Integer id, String name, BigDecimal originalPrice,
                           BigDecimal promotePrice, Integer stock, Integer rate)
    {
        Item item = itemMapper.getItemByPk(id);
        Item newItem = new Item();
        newItem.setName(name);
        newItem.setOriginalPrice(originalPrice);
        newItem.setPromotePrice(promotePrice);
        newItem.setStock(stock);
        newItem.setRate(rate);
        itemMapper.deleteByPk(id);
        itemMapper.insert(newItem);
    }
}
