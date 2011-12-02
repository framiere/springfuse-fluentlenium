package integ.com.yourcompany.yourproject;

import static integ.com.yourcompany.yourproject.support.Interactions.click;
import static org.fest.assertions.Assertions.assertThat;
import static org.openqa.selenium.support.PageFactory.initElements;
import integ.com.yourcompany.yourproject.pages.AnonymousHomePage;
import integ.com.yourcompany.yourproject.pages.LoggedHomePage;
import integ.com.yourcompany.yourproject.pages.LoginPage;
import integ.com.yourcompany.yourproject.pages.account.AccountEditPage;
import integ.com.yourcompany.yourproject.pages.account.AccountSearchPage;
import integ.com.yourcompany.yourproject.pages.document.DocumentEditPage;
import integ.com.yourcompany.yourproject.pages.document.DocumentSearchPage;
import integ.com.yourcompany.yourproject.pages.document.DocumentTab;
import integ.com.yourcompany.yourproject.pages.role.AccountRoleTab;
import integ.com.yourcompany.yourproject.pages.role.RoleSearchPage;
import integ.com.yourcompany.yourproject.support.Waiter;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class ComplexScenarioTest {
    WebDriver driver;
    LoginPage loginPage;
    AnonymousHomePage anonymousHomePage;
    LoggedHomePage loggedHomePage;

    AccountSearchPage accountSearchPage;
    AccountEditPage accountEditPage;

    DocumentSearchPage documentSearchPage;
    DocumentEditPage documentEditPage;
    DocumentTab documentTab;

    AccountRoleTab accountRoleTab;
    RoleSearchPage roleSearchPage;

    Waiter wait;

    @Before
    public void setup() {
        driver = new org.openqa.selenium.firefox.FirefoxDriver();
        // driver = new org.openqa.selenium.htmlunit.HtmlUnitDriver(true);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        wait = new Waiter(driver);
        loginPage = initElements(driver, LoginPage.class);
        anonymousHomePage = initElements(driver, AnonymousHomePage.class);
        loggedHomePage = initElements(driver, LoggedHomePage.class);

        accountSearchPage = initElements(driver, AccountSearchPage.class);

        accountEditPage = initElements(driver, AccountEditPage.class);
        accountRoleTab = initElements(driver, AccountRoleTab.class);

        documentSearchPage = initElements(driver, DocumentSearchPage.class);
        documentEditPage = initElements(driver, DocumentEditPage.class);
        documentTab = initElements(driver, DocumentTab.class);

        roleSearchPage = initElements(driver, RoleSearchPage.class);
    }

    @After
    public void tearDown() {
        driver.close();
    }

    @Test
    @Ignore
    public void upload_file() throws InterruptedException {
        driver.get("http://localhost:8080/ippevent/app/home?locale=en");
        click(anonymousHomePage.connexionLink);
        loginPage.login("admin", "admin");
        driver.get("http://localhost:8080/ippevent/app/document");
        click(documentSearchPage.createNew);
        documentEditPage.input.sendKeys("/Users/florent/Downloads/pom.xml");
        wait.text("Invalid file type");
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
        wait.text("Invalid login or password");

        // login as admin
        loginPage.login("admin", "admin");
        click(loggedHomePage.accountLink);
        assertThat(accountSearchPage.searchResultsRegion.getText()).isEmpty();

        // search by mail and verify ajax, next/previous navigation
        accountSearchPage.searchByEmail("1");
        wait.difference(accountSearchPage.searchResultsRegion, "");
        wait.text(accountSearchPage.paginatorText, "1 / 2");
        assertThat(accountSearchPage.searchResultsRegion.getText()).isEqualTo("13 results");
        click(accountSearchPage.paginatorNextButton);
        wait.text(accountSearchPage.paginatorText, "2 / 2");
        click(accountSearchPage.paginatorPrevButton);
        wait.text(accountSearchPage.paginatorText, "1 / 2");

        // search by username, select the user, and update its value
        accountSearchPage.searchByUsername(userName);
        wait.text(userName + "@example.com");
        accountSearchPage.clickEditAccount(userName);
        wait.text("Username (required)");
        accountEditPage.update("cnorris", "kickass", "gmail@chucknorris.com");
        click(accountEditPage.submit);
        wait.text("Submitted data received, validated and binded, but not saved in database");

        // add a ROLE_ADMIN to the selected user
        click(accountEditPage.rolesTab);
        click(accountRoleTab.selectButton);
        roleSearchPage.searchByRolename("ADMIN");
        roleSearchPage.clickSelectRole("ROLE_ADMIN");
        wait.text("Roles: Selected existing and added it, but not saved in database");

        // add document
        click(accountEditPage.documentsTab);
        click(documentTab.addButton);
        documentEditPage.send("README");
        wait.text("Invalid file type.");
        documentEditPage.send("src/test/resources/for_upload.txt");
        click(documentTab.submit);

        // save to database
        click(accountEditPage.save);
        wait.text("Saved OK in database");

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
        wait.text("gmail@chucknorris.com");

        // select account and revert previous changes
        accountSearchPage.clickEditAccount("cnorris");
        wait.text("Username (required)");
        accountEditPage.update(userName, userName, userName + "@example.com");
        click(accountEditPage.booksTab);
        click(accountEditPage.documentsTab);
        click(accountEditPage.rolesTab);
        accountRoleTab.clickDeleteRole("ROLE_ADMIN");
        click(accountEditPage.save);

        wait.text("Saved OK in database");
    }

}
