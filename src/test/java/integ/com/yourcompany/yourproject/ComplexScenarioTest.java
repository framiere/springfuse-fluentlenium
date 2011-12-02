package integ.com.yourcompany.yourproject;

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
import integ.com.yourcompany.yourproject.support.Client;

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

    Client client;

    @Before
    public void setup() {
        driver = new org.openqa.selenium.firefox.FirefoxDriver();
        // driver = new org.openqa.selenium.htmlunit.HtmlUnitDriver(true);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        client = new Client(driver);
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
        client.click(anonymousHomePage.connexionLink);
        loginPage.login("admin", "admin");
        driver.get("http://localhost:8080/ippevent/app/document");
        client.click(documentSearchPage.createNew);
        documentEditPage.input.sendKeys("/Users/florent/Downloads/pom.xml");
        client.text("Invalid file type");
        documentEditPage.input.sendKeys("/Users/florent/Downloads/input.txt");
        client.click(documentEditPage.save);
    }

    @Test
    public void as_an_admin_I_update_a_user() throws InterruptedException {
        driver.get("http://localhost:8080/ippevent/app/home?locale=en");
        String userName = "user19";

        client.step("login as cnorris, and verify it is not valid");
        client.click(anonymousHomePage.connexionLink);
        loginPage.login("cnorris", "kickass");
        client.text("Invalid login or password");

        client.step("login as admin");
        loginPage.login("admin", "admin");
        client.click(loggedHomePage.accountLink);
        assertThat(accountSearchPage.searchResultsRegion.getText()).isEmpty();

        client.step("search by mail and verify ajax, next/previous navigation");
        accountSearchPage.searchByEmail("1");
        client.difference(accountSearchPage.searchResultsRegion, "");
        client.text(accountSearchPage.paginatorText, "1 / 2");
        assertThat(accountSearchPage.searchResultsRegion.getText()).isEqualTo("13 results");
        client.click(accountSearchPage.paginatorNextButton);
        client.text(accountSearchPage.paginatorText, "2 / 2");
        client.click(accountSearchPage.paginatorPrevButton);
        client.text(accountSearchPage.paginatorText, "1 / 2");

        client.step("search by username, select the user, and update its value");
        accountSearchPage.searchByUsername(userName);
        client.text(userName + "@example.com");
        accountSearchPage.clickEditAccount(userName);
        client.text("Username (required)");
        accountEditPage.update("cnorris", "kickass", "gmail@chucknorris.com");
        client.click(accountEditPage.submit);
        client.text("Submitted data received, validated and binded, but not saved in database");

        client.step("add a ROLE_ADMIN to the selected user");
        client.click(accountEditPage.rolesTab);
        client.click(accountRoleTab.selectButton);
        roleSearchPage.searchByRolename("ADMIN");
        roleSearchPage.clickSelectRole("ROLE_ADMIN");
        client.text("Roles: Selected existing and added it, but not saved in database");

        client.step("add document");
        client.click(accountEditPage.documentsTab);
        client.click(documentTab.addButton);
        documentEditPage.send("README");
        client.warning("Error is expected");
        client.text("Invalid file type.");
        documentEditPage.send("src/test/resources/for_upload.txt");
        client.click(documentTab.submit);

        client.step("save to database");
        client.click(accountEditPage.save);
        client.text("Saved OK in database");

        client.step("logout");
        client.click(loggedHomePage.logoutLink);

        client.step("let's try to log as cnorris as set previously");
        client.click(anonymousHomePage.connexionLink);
        loginPage.login("cnorris", "kickass");
        client.click(loggedHomePage.accountLink);
        client.click(loggedHomePage.logoutLink);

        client.step("now log back as admin and search for chuck norris");
        client.click(anonymousHomePage.connexionLink);
        loginPage.login("admin", "admin");
        client.click(loggedHomePage.accountLink);
        accountSearchPage.searchByUsername("cnorris");
        client.text("gmail@chucknorris.com");

        client.step("select account and revert previous changes");
        accountSearchPage.clickEditAccount("cnorris");
        client.text("Username (required)");
        accountEditPage.update(userName, userName, userName + "@example.com");
        client.click(accountEditPage.booksTab);
        client.click(accountEditPage.documentsTab);
        client.click(accountEditPage.rolesTab);
        accountRoleTab.clickDeleteRole("ROLE_ADMIN");
        client.click(accountEditPage.save);

        client.text("Saved OK in database");
    }

}
