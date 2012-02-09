package integ.com.yourcompany.yourproject.pages;

import integ.com.yourcompany.yourproject.support.Client;
import integ.com.yourcompany.yourproject.support.Page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Page
public class LoginPage {
    @FindBy(id = "j_username")
    public WebElement user;
    @FindBy(id = "j_password")
    public WebElement password;
    @FindBy(name = "submit")
    public WebElement submit;
    Client client;

    public void login(String _user, String _password) {
        client.fill(user, _user);
        client.fill(password, _password);
        client.click(submit);
    }
}