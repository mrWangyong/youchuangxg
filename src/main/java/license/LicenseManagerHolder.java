package license;

import zlicense.de.schlichtherle.license.LicenseManager;
import zlicense.de.schlichtherle.license.LicenseParam;

/*import com.tiis.kstiis2.schlichtherle.license.LicenseManager;
import com.tiis.kstiis2.schlichtherle.license.LicenseParam;*/

/**
 * LicenseManager������
 * @author melina
 */
public class LicenseManagerHolder {
	
	private static LicenseManager licenseManager;
 
	public static synchronized LicenseManager getLicenseManager(LicenseParam licenseParams) {
    	if (licenseManager == null) {
    		licenseManager = new LicenseManager(licenseParams);
    	}
    	return licenseManager;
    }
}