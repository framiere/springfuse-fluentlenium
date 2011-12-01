package integ.com.yourcompany.yourproject;

import static org.fest.assertions.Assertions.assertThat;
import static org.openqa.selenium.support.PageFactory.initElements;
import integ.com.yourcompany.yourproject.Pages.AccountEditPage;
import integ.com.yourcompany.yourproject.Pages.AccountRoleTab;
import integ.com.yourcompany.yourproject.Pages.AccountSearchPage;
import integ.com.yourcompany.yourproject.Pages.AnonymousHomePage;
import integ.com.yourcompany.yourproject.Pages.LoggedHomePage;
import integ.com.yourcompany.yourproject.Pages.LoginPage;
import integ.com.yourcompany.yourproject.Pages.RoleSearchPage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountSearchPageFactoryTest {
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
        Thread.sleep(3000);
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
