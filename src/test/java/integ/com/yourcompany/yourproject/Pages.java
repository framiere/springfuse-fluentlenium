package integ.com.yourcompany.yourproject;

import static org.openqa.selenium.By.cssSelector;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Pages {
    public static class LoginPage {
        @FindBy(id = "j_username")
        WebElement user;
        @FindBy(id = "j_password")
        WebElement password;
        @FindBy(name = "submit")
        WebElement submit;

        public void login(String _user, String _password) {
            user.clear();
            user.sendKeys(_user);
            password.clear();
            password.sendKeys(_password);
            submit.click();
        }
    }

    public static class HomePage {
        @FindBy(partialLinkText = "Account Flow")
        WebElement accountLink;
    }

    public static class AnonymousHomePage extends HomePage {
        @FindBy(partialLinkText = "Connexion")
        WebElement connexionLink;
    }

    public static class LoggedHomePage extends HomePage {
        @FindBy(partialLinkText = "Logout")
        WebElement logoutLink;
    }

    public static class AccountSearchPage {
        // search box
        @FindBy(id = "form:username")
        WebElement username;
        @FindBy(id = "form:password")
        WebElement password;
        @FindBy(id = "form:email")
        WebElement email;
        @FindBy(name = "form:search")
        WebElement searchButton;

        // sum
        @FindBy(id = "searchResultsRegion")
        WebElement searchResultsRegion;

        // paginator
        @FindBy(css = "span.ui-paginator-current")
        WebElement paginatorText;
        @FindBy(css = "span.ui-icon-seek-next")
        WebElement paginatorNextButton;
        @FindBy(css = "span.ui-icon-seek-prev")
        WebElement paginatorPrevButton;

        WebDriver driver;

        public AccountSearchPage(WebDriver driver) {
            this.driver = driver;
        }

        public void searchByUsername(String _username) {
            email.clear();
            password.clear();
            username.sendKeys(_username);
            searchButton.click();
        }

        public void clickAccount(String userName) {
            driver.findElement(cssSelector("button[title=\"Edit " + userName + "\"]")).click();
        }

        public void searchByEmail(String _email) {
            email.clear();
            password.clear();
            email.sendKeys(_email);
            searchButton.click();
        }
    }

    public static class RoleSearchPage {
        // search box
        @FindBy(id = "form:roleName")
        WebElement roleName;
        @FindBy(name = "form:search")
        WebElement searchButton;

        WebDriver driver;

        public RoleSearchPage(WebDriver driver) {
            this.driver = driver;
        }

        public void searchByRolename(String _roleName) {
            roleName.clear();
            roleName.sendKeys(_roleName);
            searchButton.click();
        }

        public void selectRole(String roleName) {
            driver.findElement(cssSelector("button[title=\"Select " + roleName + "\"]")).click();
        }
    }

    public static class AccountEditPage {
        @FindBy(id = "form:messages")
        WebElement messages;

        // edit box
        @FindBy(id = "form:username")
        WebElement username;
        @FindBy(id = "form:password")
        WebElement password;
        @FindBy(id = "form:email")
        WebElement email;

        // buttons
        @FindBy(name = "form:send")
        WebElement submit;
        @FindBy(name = "form:save")
        WebElement save;
        @FindBy(name = "form:close")
        WebElement close;

        // tabs
        @FindBy(linkText = "Roles")
        WebElement tabRoles;

        public void update(String _username, String _password, String _email) {
            username.clear();
            username.sendKeys(_username);
            password.clear();
            password.sendKeys(_password);
            email.clear();
            email.sendKeys(_email);
        }
    }

    public static class AccountRoleTab {
        @FindBy(css = "button[title=\"Search role\"]")
        WebElement select;
        
        WebDriver driver;

        public AccountRoleTab(WebDriver driver) {
            this.driver = driver;
        }

        public void deleteRole(String roleName) {
            driver.findElement(cssSelector("button[title=\"Delete " + roleName + "\"]")).click();
        }
    }

}
