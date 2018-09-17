import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.net.URL;

public class BaseTest {

    private final static String APPIUM_SERVER_URL = "http://localhost:4723/wd/hub";

    protected AndroidDriver driver;

    @BeforeTest(alwaysRun = true)
    @Parameters({"udid", "systemPort"})
    public void setup(String udid, int systemPort) throws Exception {

        URL url = new URL(APPIUM_SERVER_URL);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.UDID, udid);
        capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, systemPort);
        capabilities.setCapability(MobileCapabilityType.APP, "/Users/ekwok/Documents/Appium/Seetest/Apps/appriddle.apk");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);

        driver = new AndroidDriver<MobileElement>(url, capabilities);

        // Setup implicit wait to 10 seconds to wait for mobile elements.
        // Use a higher value if your mobile elements take time to show up
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterTest
    public void tearDown() throws Exception {
        driver.quit();
    }
}