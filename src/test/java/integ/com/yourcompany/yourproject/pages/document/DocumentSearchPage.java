package integ.com.yourcompany.yourproject.pages.document;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DocumentSearchPage {
    // search box
    @FindBy(id = "form:search")
    public WebElement searchButton;

    @FindBy(id = "form:sendNew")
    public WebElement createNew;
}