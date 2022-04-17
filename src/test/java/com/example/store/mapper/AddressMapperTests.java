package com.example.store.mapper;

import com.example.store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @author JlX
 * @create 2022-04-10 15:14
 */
@RunWith(SpringRunner.class)//表示启动这个单元测试类(单元测试类是不能够运行的)，需要传递一个参数，必须是SpringRunner的实例类型
@SpringBootTest //标注当前的类是一个测试类，不会打包，打包的时候自动过滤
public class AddressMapperTests {
    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insert() {
        Address address = new Address();
        address.setUid(11);
        address.setName("admin");
        address.setPhone("17858802974");
        address.setAddress("雁塔区小寨赛格");
        Integer rows = addressMapper.insert(address);
        System.out.println("rows=" + rows);
    }

    @Test
    public void countByUid() {
        Integer uid = 11;
        Integer count = addressMapper.countByUid(uid);
        System.out.println("count=" + count);
    }

    @Test
    public void findByUid() {
        Integer uid = 12;
        List<Address> list = addressMapper.findByUid(uid);
        System.out.println("count=" + list.size());
        for (Address item : list) {
            System.out.println(item);
        }
    }

    @Test
    public void updateNonDefaultByUid() {
        Integer uid = 11;
        Integer rows = addressMapper.updateNonDefaultByUid(uid);
        System.out.println("rows=" + rows);
    }

    @Test
    public void updateDefaultByAid() {
        Integer aid = 7;
        String modifiedUser = "管理员";
        Date modifiedTime = new Date();
        Integer rows = addressMapper.updateDefaultByAid(aid, modifiedUser, modifiedTime);
        System.out.println("rows=" + rows);
    }

    @Test
    public void findByAid() {
        Integer aid =6;
        Address result = addressMapper.findByAid(aid);
        System.out.println(result);
    }

    @Test
    public void deleteByAid() {
        Integer aid = 2;
        Integer rows = addressMapper.deleteByAid(aid);
        System.out.println("rows=" + rows);
    }

    @Test
    public void findLastModified() {
        Integer uid = 12;
        Address result = addressMapper.findLastModified(uid);
        System.out.println(result);
    }

   /* @Test
    public void updateAddressByAid(){
        addressMapper.updateAddressByAid()
    }
*/
}
