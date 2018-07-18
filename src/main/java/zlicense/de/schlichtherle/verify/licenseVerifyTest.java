package zlicense.de.schlichtherle.verify;

public class licenseVerifyTest {
	public static void test(String[] args){
		VerifyLicense vLicense = new VerifyLicense();
		try{

			vLicense.setParam("D:\\tmp\\param.properties");

			vLicense.verify();
		}
		catch(Exception er){
			er.printStackTrace();
		}

	}
}
