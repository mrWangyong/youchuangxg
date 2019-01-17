package com.ycxg.server.Service;

import com.ycxg.server.model.UrlInfo;

import java.util.List;

public interface UrlInfoService {

    List<UrlInfo> getList();

    int addUrl(UrlInfo urlInfo);

    int editUrl(UrlInfo urlInfo);

    int deleteUrl(UrlInfo urlInfo);
}
