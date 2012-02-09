package integ.com.yourcompany.yourproject.pages.document;

import integ.com.yourcompany.yourproject.support.Page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Page
public class DocumentTab {
    @FindBy(css = "button[title=\"Add document\"]")
    public WebElement addButton;
    @FindBy(id = "form:ok")
    public WebElement submit;
}