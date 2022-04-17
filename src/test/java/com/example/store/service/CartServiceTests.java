package com.example.store.service;


import com.example.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTests {
    @Autowired
    private ICartService cartService;

    @Test
    public void addToCart() {
        try {
            Integer uid = 11;
            Integer pid = 10000007;
            Integer amount = 3;
            String username = "Tom";
            cartService.addToCart(uid, pid, amount, username);
            System.out.println("OK.");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

//    @Test
//    public void getVOByUid() {
//        List<CartVO> list = cartService.getVOByUid(31);
//        System.out.println("count=" + list.size());
//        for (CartVO item : list) {
//            System.out.println(item);
//        }
//    }
//
//    @Test
//    public void addNum() {
//        try {
//            Integer cid = 6;
//            Integer uid = 31;
//            String username = "管理员";
//            Integer num = cartService.addNum(cid, uid, username);
//            System.out.println("OK. New num=" + num);
//        } catch (ServiceException e) {
//            System.out.println(e.getClass().getSimpleName());
//            System.out.println(e.getMessage());
//        }
//    }
//
//    @Test
//    public void getVOByCids() {
//        Integer[] cids = {1, 2, 6, 7, 8, 9, 10};
//        Integer uid = 31;
//        List<CartVO> list = cartService.getVOByCids(uid, cids);
//        System.out.println("count=" + list.size());
//        for (CartVO item : list) {
//            System.out.println(item);
//        }
//    }

}
