package helpers;

import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TakeScreen {
    @Attachment(value = "Falure screenshot", type = "image/png")
    public static byte[] takeScreenshot(WebDriver driver, String testName) {
        //return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
        //TakesScreenshot - это интерфейс
        // (TakesScreenshot) getDriver() - это приведение метода гетДрайвер к интерфейсу TakesScreenshot

        try {String screenshotName = testName + "_" + System.currentTimeMillis() + ".png";
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File("screenshots/" + screenshotName));
            return Files.readAllBytes(Paths.get("screenshots\\" + screenshotName));
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}