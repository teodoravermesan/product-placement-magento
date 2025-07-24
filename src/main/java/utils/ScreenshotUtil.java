package utils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class ScreenshotUtil {

    public static void takeScreenshot(WebDriver driver, String fileName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        String dirPath = "./screenshots";
        File dir = new File(dirPath);

        if (!dir.exists()) {
            dir.mkdir();
        }

        try {
            FileUtils.copyFile(source, new File(dirPath + "/" + fileName + ".png"));
            System.out.println("Screenshot saved: " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving screenshot: " + e.getMessage());
        }
    }
}
