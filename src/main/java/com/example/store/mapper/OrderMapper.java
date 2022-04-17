package com.example.store.mapper;

import com.example.store.entity.Order;
import com.example.store.entity.OrderItem;

/**
 * @author JlX
 * @create 2022-04-15 17:08
 */
public interface OrderMapper {
    /**
     * 插入订单数据
     * @param order 订单数据
     * @return 受影响的行数
     */
    Integer insertOrder(Order order);

    /**
     * 插入订单商品数据
     * @param orderItem 订单商品数据
     * @return 受影响的行数
     */
    Integer insertOrderItem(OrderItem orderItem);
}
