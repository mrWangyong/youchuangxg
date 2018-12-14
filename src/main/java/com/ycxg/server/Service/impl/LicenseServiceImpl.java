package com.ycxg.server.Service.impl;

import com.github.pagehelper.PageHelper;
import com.ycxg.server.Result.PackageInfo;
import com.ycxg.server.Service.LicenseService;
import com.ycxg.server.mapper.LicenseMapper;
import com.ycxg.server.model.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service(value = "licenseService")
public class LicenseServiceImpl implements LicenseService {
    @Autowired
    private LicenseMapper licenseMapper;
    @Override
    public int addLicense(License license) {
        return licenseMapper.insertSelective(license);
    }

    @Override
    public List<License> findAllLicense(Map map) {
        int pageNum =(int) map.get("pageNum");
        int pageSize =(int) map.get("pageSize");
        PageHelper.startPage(pageNum,pageSize);
        List<License> list=licenseMapper.findAllLicense(map);
        return list;
    }

    @Override
    public List<License> findLicenseByAccreditCode(License license) {
        return licenseMapper.findLicenseByAccreditCode(license);
    }

    @Override
    public List<License> findLicenseByPackageName(License license) {
        return licenseMapper.findLicenseByPackageName(license);
    }

    @Override
    public List<License> findLicenseByUniqueMark(License license) {
        return licenseMapper.findLicenseByUniqueMark(license);
    }

    @Override
    public int updataLicenseBylicenseId(License license) {
        return licenseMapper.updateByPrimaryKey(license);
    }

    @Override
    public int countLicense(Map map) {
        return licenseMapper.countLicense(map);
    }

    @Override
    public List<PackageInfo> findLicensePackageName(Map map) {
//        int pageNum =(int) map.get("pageNum");
//        int pageSize =(int) map.get("pageSize");
//        PageHelper.startPage(pageNum,pageSize);
//        List<License> list=licenseMapper.selectAllLicense();
        return licenseMapper.findLicensePackageName(map);
    }
}
