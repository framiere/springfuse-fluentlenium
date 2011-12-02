package integ.com.yourcompany.yourproject;

import static com.google.common.base.Preconditions.checkArgument;
import static integ.com.yourcompany.yourproject.AccountSearchPageFactoryTest.clear;
import static integ.com.yourcompany.yourproject.AccountSearchPageFactoryTest.click;
import static integ.com.yourcompany.yourproject.AccountSearchPageFactoryTest.sleep;
import static integ.com.yourcompany.yourproject.AccountSearchPageFactoryTest.write;
import static org.openqa.selenium.By.cssSelector;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.google.common.base.Preconditions;

public class Pages {
    public static class LoginPage {
        @FindBy(id = "j_username")
        WebElement user;
        @FindBy(id = "j_password")
        WebElement password;
        @FindBy(name = "submit")
        WebElement submit;

        public void login(String _user, String _password) {
            write(user, _user);
            write(password, _password);
            click(submit);
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
            clear(email, password, username);
            write(username, _username);
            click(searchButton);
        }

        public void clickAccount(String userName) {
            click(driver.findElement(cssSelector("button[title=\"Edit " + userName + "\"]")));
        }

        public void searchByEmail(String _email) {
            clear(password, username);
            write(email, _email);
            click(searchButton);
        }
    }

    public static class DocumentSearchPage {
        // search box
        @FindBy(id = "form:search")
        WebElement searchButton;

        @FindBy(id = "form:sendNew")
        WebElement createNew;
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
            write(roleName, _roleName);
            click(searchButton);
        }

        public void selectRole(String roleName) {
            click(driver.findElement(cssSelector("button[title=\"Select " + roleName + "\"]")));
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
        @FindBy(linkText = "Documents")
        WebElement tabDocuments;

        public void update(String _username, String _password, String _email) {
            write(username, _username);
            write(password, _password);
            write(email, _email);
        }
    }

    public static class DocumentEditPage {
        // edit box
        @FindBy(id = "form:documentBinary_input")
        WebElement input;

        // buttons
        @FindBy(id = "form:save")
        WebElement save;

        public void send(String file) {
            checkArgument(new File(file).exists(), " must exist");
            send(new File(file));
        }

        public void send(File file) {
            input.sendKeys(file.getAbsolutePath());
            sleep();
        }
    }

    public static class AccountRoleTab {
        @FindBy(css = "button[title=\"Search role\"]")
        WebElement selectButton;

        WebDriver driver;

        public AccountRoleTab(WebDriver driver) {
            this.driver = driver;
        }

        public void deleteRole(String roleName) {
            click(driver.findElement(cssSelector("button[title=\"Delete " + roleName + "\"]")));
        }
    }

    public static class DocumentTab {
        @FindBy(css = "button[title=\"Add document\"]")
        WebElement addButton;
        @FindBy(id = "form:ok")
        WebElement submit;
    }
}
