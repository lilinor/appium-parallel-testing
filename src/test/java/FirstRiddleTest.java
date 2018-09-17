import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class FirstRiddleTest extends BaseTest {

    @Test
    public void testAnswerRiddle() throws Exception {

        //get thread ID
        long id = Thread.currentThread().getId();
        System.out.println("testText Thread ID is : " +id);
        //click on the third riddle
        List riddleList = driver.findElements(By.id("riddle_title"));
        AndroidElement textView = (AndroidElement) riddleList.get(2);
        System.out.println(textView.getText());
        textView.click();
        //riddlescreen - answer 1st question
        AndroidElement editText = (AndroidElement) driver.findElementById("guess");
        editText.sendKeys("vein");
        //validate answer
        AndroidElement btnGuess = (AndroidElement) driver.findElementById("btnGuess");
        btnGuess.click();
        //check answer is correct
        AndroidElement answerTxtview = (AndroidElement) driver.findElementById("riddlecheck");

        Assert.assertEquals(answerTxtview.getText(),"Correct");

        return ;
    }

}
