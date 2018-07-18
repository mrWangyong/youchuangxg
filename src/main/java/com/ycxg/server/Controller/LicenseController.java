package com.ycxg.server.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ycxg.server.Service.LicenseService;
import com.ycxg.server.model.License;
import license.CreateLicense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSON;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.*;


@CrossOrigin
@RestController
@RequestMapping(value = "license")
public class LicenseController {
    @Autowired
    private LicenseService licenseService;

    final Base64.Decoder decoder = Base64.getDecoder();
    final Base64.Encoder encoder = Base64.getEncoder();
    @Value("${youchuangxg.filePath}")
    private String cerPath;

    @Value("${youchuangxg.DownloadUrl}")
    private String urlDownload;

    @ResponseBody
    @RequestMapping(value = "/add/{licenseAccreditCode}/{licenseUniqueMark}/{licensePackageName}",produces = {"application/json;charset=UTF-8"})
    public Map<String , Object> addLicense(@PathVariable("licenseAccreditCode") String licenseAccreditCode,
                                           @PathVariable("licenseUniqueMark") String licenseUniqueMark,
                                           @PathVariable("licensePackageName") String licensePackageName,
                                           License license) throws FileNotFoundException {

        System.out.println("licenseAccreditCode="+licenseAccreditCode);
        System.out.println("licenseUniqueMark="+licenseUniqueMark);
        String comPaht=UUID.randomUUID().toString();
        String licPath=cerPath+"/"+comPaht+".lic";
        String publicStorePath=cerPath+"/"+"ycxgpublic.store";
        CreateLicense cLicense = new CreateLicense();
        System.out.println(System.getProperty("user.dir")+"\\ycxgparam.properties");
        //获取参数
        cLicense.setParam(System.getProperty("user.dir")+"\\ycxgparam.properties",
                licenseAccreditCode,licenseUniqueMark,licPath,licensePackageName);
        //生成证书
        cLicense.create();

        license.setLicenseLicPath(comPaht+".lic");
        license.setLicensePublicStorePath("ycxgpublic.store");
        license.setLicenseClientType("licenseClientType");
        license.setLicenseSecretkey("licenseSecretkey");

        licenseService.addLicense(license);

        String licPathDownload = urlDownload  +comPaht+".lic";

        String publicStorePathDownload = urlDownload  + "ycxgpublic.store" ;

        Map<String , Object> result = new HashMap<String , Object>();

        result.put("resultCode","200");
        result.put("message","成功");
        result.put("licPath",licPathDownload);
        result.put("publicStorePath",publicStorePathDownload);
        result.put("pathCode",true);

        return result;
      //  return licenseService.addLicense(license);
    }

    @ResponseBody
    @RequestMapping(value = "/all/{pageNum}/{pageSize}",produces = {"application/json;charset=UTF-8"})
    public Object findAllUser(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
        return licenseService.findAllLicense(pageNum,pageSize);
    }

    @ResponseBody
    @RequestMapping(value = "/getSecretKey",produces = {"application/json;charset=UTF-8"} , method = RequestMethod.POST)
    public Map<String , Object> getSecretKey(
        @RequestBody  License license) throws FileNotFoundException, UnsupportedEncodingException {
        Map<String , Object> result = new HashMap<String , Object>();
        String str = license.getLicenseSecretkey();

        try {
            String decodedTextBase64 = new String(decoder.decode(str),"UTF-8");
        } catch (IllegalArgumentException var5) {
            result.put("resultCode","500");
            result.put("message","参数错误");
            return  result  ;
        }

        String decodedTextBase64 = new String(decoder.decode(str),"UTF-8");

        License licenseRequest = JSON.parseObject(decodedTextBase64,License.class);

        List<License> LicenseList = licenseService.findLicenseByAccreditCode(licenseRequest.getLicenseAccreditCode());

        if ( LicenseList.size() == 0){
            result.put("resultCode","404");
            result.put("message","授权码不存在");
            return  result  ;
        }

        License license1 = LicenseList.get(0);

        license1.setLicenseUniqueMark(licenseRequest.getLicenseUniqueMark());
        license1.setLicensePackageName(licenseRequest.getLicensePackageName());
        license1.setLicenseClientType(licenseRequest.getLicenseClientType());

        licenseService.updataLicenseBylicenseId(license1);

        String jsonString = JSON.toJSONString(license1);
        System.out.println("jsonString:" + jsonString);

        final byte[] textByte = jsonString.getBytes("UTF-8");

        final String encodedTextBase64 = encoder.encodeToString(textByte);
        System.out.println("base64编码="+encodedTextBase64);

        result.put("resultCode","200");
        result.put("message","成功");
        result.put("encodedTextBase64",encodedTextBase64);

        return  result  ;
    }
}
