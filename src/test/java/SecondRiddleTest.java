import io.appium.java_client.android.AndroidElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SecondRiddleTest extends BaseTest {

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

        Assert.assertEquals(answerTxtview.getText(),"true");

        return ;
    }
}
