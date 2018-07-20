package com.ycxg.server.Controller;

import com.ycxg.server.Service.LicenseService;
import com.ycxg.server.model.License;
import license.CreateLicense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSON;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
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
    public Map<String , Object> findAllUser(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){

        Map<String , Object> result = new HashMap<String , Object>();
        List<License> LicenseList = licenseService.findAllLicense(pageNum,pageSize);
        int count = licenseService.countLicense();
        result.put("resultCode","200");
        result.put("message","请求成功");
        result.put("count",count);
        result.put("LicenseList",LicenseList);
        return  result  ;

       // return licenseService.findAllLicense(pageNum,pageSize);
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
                result.put("resultCode","200");
                result.put("message","参数错误");
                return  result  ;
            }

            String decodedTextBase64 = new String(decoder.decode(str),"UTF-8");

            License licenseRequest = JSON.parseObject(decodedTextBase64,License.class);
            List<License> LicenseList = licenseService.findLicenseByAccreditCode(licenseRequest);

            if ( LicenseList.size() == 0){
                result.put("resultCode","200");
                result.put("message","授权码或者包名不存在");
                return  result  ;
            }

            License license1 = LicenseList.get(0);
            int count = LicenseList.size();  //设备数量（实际数量）
            int LicenseDeviceCount = license1.getLicenseDeviceCount(); //数据库中设定的数量

            //验证唯一标识是否存在（设备是否存在）
            List<License> licenseUniqueMarkList = licenseService.findLicenseByUniqueMark(licenseRequest) ;
            boolean exist = true ;  //是否存在标识（默认存在）
            if ( licenseUniqueMarkList.size() == 0){  // 不存在
                count ++ ;
                exist = false ;
            }
            if (count > LicenseDeviceCount) {   //实际数量大于设定的设备数量

                if (exist) {
                    licenseRequest.setLicenseState(0);
                    licenseService.updataLicenseBylicenseId(licenseRequest);
                }

                result.put("resultCode","200");
                result.put("message","设备数量超限");
                return  result  ;
            }

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    //    	String date = df.format(new Date().getTime());// new Date()为获取当前系统时间，也可使用当前时间戳

            Long date = new Date().getTime();

            Long databaseData = license1.getLicenseUseTimeLimit().getTime();

            if (date > databaseData) {   //当前时间大于设定的时间，时间超限

                if (exist) {
                    licenseRequest.setLicenseState(0);
                    licenseService.updataLicenseBylicenseId(licenseRequest);
                }

                result.put("resultCode","200");
                result.put("message","时间超限");
                return  result  ;
            }

            System.out.println("当前时间="+date);
            System.out.println("数据库时间="+databaseData);

            java.sql.Date currTime = getCurrTime();


            license1.setLicenseUniqueMark(licenseRequest.getLicenseUniqueMark());
            license1.setLicensePackageName(licenseRequest.getLicensePackageName());
            license1.setLicenseClientType(licenseRequest.getLicenseClientType());

            licenseRequest.setLicenseState(1);
            if(exist){   //存在就更新
                licenseRequest.setLicenseUpdateTime(currTime);
                licenseService.updataLicenseBylicenseId(licenseRequest);
            }else {  // 不存在就新增
                licenseRequest.setLicenseCreateTime(currTime);  //新增创建时间
                licenseService.addLicense(licenseRequest);
            }

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


    /*
    * 新增应用
    * */
    @ResponseBody
    @RequestMapping(value = "/addApp",produces = {"application/json;charset=UTF-8"} , method = RequestMethod.POST)
    public Map<String , Object>   addApp(
            @RequestBody  License license) throws FileNotFoundException, UnsupportedEncodingException {
        Map<String , Object> result = new HashMap<String , Object>();

        int type =  licenseService.addLicense(license);

        result.put("resultCode","200");
        result.put("message","成功");
        result.put("type",type);
        return  result ;
    }

    /*
     * 计算总条数
     * */
    @ResponseBody
    @RequestMapping(value = "/countLicense",produces = {"application/json;charset=UTF-8"} , method = RequestMethod.GET)
    public Map<String , Object>  countLicense() throws FileNotFoundException, UnsupportedEncodingException {
        Map<String , Object> result = new HashMap<String , Object>();
        int count =  licenseService.countLicense();
        result.put("resultCode","200");
        result.put("message","成功");
        result.put("count",count);
        return  result ;
    }

    public java.sql.Date getCurrTime (){
        java.sql.Date sdate;//数据库支持类型
        java.util.Date udate;
        udate = new java.util.Date();//获取系统时间
        sdate = new java.sql.Date(udate.getTime());//类型转换
        return sdate ;
    }
}
