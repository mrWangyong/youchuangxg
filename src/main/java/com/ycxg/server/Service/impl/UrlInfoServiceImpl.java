package com.ycxg.server.Service.impl;

import com.ycxg.server.Service.UrlInfoService;
import com.ycxg.server.mapper.UrlInfoMapper;
import com.ycxg.server.model.UrlInfo;
import com.ycxg.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "UrlInfo")
public class UrlInfoServiceImpl implements UrlInfoService {

    @Autowired
    private UrlInfoMapper urlInfoMapper;

    @Override
    public  List<UrlInfo> getList() {
        return urlInfoMapper.getList();
    }

    @Override
    public  int addUrl(UrlInfo urlInfo){
        return urlInfoMapper.addUrl(urlInfo);
    }
}
