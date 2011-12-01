package integ.com.yourcompany.yourproject;

import static org.fest.assertions.Assertions.assertThat;
import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.support.PageFactory.initElements;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountSearchPageFactoryTest {

    private static final int TIME_IN_SECONDS = 4;
    WebDriver driver;
    LoginPage loginPage;
    AnonymousHomePage anonymousHomePage;
    LoggedHomePage loggedHomePage;
    AccountSearchPage accountSearchPage;
    AccountEditPage accountEditPage;
    AccountRoleTab accountRoleTab;
    RoleSearchPage roleSearchPage;

    @Before
    public void setup() {
        driver = new org.openqa.selenium.firefox.FirefoxDriver();
        // driver = new org.openqa.selenium.htmlunit.HtmlUnitDriver(true);
        loginPage = initElements(driver, LoginPage.class);
        accountSearchPage = initElements(driver, AccountSearchPage.class);
        anonymousHomePage = initElements(driver, AnonymousHomePage.class);
        loggedHomePage = initElements(driver, LoggedHomePage.class);
        accountEditPage = initElements(driver, AccountEditPage.class);
        accountRoleTab = initElements(driver, AccountRoleTab.class);
        roleSearchPage = initElements(driver, RoleSearchPage.class);
    }

    @After
    public void tearDown() {
        driver.close();
    }

    @Test
    public void as_an_admin_I_update_a_user() throws InterruptedException {
        driver.get("http://localhost:8080/florent/app/home?locale=en");
        String userName = "user19";


        // login
        anonymousHomePage.connexionLink.click();
        sleep();
        
        loginPage.login("cnorris", "kickass");
        sleep();
        waitForText("Invalid login or password");

        loginPage.login("admin", "admin");
        sleep();

        // click account
        loggedHomePage.accountLink.click();
        sleep();
        assertThat(accountSearchPage.searchResultsRegion.getText()).isEmpty();

        // search by mail
        accountSearchPage.searchByEmail("1");
        sleep();
        browserWait().until(new TextNotEquals(accountSearchPage.searchResultsRegion, ""));
        browserWait().until(new TextEquals(accountSearchPage.paginatorText, "1 / 2"));
        assertThat(accountSearchPage.searchResultsRegion.getText()).isEqualTo("13 results");
        assertThat(accountSearchPage.paginatorText.getText()).isEqualTo("1 / 2");

        // click next page
        accountSearchPage.paginatorNextButton.click();
        sleep();
        waitForText("2 / 2");

        // click previous page
        accountSearchPage.paginatorPrevButton.click();
        sleep();
        waitForText("1 / 2");

        // search by username
        accountSearchPage.searchByUsername(userName);
        sleep();
        waitForText(userName + "@example.com");

        // select account
        accountSearchPage.clickAccount(userName);
        sleep();
        waitForText("Username (required)");

        // update account
        accountEditPage.update("cnorris", "kickass", "gmail@chucknorris.com");
        sleep();

        // submit values
        accountEditPage.submit.click();
        sleep();
        waitForText("Submitted data received, validated and binded, but not saved in database");

        // select role tab
        accountEditPage.tabRoles.click();
        sleep();

        // click search button
        accountRoleTab.select.click();
        sleep();

        // search role ADMIN
        roleSearchPage.searchByRolename("ADMIN");
        sleep();

        // select role admin
        roleSearchPage.selectRole("ROLE_ADMIN");
        sleep();
        waitForText("Roles: Selected existing and added it, but not saved in database");

        // save to database
        accountEditPage.save.click();
        sleep();
        waitForText("Saved OK in database");

        // logout
        loggedHomePage.logoutLink.click();
        sleep();

        // click connexion
        anonymousHomePage.connexionLink.click();
        sleep();

        // try to log as cnorris
        loginPage.login("cnorris", "kickass");
        sleep();
        loggedHomePage.accountLink.click();
        sleep();
        loggedHomePage.logoutLink.click();
        sleep();

        // now log back as admin
        anonymousHomePage.connexionLink.click();
        sleep();
        // login
        loginPage.login("admin", "admin");
        sleep();
        
        loggedHomePage.accountLink.click();
        sleep();
        
        accountSearchPage.searchByUsername("cnorris");
        sleep();
        waitForText("gmail@chucknorris.com");

        // select account
        accountSearchPage.clickAccount("cnorris");
        sleep();
        waitForText("Username (required)");

        // update account
        accountEditPage.update(userName, userName, userName + "@example.com");
        sleep();
        
        // select role tab
        accountEditPage.tabRoles.click();
        sleep();

        accountRoleTab.deleteRole("ROLE_ADMIN");
        sleep();

        // submit values
        accountEditPage.save.click();
        sleep();
        waitForText("Saved OK in database");
    }

    private void waitForText(String text) {
        browserWait().until(contains(text));
    }

    private void sleep() throws InterruptedException {
        Thread.sleep(1000);
    }

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

    private WebDriverWait browserWait() {
        return new WebDriverWait(driver, TIME_IN_SECONDS);
    }

    public static ExpectedCondition<Boolean> contains(final String text) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver from) {
                return from.getPageSource().contains(text);
            }
        };
    }

}
