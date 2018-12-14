package com.ycxg.server.mapper;

import com.ycxg.server.model.UrlInfo;

import java.util.List;

public interface UrlInfoMapper {
    List<UrlInfo> getList();
    int addUrl(UrlInfo urlInfo);
}
