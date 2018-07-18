package com.ycxg.server.Service;

import com.ycxg.server.model.License;

import java.util.List;

public interface LicenseService {
    int addLicense(License license);
    List<License> findAllLicense(int pageNum, int pageSize);
    List<License> findLicenseByAccreditCode(String licenseAccreditCode);
    int updataLicenseBylicenseId(License license);
}
