package integ.com.yourcompany.yourproject.pages.role;

import static org.openqa.selenium.By.cssSelector;
import integ.com.yourcompany.yourproject.support.Client;
import integ.com.yourcompany.yourproject.support.Page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Page
public class RoleSearchPage {
    // search box
    @FindBy(id = "form:roleName")
    public WebElement roleName;
    @FindBy(name = "form:search")
    public WebElement searchButton;

    Client client;

    public void searchByRolename(String _roleName) {
        client.fill(roleName, _roleName);
        client.click(searchButton);
    }

    public void clickSelectRole(String roleName) {
        client.click(cssSelector("button[title=\"Select " + roleName + "\"]"));
    }
}