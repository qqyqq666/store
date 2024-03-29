package com.example.store.service;

import com.example.store.entity.Order;

/**
 * @author JlX
 * @create 2022-04-15 19:04
 */
public interface IOrderService {
    /**
     * 创建订单
     * @param aid 收货地址的id
     * @param cids 即将购买的商品数据在购物车表中的id
     * @param uid 当前登录的用户的id
     * @param username 当前登录的用户名
     * @return 成功创建的订单数据
     */
    Order create(Integer aid, Integer[] cids, Integer uid, String username);
}
