package integ.com.yourcompany.yourproject.pages.account;

import static integ.com.yourcompany.yourproject.support.Interactions.write;

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

    public void update(String _username, String _password, String _email) {
        write(username, _username);
        write(password, _password);
        write(email, _email);
    }
}