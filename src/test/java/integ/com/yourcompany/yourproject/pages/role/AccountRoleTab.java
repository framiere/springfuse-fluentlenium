package integ.com.yourcompany.yourproject.pages.role;

import static org.openqa.selenium.By.cssSelector;
import integ.com.yourcompany.yourproject.support.Client;
import integ.com.yourcompany.yourproject.support.Page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Page
public class AccountRoleTab {
    @FindBy(css = "button[title=\"Search role\"]")
    public WebElement selectButton;

    Client client;

    public void clickDeleteRole(String roleName) {
        client.click(cssSelector("button[title=\"Delete " + roleName + "\"]"));
    }
}