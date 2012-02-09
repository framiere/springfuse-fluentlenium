package integ.com.yourcompany.yourproject.pages;

import integ.com.yourcompany.yourproject.support.Page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Page
public class LoggedHomePage extends HomePage {
    @FindBy(partialLinkText = "Logout")
    public WebElement logoutLink;
}
