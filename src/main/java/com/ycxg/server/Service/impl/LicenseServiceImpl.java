package com.ycxg.server.Service.impl;

import com.github.pagehelper.PageHelper;
import com.ycxg.server.Service.LicenseService;
import com.ycxg.server.mapper.LicenseMapper;
import com.ycxg.server.model.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "licenseService")
public class LicenseServiceImpl implements LicenseService {
    @Autowired
    private LicenseMapper licenseMapper;
    @Override
    public int addLicense(License license) {
        return licenseMapper.insert(license);
    }

    @Override
    public List<License> findAllLicense(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<License> list=licenseMapper.selectAllLicense();
        return list;
    }

    @Override
    public List<License> findLicenseByAccreditCode(String licenseAccreditCode) {
        return licenseMapper.findLicenseByAccreditCode(licenseAccreditCode);
    }

    @Override
    public int updataLicenseBylicenseId(License license) {
        return licenseMapper.updateByPrimaryKey(license);
    }
}
