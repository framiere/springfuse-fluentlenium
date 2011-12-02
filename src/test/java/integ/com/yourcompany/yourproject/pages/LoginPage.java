package integ.com.yourcompany.yourproject.pages;

import static integ.com.yourcompany.yourproject.support.Interactions.click;
import static integ.com.yourcompany.yourproject.support.Interactions.write;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
    @FindBy(id = "j_username")
    public WebElement user;
    @FindBy(id = "j_password")
    public WebElement password;
    @FindBy(name = "submit")
    public WebElement submit;

    public void login(String _user, String _password) {
        write(user, _user);
        write(password, _password);
        click(submit);
    }
}