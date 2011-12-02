package integ.com.yourcompany.yourproject.pages;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AnonymousHomePage extends HomePage {
    @FindBy(partialLinkText = "Connexion")
    public WebElement connexionLink;
}