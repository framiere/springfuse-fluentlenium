package integ.com.yourcompany.yourproject.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoggedHomePage extends HomePage {
    @FindBy(partialLinkText = "Logout")
    public WebElement logoutLink;
}
