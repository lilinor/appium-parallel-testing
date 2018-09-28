import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ParallelTests {

    private final static String APPIUM_SERVER_URL = "http://localhost:4723/wd/hub";
    private AndroidDriver driver;
    private boolean isFirstPlayed = true;

    @BeforeSuite
    public void beforeSuite(ITestContext context) {
        for (ITestNGMethod method : context.getAllTestMethods()) {
            method.setRetryAnalyzer(new RetryAnalyzer());
        }
    }

    @BeforeTest
    @Parameters({"udid", "systemPort"})
    public void setup(String udid, int systemPort) throws Exception {

        URL url = new URL(APPIUM_SERVER_URL);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.UDID, udid);
        capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, systemPort);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.workingagenda.devinettes");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "MainActivity");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60000);

        driver = new AndroidDriver<MobileElement>(url, capabilities);

    }

    @Test
    public void testAnswerRiddle() throws Exception {

        //If the test is played again due to failure, it needs to relaunch the app
        if(!isFirstPlayed) {
            driver.launchApp();
        }
        //get thread ID
        long id = Thread.currentThread().getId();
        System.out.println("testText Thread ID is : " +id);
        //click on the first riddle
        AndroidElement textView = (AndroidElement) driver.findElementById("riddle_title");
        textView.click();
        //riddlescreen - answer 1st question
        AndroidElement editText = (AndroidElement) driver.findElementById("guess");
        editText.sendKeys("human");
        //validate answer
        AndroidElement btnGuess = (AndroidElement) driver.findElementById("btnGuess");
        btnGuess.click();
        //check answer is correct
        AndroidElement answerTxtview = (AndroidElement) driver.findElementById("riddlecheck");

        isFirstPlayed = false;

        Assert.assertEquals(answerTxtview.getText(),"false");

        return ;
    }

    @AfterTest
    public void tearDown() throws Exception {
        driver.quit();
    }

}
