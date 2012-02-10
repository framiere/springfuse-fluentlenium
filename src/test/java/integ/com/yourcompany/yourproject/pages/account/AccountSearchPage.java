package integ.com.yourcompany.yourproject.pages.account;

import static org.openqa.selenium.By.cssSelector;
import integ.com.yourcompany.yourproject.support.Client;
import integ.com.yourcompany.yourproject.support.Page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Page
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

    Client client;

    public void searchByUsername(String _username) {
        client.clear(email, password, username);
        client.fill(username, _username);
        client.click(searchButton);
    }

    public void searchByEmail(String _email) {
        client.clear(password, username);
        client.fill(email, _email);
        client.click(searchButton);
    }

    public void clickEditAccount(String userName) {
        client.click(cssSelector("button[title=\"Edit " + userName + "\"]"));
    }
}