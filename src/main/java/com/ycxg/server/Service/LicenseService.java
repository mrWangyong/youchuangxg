package com.ycxg.server.Service;

import com.sun.xml.internal.xsom.impl.scd.Iterators;
import com.ycxg.server.Result.PackageInfo;
import com.ycxg.server.model.License;

import java.util.List;
import java.util.Map;

public interface LicenseService {
    int addLicense(License license);
    List<License> findAllLicense(Map map);
    int countLicense(Map map);
    List<License> findLicenseByAccreditCode(License license); //验证授权码和包名
    List<License> findLicenseByUniqueMark(License license); //验证唯一标识
    int updataLicenseBylicenseId(License license);
    List<License> findLicenseByPackageName(License license); //验证唯一标识

    List<PackageInfo> findLicensePackageName(Map map);
}
