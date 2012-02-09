package integ.com.yourcompany.yourproject.pages;

import integ.com.yourcompany.yourproject.support.Page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Page
public class AnonymousHomePage extends HomePage {
    @FindBy(partialLinkText = "Connexion")
    public WebElement connexionLink;
}