package exp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class AttachmentTest {

    public static void scrollToTheElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
    @Test
    public void attachmentTest() throws InterruptedException {
        WebDriverManager.chromedriver().setup();//отвечает за синхронизацию версий браузера
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://ilcarro.web.app/let-car-work");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                    By.xpath("//input[@id='photos']"),0));
            WebElement addPhoto =
                    driver.findElement(By.xpath("//input[@id='photos']"));
            scrollToTheElement(driver, addPhoto);
            addPhoto.sendKeys("/Users/marina/Documents/VISA USA_23/DS-160_23/EGOR/IMG_6904.JPG");
            Thread.sleep(3000);

        }
        finally {
            if(driver != null) {
                driver.quit();
            }
        }


    }
}
