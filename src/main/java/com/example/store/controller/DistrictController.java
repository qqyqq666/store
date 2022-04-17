package com.example.store.controller;

import com.example.store.entity.District;
import com.example.store.service.IDistrictService;
import com.example.store.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author JlX
 * @create 2022-04-10 17:33
 */
@RestController
@RequestMapping("districts")
public class DistrictController extends BaseController{
    @Autowired
    private IDistrictService districtService;

    @GetMapping({"", "/"}) //加不加/都可以执行
    //@RequestMapping(method={RequestMethod.GET}) 或者是@RequestMapping("/")
    public JsonResult<List<District>> getByParent(String parent) {
        List<District> data = districtService.getByParent(parent);
        return new JsonResult<>(OK, data);
    }
}
