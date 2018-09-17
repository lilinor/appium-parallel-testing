import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

import static java.lang.Thread.sleep;

public class RetryAnalyzer implements IRetryAnalyzer {

    int counter = 0;
    int retryLimit = 2;
    int retryDelay = 3000; //retry delay in milliseconds after failed test

    public boolean retry(ITestResult iTestResult) {

        if(counter < retryLimit) {
            counter++;
            System.out.println("testfailed retry "+String.valueOf(counter));
            Reporter.log("testfailed retry "+String.valueOf(counter));
            try {
                sleep(retryDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
}
