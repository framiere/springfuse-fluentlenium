package integ.com.yourcompany.yourproject.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage {
    @FindBy(partialLinkText = "Account Flow")
    public WebElement accountLink;
}