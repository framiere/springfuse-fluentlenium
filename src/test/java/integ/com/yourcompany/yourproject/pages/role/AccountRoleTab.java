package integ.com.yourcompany.yourproject.pages.role;

import static org.openqa.selenium.By.cssSelector;
import integ.com.yourcompany.yourproject.support.Client;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRoleTab {
    @FindBy(css = "button[title=\"Search role\"]")
    public WebElement selectButton;

    Client client;

    public AccountRoleTab(WebDriver driver) {
        client = new Client(driver);
    }

    public void clickDeleteRole(String roleName) {
        client.click(cssSelector("button[title=\"Delete " + roleName + "\"]"));
    }
}