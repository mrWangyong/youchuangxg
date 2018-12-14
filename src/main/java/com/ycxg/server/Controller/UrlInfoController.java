package com.ycxg.server.Controller;


import com.ycxg.server.Service.UrlInfoService;
import com.ycxg.server.model.UrlInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "UrlInfo")
public class UrlInfoController {

    @Autowired
    private UrlInfoService urlInfoService;
    /*
     *  网址列表
     *
     * */

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/getList",produces = {"application/json;charset=UTF-8"}, method = RequestMethod.POST)
    public Map<String , Object> getList(){
        Map<String , Object> result = new HashMap<String , Object>();
        List<UrlInfo> list=  urlInfoService.getList();
        result.put("resultCode","200");
        result.put("message","请求成功");
        result.put("urlList",list);
        return  result  ;
    }

    @ResponseBody
    @RequestMapping(value = "/addUrl",produces = {"application/json;charset=UTF-8"}, method = RequestMethod.POST)
    public Map<String , Object> addUrl(@RequestBody UrlInfo urlInfo){
        Map<String , Object> result = new HashMap<String , Object>();
        int res =  urlInfoService.addUrl(urlInfo);
        result.put("resultCode","200");
        result.put("message","请求成功");
        result.put("res",res);
        return  result  ;
    }
}
