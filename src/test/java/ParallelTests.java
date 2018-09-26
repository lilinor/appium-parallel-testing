import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.net.URL;
import java.util.List;

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
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.UDID, udid);
        capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, systemPort);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.workingagenda.devinettes");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "MainActivity");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);

        driver = new AndroidDriver<MobileElement>(url, capabilities);
        
    }

    @Test
    public void test() {
    }

    @Test
    public void testAnswerRiddle() throws Exception {

        //get thread ID
        long id = Thread.currentThread().getId();
        System.out.println("testText Thread ID is : " +id);

        List riddleList = driver.findElements(By.id("riddle_title"));

        /*----- FIRST TEST ------*/
        AndroidElement textView = (AndroidElement) riddleList.get(0);

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

        Assert.assertEquals(answerTxtview.getText(),"Correct");

        //Go back to the previous screen to launch the second test
        driver.pressKeyCode(AndroidKeyCode.BACK);

        /*----- SECOND TEST ------*/
        //click on the 2nd riddle
        textView = (AndroidElement) riddleList.get(2);
        System.out.println(textView.getText());
        textView.click();
        //riddlescreen - answer 1st question
        editText = (AndroidElement) driver.findElementById("guess");
        editText.sendKeys("vein");
        //validate answer
        btnGuess = (AndroidElement) driver.findElementById("btnGuess");
        btnGuess.click();
        //check answer is correct
        answerTxtview = (AndroidElement) driver.findElementById("riddlecheck");

        Assert.assertEquals(answerTxtview.getText(),"Correct");

        return ;
    }

    @AfterTest
    public void tearDown() throws Exception {
        driver.quit();
    }


}
