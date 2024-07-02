import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.AndroidKey;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.net.URL;
import java.util.List;

public class ParallelTests {

    private final static String APPIUM_SERVER_URL = "http://localhost:4723";
    private AndroidDriver driver;

    @BeforeTest(alwaysRun = true)
    @Parameters({"udid", "systemPort"})
    public void setup(String udid, int systemPort) throws Exception {

        URL url = new URL(APPIUM_SERVER_URL);

        UiAutomator2Options options = new UiAutomator2Options()
            .setDeviceName("Android Emulator")
            .setPlatformName("Android")
            .setUdid(udid)
            .setSystemPort(systemPort)
            .setAppPackage("com.workingagenda.devinettes")
            .setAppActivity("MainActivity")
            .setNoReset(false);

        driver = new AndroidDriver(url, options);
        
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
        WebElement textView = (WebElement) riddleList.get(0);

        System.out.println(textView.getText());
        textView.click();

        //riddlescreen - answer 1st question
        WebElement editText = driver.findElement(By.id("guess"));
        editText.sendKeys("human");
        //validate answer
        WebElement btnGuess = driver.findElement(By.id("btnGuess"));
        btnGuess.click();
        //check answer is correct
        WebElement answerTxtview = driver.findElement(By.id("riddlecheck"));

        Assert.assertEquals(answerTxtview.getText(),"Correct");

        //Go back to the previous screen to launch the second test
        driver.pressKey(new KeyEvent(AndroidKey.BACK));

        /*----- SECOND TEST ------*/
        //click on the 2nd riddle
        textView = (WebElement) riddleList.get(2);
        System.out.println(textView.getText());
        textView.click();
        //riddlescreen - answer 1st question
        editText = driver.findElement(By.id("guess"));
        editText.sendKeys("vein");
        //validate answer
        btnGuess = driver.findElement(By.id("btnGuess"));
        btnGuess.click();
        //check answer is correct
        answerTxtview = driver.findElement(By.id("riddlecheck"));

        Assert.assertEquals(answerTxtview.getText(),"Correct");

        return ;
    }

    @AfterTest
    public void tearDown() throws Exception {
        driver.quit();
    }


}
