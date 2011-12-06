package integ.com.yourcompany.yourproject.pages.role;

import static org.openqa.selenium.By.cssSelector;
import integ.com.yourcompancripty.yourproject.support.Client;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RoleSearchPage {
    // search box
    @FindBy(id = "form:roleName")
    public WebElement roleName;
    @FindBy(name = "form:search")
    public WebElement searchButton;

    private final Client client;

    public RoleSearchPage(WebDriver driver) {
        client = new Client(driver);
    }

    public void searchByRolename(String _roleName) {
        client.fill(roleName, _roleName);
        client.click(searchButton);
    }

    public void clickSelectRole(String roleName) {
        client.click(cssSelector("button[title=\"Select " + roleName + "\"]"));
    }
}