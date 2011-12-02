package integ.com.yourcompany.yourproject;

import static org.fest.assertions.Assertions.assertThat;
import static org.openqa.selenium.support.PageFactory.initElements;
import integ.com.yourcompany.yourproject.Pages.AccountEditPage;
import integ.com.yourcompany.yourproject.Pages.AccountRoleTab;
import integ.com.yourcompany.yourproject.Pages.AccountSearchPage;
import integ.com.yourcompany.yourproject.Pages.AnonymousHomePage;
import integ.com.yourcompany.yourproject.Pages.DocumentEditPage;
import integ.com.yourcompany.yourproject.Pages.DocumentSearchPage;
import integ.com.yourcompany.yourproject.Pages.LoggedHomePage;
import integ.com.yourcompany.yourproject.Pages.LoginPage;
import integ.com.yourcompany.yourproject.Pages.RoleSearchPage;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

public class AccountSearchPageFactoryTest {
    WebDriver driver;
    LoginPage loginPage;
    AnonymousHomePage anonymousHomePage;
    LoggedHomePage loggedHomePage;
    AccountSearchPage accountSearchPage;
    DocumentSearchPage documentSearchPage;
    AccountEditPage accountEditPage;
    DocumentEditPage documentEditPage;
    AccountRoleTab accountRoleTab;
    RoleSearchPage roleSearchPage;

    @Before
    public void setup() {
        driver = new org.openqa.selenium.firefox.FirefoxDriver();
        // driver = new org.openqa.selenium.htmlunit.HtmlUnitDriver(true);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginPage = initElements(driver, LoginPage.class);
        accountSearchPage = initElements(driver, AccountSearchPage.class);
        documentSearchPage = initElements(driver, DocumentSearchPage.class);
        documentEditPage = initElements(driver, DocumentEditPage.class);
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
    public void upload_file() throws InterruptedException {
        driver.get("http://localhost:8080/ippevent/app/home?locale=en");
        click(anonymousHomePage.connexionLink);
        loginPage.login("admin", "admin");
        driver.get("http://localhost:8080/ippevent/app/document");
        click(documentSearchPage.createNew);
        documentEditPage.input.sendKeys("/Users/florent/Downloads/pom.xml");
        waitForText("Invalid file type");
        documentEditPage.input.sendKeys("/Users/florent/Downloads/input.txt");
        click(documentEditPage.save);
    }

    @Test
    public void as_an_admin_I_update_a_user() throws InterruptedException {
        driver.get("http://localhost:8080/ippevent/app/home?locale=en");
        String userName = "user19";

        // login as cnorris, and verify it is not valid
        click(anonymousHomePage.connexionLink);
        loginPage.login("cnorris", "kickass");
        waitForText("Invalid login or password");

        // login as admin
        loginPage.login("admin", "admin");
        click(loggedHomePage.accountLink);
        assertThat(accountSearchPage.searchResultsRegion.getText()).isEmpty();

        // search by mail and verify ajax, next/previous navigation
        accountSearchPage.searchByEmail("1");
        waitForDifferentText(accountSearchPage.searchResultsRegion, "");
        waitForText(accountSearchPage.paginatorText, "1 / 2");
        assertThat(accountSearchPage.searchResultsRegion.getText()).isEqualTo("13 results");
        click(accountSearchPage.paginatorNextButton);
        waitForText("2 / 2");
        click(accountSearchPage.paginatorPrevButton);
        waitForText("1 / 2");

        // search by username, select the user, and update its value
        accountSearchPage.searchByUsername(userName);
        waitForText(userName + "@example.com");
        accountSearchPage.clickAccount(userName);
        waitForText("Username (required)");
        accountEditPage.update("cnorris", "kickass", "gmail@chucknorris.com");
        click(accountEditPage.submit);
        waitForText("Submitted data received, validated and binded, but not saved in database");

        // add a ROLE_ADMIN to the selected user
        click(accountEditPage.tabRoles);
        click(accountRoleTab.select);
        roleSearchPage.searchByRolename("ADMIN");
        roleSearchPage.selectRole("ROLE_ADMIN");
        waitForText("Roles: Selected existing and added it, but not saved in database");

        // save to database
        click(accountEditPage.save);
        waitForText("Saved OK in database");

        // then logout
        click(loggedHomePage.logoutLink);

        // let's try to log as cnorris as set previously
        click(anonymousHomePage.connexionLink);
        loginPage.login("cnorris", "kickass");
        click(loggedHomePage.accountLink);
        click(loggedHomePage.logoutLink);

        // now log back as admin and search for chuck norris
        click(anonymousHomePage.connexionLink);
        loginPage.login("admin", "admin");
        click(loggedHomePage.accountLink);
        accountSearchPage.searchByUsername("cnorris");
        waitForText("gmail@chucknorris.com");

        // select account and revert previous changes
        accountSearchPage.clickAccount("cnorris");
        waitForText("Username (required)");
        accountEditPage.update(userName, userName, userName + "@example.com");
        click(accountEditPage.tabRoles);
        accountRoleTab.deleteRole("ROLE_ADMIN");
        click(accountEditPage.save);

        waitForText("Saved OK in database");
    }

    private void waitForText(String text) {
        waitFor(contains(text));
    }

    public static void click(WebElement webElement) {
        webElement.click();
        sleep();
    }

    public static void clear(WebElement... webElements) {
        for (WebElement webElement : webElements) {
            webElement.clear();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                //
            }
        }
    }

    public static void write(WebElement webElement, String text) {
        webElement.clear();
        webElement.sendKeys(text);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            //
        }
    }

    private void waitForText(WebElement webElement, String text) {
        waitFor(new TextEquals(webElement, text));
    }

    private void waitForDifferentText(WebElement webElement, String text) {
        waitFor(new TextNotEquals(webElement, text));
    }

    private void waitFor(Function<WebDriver, Boolean> function) {
        browserWait().until(function);
    }

    public static final void sleep() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            //
        }
    }

    private WebDriverWait browserWait() {
        return new WebDriverWait(driver, 4);
    }

    public static ExpectedCondition<Boolean> contains(final String text) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver from) {
                return from.getPageSource().contains(text);
            }
        };
    }
}
