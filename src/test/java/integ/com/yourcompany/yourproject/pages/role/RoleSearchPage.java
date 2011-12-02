package integ.com.yourcompany.yourproject.pages.role;

import static integ.com.yourcompany.yourproject.support.Interactions.click;
import static integ.com.yourcompany.yourproject.support.Interactions.write;
import static org.openqa.selenium.By.cssSelector;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RoleSearchPage {
    // search box
    @FindBy(id = "form:roleName")
    public WebElement roleName;
    @FindBy(name = "form:search")
    public WebElement searchButton;

    WebDriver driver;

    public RoleSearchPage(WebDriver driver) {
        this.driver = driver;
    }

    public void searchByRolename(String _roleName) {
        write(roleName, _roleName);
        click(searchButton);
    }

    public void clickSelectRole(String roleName) {
        click(driver.findElement(cssSelector("button[title=\"Select " + roleName + "\"]")));
    }
}