package tests;

import com.beust.ah.A;
import config.BaseTest;
import helpers.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import model.Contact;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.AddPage;
import pages.ContactsPage;
import pages.LoginPage;
import pages.MainPage;

import java.io.IOException;
import java.sql.Driver;
import java.time.Duration;
import java.util.List;
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

    @Test
    public void deleteContact() throws InterruptedException {
        Allure.description("User already exist. Delete contact by phone number!");
        MainPage mainPage = new MainPage(getDriver());
        Allure.step("Step 1");
        LoginPage lpage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        Allure.step("Step 2");
        lpage.fillEmailField(PropertiesReader.getProperty("existingUserEmail"))
                .fillPasswordField(PropertiesReader.getProperty("existingUserPassword"))
                .clickByLoginButton();
        ContactsPage contactsPage = new ContactsPage(getDriver());
        Assert.assertNotEquals(contactsPage.deleteContactByPhoneNumberOrName("2101225254138"),
                contactsPage.getContactsListSize(),"Contact lists are different");

    }

    @Test
    public void deleteContactApproachTwo() throws IOException {
        String filename = "contactDataFile.ser";
        MainPage mainPage = new MainPage(getDriver());
        LoginPage lpage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        lpage.fillEmailField(PropertiesReader.getProperty("existingUserEmail"))
                .fillPasswordField(PropertiesReader.getProperty("existingUserPassword"))
                .clickByLoginButton();
        MainPage.openTopMenu(TopMenuItem.ADD.toString());
        AddPage addPage = new AddPage(getDriver());
        Contact newContact = new Contact(NameAndLastNameGenerator.generateName(),NameAndLastNameGenerator.generateLastName(),
                PhoneNumberGenerator.generatePhoneNumber(),
                EmailGenerator.generateEmail(10,5,3),
                AddressGenerator.generateAddress(), "Test description");
        addPage.fillFormAndSave(newContact);
        Contact.serializeContact(newContact, filename);
        ContactsPage contactsPage = new ContactsPage(getDriver());
        Contact deserContact = Contact.desiarializeContact(filename);
        Assert.assertNotEquals(contactsPage.deleteContactByPhoneNumberOrName(deserContact.getPhone()),
                contactsPage.getContactsListSize());

    }

    @Test
    public void registrationOfAnAlreadyRegisteredUser() {
     //   WebDriver driver = new FirefoxDriver();
        Allure.description("User already exist. Registration of an already registrated user!");
        MainPage mainPage = new MainPage(getDriver());
        Allure.step("Open Registration page");
        LoginPage lpage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        Allure.step("Fill in email and password by resources");
        String email = EmailGenerator.generateEmail(10,5,3);
        String password = PasswordStringGenerator.generateString();

        lpage.fillEmailField(email)
                .fillPasswordField(password).clickByRegistrationButton();
//        lpage.fillEmailField(PropertiesReader.getProperty("existingUserEmail"))
//                .fillPasswordField(PropertiesReader.getProperty("existingUserPassword"))
//                .clickByRegistrationButton();
        Allure.step("Make sure that Sign out [button] was displayed");
        WebElement signOutButton = getDriver().findElement(By.xpath("//button[contains(text(),'Sign')]"));
        signOutButton.click();
        TakeScreen.takeScreenshot("screen");
        Allure.step("Fill in email and password that already exist");
//        lpage.fillEmailField(PropertiesReader.getProperty("existingUserEmail"))
//                .fillPasswordField(PropertiesReader.getProperty("existingUserPassword"))
//                .clickByRegistrationButton();
        Allure.step("Make sure that Allert [User already exist] was displayed");
        String exectedString = "exist";

//        Alert alert = lpage.fillEmailField(PropertiesReader.getProperty("existingUserEmail"))
//                .fillPasswordField(PropertiesReader.getProperty("existingUserPassword"))
//                .clickByRegistrationButton();
        Alert alert = lpage.fillEmailField(email)
                .fillPasswordField(password)
                .clickByRegistrationButton();

          //  WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        boolean isAllertHandled = AlertHandler.handleAlert(alert,exectedString);
        Assert.assertTrue(isAllertHandled);

    }
}
