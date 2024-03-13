package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class HomePage extends BasePage{
    public HomePage(WebDriver driver){
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }
}

//PageFactory - гранит, на котором стоит автоматизация, позволяет инициализировать элементы
//которые мы ему покажем
//Ajax - позволяет искать  элементы динамически на странице,
//чтобы не было ошибок тайм-аута
