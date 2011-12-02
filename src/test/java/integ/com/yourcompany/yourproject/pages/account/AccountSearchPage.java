package integ.com.yourcompany.yourproject.pages.account;

import static integ.com.yourcompany.yourproject.support.Interactions.clear;
import static integ.com.yourcompany.yourproject.support.Interactions.click;
import static integ.com.yourcompany.yourproject.support.Interactions.write;
import static org.openqa.selenium.By.cssSelector;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountSearchPage {
    // search box
    @FindBy(id = "form:username")
    public WebElement username;
    @FindBy(id = "form:password")
    public WebElement password;
    @FindBy(id = "form:email")
    public WebElement email;
    @FindBy(name = "form:search")
    public WebElement searchButton;

    // sum
    @FindBy(id = "searchResultsRegion")
    public WebElement searchResultsRegion;

    // paginator
    @FindBy(css = "span.ui-paginator-current")
    public WebElement paginatorText;
    @FindBy(css = "span.ui-icon-seek-next")
    public WebElement paginatorNextButton;
    @FindBy(css = "span.ui-icon-seek-prev")
    public WebElement paginatorPrevButton;

    WebDriver driver;

    public AccountSearchPage(WebDriver driver) {
        this.driver = driver;
    }

    public void searchByUsername(String _username) {
        clear(email, password, username);
        write(username, _username);
        click(searchButton);
    }

    public void clickEditAccount(String userName) {
        click(driver.findElement(cssSelector("button[title=\"Edit " + userName + "\"]")));
    }

    public void searchByEmail(String _email) {
        clear(password, username);
        write(email, _email);
        click(searchButton);
    }
}