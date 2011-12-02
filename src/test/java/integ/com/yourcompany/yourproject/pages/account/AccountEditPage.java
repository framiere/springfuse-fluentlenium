package integ.com.yourcompany.yourproject.pages.account;

import integ.com.yourcompany.yourproject.support.Client;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountEditPage {
    @FindBy(id = "form:messages")
    public WebElement messages;

    // edit box
    @FindBy(id = "form:username")
    public WebElement username;
    @FindBy(id = "form:password")
    public WebElement password;
    @FindBy(id = "form:email")
    public WebElement email;

    // buttons
    @FindBy(name = "form:send")
    public WebElement submit;
    @FindBy(name = "form:save")
    public WebElement save;
    @FindBy(name = "form:close")
    public WebElement close;

    // tabs
    @FindBy(linkText = "Books")
    public WebElement booksTab;
    @FindBy(linkText = "Roles")
    public WebElement rolesTab;
    @FindBy(linkText = "Documents")
    public WebElement documentsTab;


    Client client;

    public AccountEditPage(WebDriver driver) {
        client = new Client(driver);
    }

    public void update(String _username, String _password, String _email) {
        client.write(username, _username);
        client.write(password, _password);
        client.write(email, _email);
    }
}