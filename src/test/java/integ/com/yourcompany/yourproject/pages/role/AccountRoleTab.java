package integ.com.yourcompany.yourproject.pages.role;

import static integ.com.yourcompany.yourproject.support.Interactions.click;
import static org.openqa.selenium.By.cssSelector;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRoleTab {
    @FindBy(css = "button[title=\"Search role\"]")
    public WebElement selectButton;

    WebDriver driver;

    public AccountRoleTab(WebDriver driver) {
        this.driver = driver;
    }

    public void clickDeleteRole(String roleName) {
        click(driver.findElement(cssSelector("button[title=\"Delete " + roleName + "\"]")));
    }
}