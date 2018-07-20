package com.ycxg.server.Service;

import com.ycxg.server.model.License;

import java.util.List;

public interface LicenseService {
    int addLicense(License license);
    List<License> findAllLicense(int pageNum, int pageSize);
    int countLicense();
    List<License> findLicenseByAccreditCode(License license); //验证授权码和包名
    List<License> findLicenseByUniqueMark(License license); //验证唯一标识
    int updataLicenseBylicenseId(License license);
}
