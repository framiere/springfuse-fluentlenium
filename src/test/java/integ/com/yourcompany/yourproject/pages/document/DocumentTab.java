package integ.com.yourcompany.yourproject.pages.document;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DocumentTab {
    @FindBy(css = "button[title=\"Add document\"]")
    public WebElement addButton;
    @FindBy(id = "form:ok")
    public WebElement submit;
}