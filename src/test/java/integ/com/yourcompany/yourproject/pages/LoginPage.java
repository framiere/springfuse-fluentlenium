package integ.com.yourcompany.yourproject.pages;

import integ.com.yourcompany.yourproject.support.Client;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
    @FindBy(id = "j_username")
    public WebElement user;
    @FindBy(id = "j_password")
    public WebElement password;
    @FindBy(name = "submit")
    public WebElement submit;

    Client client;

    public LoginPage(WebDriver driver) {
        client = new Client(driver);
    }

    public void login(String _user, String _password) {
        client.write(user, _user);
        client.write(password, _password);
        client.click(submit);
    }
}