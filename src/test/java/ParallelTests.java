import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ParallelTests {

    private final static String APPIUM_SERVER_URL = "http://localhost:4723/wd/hub";
    private AndroidDriver driver;

    @BeforeTest(alwaysRun = true)
    @Parameters({"udid", "systemPort"})
    public void setup(String udid, int systemPort) throws Exception {

        URL url = new URL(APPIUM_SERVER_URL);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.UDID, udid);
        capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, systemPort);
        capabilities.setCapability(MobileCapabilityType.APP, "/Users/ekwok/Documents/Appium/Seetest/Apps/appriddle.apk");
        capabilities.setCapability(MobileCapabilityType.ORIENTATION, "PORTRAIT");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);

        driver = new AndroidDriver<MobileElement>(url, capabilities);

        // Setup implicit wait to 10 seconds to wait for mobile elements.
        // Use a higher value if your mobile elements take time to show up
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void testAnswerRiddle() throws Exception {

        //get thread ID
        long id = Thread.currentThread().getId();
        System.out.println("testText Thread ID is : " +id);
        //click on the first riddle
        AndroidElement textView = (AndroidElement) driver.findElementById("riddle_title");
        System.out.println(textView.getText());
        textView.click();
        //riddlescreen - answer 1st question
        AndroidElement editText = (AndroidElement) driver.findElementById("guess");
        editText.sendKeys("human");
        //validate answer
        AndroidElement btnGuess = (AndroidElement) driver.findElementById("btnGuess");
        btnGuess.click();
        //check answer is correct
        AndroidElement answerTxtview = (AndroidElement) driver.findElementById("riddlecheck");
        //takeScreenShot();

        if(answerTxtview.getText().equals("Correct"))
            System.out.println("true");
        else
            throw new Exception("wrong");

        return ;
    }

    @AfterTest
    public void tearDown() throws Exception {
        driver.quit();
    }


}
