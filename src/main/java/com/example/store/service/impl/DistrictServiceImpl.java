package com.example.store.service.impl;

import com.example.store.entity.District;
import com.example.store.mapper.DistrictMapper;
import com.example.store.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author JlX
 * @create 2022-04-10 17:25
 */
/** 处理省/市/区数据的业务层实现类 */
@Service
public class DistrictServiceImpl implements IDistrictService {
    @Autowired
    private DistrictMapper districtMapper;
    @Override
    public List<District> getByParent(String parent) {
        List<District> districts = districtMapper.findByParent(parent);
        //快捷键iter
        for(District district : districts){
            district.setId(null);  //查出来了，这两条数据就不需要了
            district.setParent(null);
        }

        return districts;
    }

    @Override
    public String getNameByCode(String code) {
        return districtMapper.findNameByCode(code);
    }
}
