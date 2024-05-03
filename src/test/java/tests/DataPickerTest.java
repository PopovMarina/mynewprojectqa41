package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.util.Random;

public class DataPickerTest {

    @Test
    public void datapickerTest() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://material.angular.io/components/datepicker/overview");
        driver.manage().window().maximize();

        WebElement dataPicker = driver.findElement(By.xpath("//input[@id='mat-input-0']"));
        String value = generateDate();
      //String value = "10/12/2024";
        dataPicker.sendKeys(value);
        Thread.sleep(3000);
        driver.quit();

    }

public static String generateDate(){
    Random random = new Random();
    int month = random.nextInt(12)+1;
    int day;
    if(month == 2) {
        day = random.nextInt(28)+1;
    }
    else if(month == 4 || month == 6 || month == 9 || month == 11) {
        day = random.nextInt(30)+1;
    }
    else {day = random.nextInt(31)+1;}

    int year = random.nextInt(24)+2000;
    return String.format("%02d/%02d/%04d", month, day,year);

}

}
