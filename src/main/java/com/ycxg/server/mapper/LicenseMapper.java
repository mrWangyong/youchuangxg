package com.ycxg.server.mapper;

import com.ycxg.server.model.License;

import java.util.List;

public interface LicenseMapper {
    int deleteByPrimaryKey(Integer licenseId);

    int insert(License record);

    int insertSelective(License record);

    int countLicense(); //计算总数

    License selectByPrimaryKey(Integer licenseId);

    int updateByPrimaryKeySelective(License record);

    int updateByPrimaryKey(License record);

    List<License> selectAllLicense();
    List<License> findLicenseByAccreditCode(License license);

    List<License> findLicenseByUniqueMark(License license);
}