import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.screenrecording.ScreenRecordingUploadOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class ParallelTests {

    private final static String APPIUM_SERVER_URL = "http://localhost:4723/wd/hub";
    private final static String PATH_VIDEO_FILE = "/Users/ekwok/Documents/Appium/SeeTest/Videos/video_"; //will add the device udid at the end

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

    @BeforeMethod
    public void setUp(){
        ScreenRecordingUploadOptions uOpt = new ScreenRecordingUploadOptions().withRemotePath(PATH_VIDEO_FILE);

        AndroidStartScreenRecordingOptions rOpt = new AndroidStartScreenRecordingOptions().
                withUploadOptions(uOpt);

        driver.startRecordingScreen(rOpt);
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
    @Parameters("udid")
    public void tearDown(String udid) throws Exception {

        String result = driver.stopRecordingScreen();
        decoder(result, PATH_VIDEO_FILE+udid+".mp4");

        driver.quit();
    }

    private static void decoder(String base64, String pathFile) {
        try {
            FileOutputStream imageOutFile = new FileOutputStream(pathFile);
            // Converting a Base64 String into Image byte array
            byte[] ByteArray = Base64.getMimeDecoder().decode(base64);
            System.out.println(ByteArray);
            imageOutFile.write(ByteArray);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
    }


}
