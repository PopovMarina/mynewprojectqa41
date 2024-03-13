package tests;

import config.BaseTest;
import helpers.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import model.Contact;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.AddPage;
import pages.ContactsPage;
import pages.LoginPage;
import pages.MainPage;

import java.sql.Driver;
import java.util.TooManyListenersException;

public class PhoneBookTest extends BaseTest {

    @Test(description = "The test checks the empty field warning declaration.")
    @Parameters("browser")
    public void registrationWithoutPassword(@Optional("chrome") String browser) throws InterruptedException {
        Allure.description("User already exist. Login and add contact.!");
        //Аллюр - это такая штука, которая генерирует отчеты - важнаая
        //команда для генерации отчета - через терминал идеи - allure generate allure-results --clean -o allure-report
        //--clean удаляет из файла ранее созданные отчеты
        //allure generate allure-results  -o allure-report - в таком случае предыдущие отчеты останутся (накоплением)
        //если автоматически папка Аллюр-репорт не появилась - ввести команду allure serve
        MainPage mainPage = new MainPage(getDriver());

        Allure.step("Click by Login button");
        //Степс - шаги, которые происходят во время теста, если упадет - будет видно на каком шаге
        LoginPage loginPage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        Allure.step("Click by Red button");
        String  expectedString = "Wrong";//эта строчка должна быть в другом месте
        //в отдельном классе настроек

        Alert alert = loginPage.fillEmailField("poi7777@mail.rut").clickByRegistrationButton();
        //loginPage.fillPasswordField("Mana17$").clickByLoginButton();
        boolean isAlertHandled = AlertHandler.handleAlert(alert, expectedString);
        //handlerAlert - метод обратотки окошка,которое появится
        Assert.assertTrue(isAlertHandled);

//        loginPage.fillEmailField("poi1@mail.ru").fillPasswordField("Naa1234$").clickByLoginButton();
//        Thread.sleep(8000);
    }
    @Test
    @Description("User already exist. Login and add contact.")
    public void loginOfAnExistingUserAddContact() throws InterruptedException {

        Allure.description("User already exist. Login and add contact.!");
        MainPage mainPage = new MainPage(getDriver());
        Allure.step("Step 1");
        LoginPage lpage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        Allure.step("Step 2");

        //lpage.fillEmailField("poi7777@mail.rut").clickByLoginButton();
        lpage.fillEmailField(PropertiesReader.getProperty("existingUserEmail"))
                .fillPasswordField((PropertiesReader.getProperty("existingUserPassword")))
                .clickByLoginButton();
        Allure.step("Step 3");
        MainPage.openTopMenu(TopMenuItem.ADD.toString());
        AddPage addPage = new AddPage(getDriver());
        Contact newContact = new Contact(NameAndLastNameGenerator.generateName(),
                NameAndLastNameGenerator.generateLastName(),
                PhoneNumberGenerator.generatePhoneNumber(), EmailGenerator.generateEmail(10, 5, 3),
                AddressGenerator.generateAddress(), "new description");

        newContact.toString();
        addPage.fillFormAndSave(newContact);
        ContactsPage contactsPage = new ContactsPage(getDriver());
        Assert.assertTrue(contactsPage.getDataFromContactList(newContact));
        TakeScreen.takeScreenshot("screen");
        Thread.sleep(3000);

    }
        @Test(description = "Successful registration.")
        @Parameters("browser")
    public void successfulRegistration() throws InterruptedException {
        Allure.description("Successful registration");
        MainPage mainPage = new MainPage(getDriver());
        Allure.step("Open Registration page");
        LoginPage loginPage = MainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        Allure.step("Fill in email and password by generator");
        loginPage.fillEmailField(EmailGenerator.generateEmail(10,5,3))
                .fillPasswordField(PasswordStringGenerator.generateString());
        Allure.step("Make sure that Sign out [button] was displayed");
        Alert alert = loginPage.clickByRegistrationButton();
        if(alert == null) {
            ContactsPage contactsPage = new ContactsPage(getDriver());
            Assert.assertTrue(contactsPage.isElementPersist(getDriver()
                    .findElement(By.xpath("//button[contains(text(),'Sign')]"))));
        }else {
            TakeScreen.takeScreenshot("Successful registration");
        }
//        TakeScreen.takeScreenshot("screen");
//        WebElement signOut = getDriver().findElement(By.xpath("//button[contains(text(),'Sign')]"));
//       Assert.assertTrue(signOut.isDisplayed());
//       Thread.sleep(5000);

        }

}
