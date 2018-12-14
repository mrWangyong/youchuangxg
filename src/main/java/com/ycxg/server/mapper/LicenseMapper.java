package com.ycxg.server.mapper;

import com.ycxg.server.Result.PackageInfo;
import com.ycxg.server.model.License;

import java.util.List;
import java.util.Map;

public interface LicenseMapper {
    int deleteByPrimaryKey(Integer licenseId);

    int insert(License record);

    int insertSelective(License record);

    int countLicense(Map map); //计算总数

    License selectByPrimaryKey(Integer licenseId);

    int updateByPrimaryKeySelective(License record);

    int updateByPrimaryKey(License record);

    List<License> findAllLicense(Map map);

    List<License> findLicenseByAccreditCode(License license);

    List<License> findLicenseByUniqueMark(License license);

    List<License> findLicenseByPackageName(License license);

    List<PackageInfo> findLicensePackageName(Map map);
}