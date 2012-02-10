package com.yourcompany.yourproject;

import static com.yourcompany.yourproject.support.Client.ClientBuilder.newClient;
import static org.fest.assertions.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yourcompany.yourproject.pages.AnonymousHomePage;
import com.yourcompany.yourproject.pages.LoggedHomePage;
import com.yourcompany.yourproject.pages.LoginPage;
import com.yourcompany.yourproject.pages.account.AccountEditPage;
import com.yourcompany.yourproject.pages.account.AccountSearchPage;
import com.yourcompany.yourproject.pages.document.DocumentEditPage;
import com.yourcompany.yourproject.pages.document.DocumentSearchPage;
import com.yourcompany.yourproject.pages.document.DocumentTab;
import com.yourcompany.yourproject.pages.role.AccountRoleTab;
import com.yourcompany.yourproject.pages.role.RoleSearchPage;
import com.yourcompany.yourproject.support.Client;

public class ComplexScenarioTest {
    Client client;

    // home
    LoginPage loginPage;
    AnonymousHomePage anonymousHomePage;
    LoggedHomePage loggedHomePage;

    // account
    AccountSearchPage accountSearchPage;
    AccountEditPage accountEditPage;

    // document
    DocumentSearchPage documentSearchPage;
    DocumentEditPage documentEditPage;
    DocumentTab documentTab;

    AccountRoleTab accountRoleTab;
    RoleSearchPage roleSearchPage;

    String context = "http://localhost:8080/springdata";
    boolean useHtmlUnit = false;
    int waitTimeInSeconds = 10;

    @Before
    public void setup() {
        client = newClient() //
                .context(context) //
                .useHtmlUnit(useHtmlUnit) //
                .webDriver("htmlunit") //
                .waitTimeInSeconds(waitTimeInSeconds) //
                .onTest(this) //
                .build();
    }

    @After
    public void tearDown() {
        client.close();
    }

    @Test
    public void as_an_admin_I_update_a_user() throws InterruptedException {
        client.page("/app/home?locale=en");
        String userName = "user19";

        client.step("Login as cnorris, and verify it is not valid");
        client.click(anonymousHomePage.connexionLink);
        loginPage.login("cnorris", "kickass");
        client.text("Invalid login or password");

        client.step("Login as admin");
        loginPage.login("admin", "admin");
        client.click(loggedHomePage.accountLink);
        assertThat(accountSearchPage.searchResultsRegion.getText()).isEmpty();

        client.step("Search by mail and verify ajax, next/previous navigation");
        accountSearchPage.searchByEmail("1");
        client.difference(accountSearchPage.searchResultsRegion, "");
        client.text(accountSearchPage.paginatorText, "1 / 2");
        assertThat(accountSearchPage.searchResultsRegion.getText()).isEqualTo("13 results");
        client.click(accountSearchPage.paginatorNextButton);
        client.text(accountSearchPage.paginatorText, "2 / 2");
        client.click(accountSearchPage.paginatorPrevButton);
        client.text(accountSearchPage.paginatorText, "1 / 2");

        client.step("Search by username, select the user, and update its value");
        accountSearchPage.searchByUsername(userName);
        client.text(userName + "@example.com");
        accountSearchPage.clickEditAccount(userName);
        client.text("Username (required)");
        accountEditPage.update("cnorris", "kickass", "gmail@chucknorris.com");
        client.click(accountEditPage.submit);
        client.text("Submitted data received, validation skipped, binded on model, not saved in database");

        client.step("Add a ROLE_ADMIN to the selected user");
        client.click(accountEditPage.rolesTab);
        client.click(accountRoleTab.selectButton);
        roleSearchPage.searchByRolename("ADMIN");
        roleSearchPage.clickSelectRole("ROLE_ADMIN");
        client.text("Roles: Selected existing and added it, but not saved in database");

        client.step("Add a document");
        client.click(accountEditPage.documentsTab);
        client.click(documentTab.addButton);
        documentEditPage.send("README");
        client.warning("Error is expected");
        client.text("Invalid file type.");
        documentEditPage.send("src/test/resources/for_upload.txt");
        client.click(documentTab.submit);

        client.step("Save to database");
        client.click(accountEditPage.save);
        client.text("Saved OK in database");

        client.step("Logout");
        client.click(loggedHomePage.logoutLink);

        client.step("Let's try to log as cnorris as set previously");
        client.click(anonymousHomePage.connexionLink);
        loginPage.login("cnorris", "kickass");
        client.click(loggedHomePage.accountLink);
        client.click(loggedHomePage.logoutLink);

        client.step("Now log back as admin and search for chuck norris");
        client.click(anonymousHomePage.connexionLink);
        loginPage.login("admin", "admin");
        client.click(loggedHomePage.accountLink);
        accountSearchPage.searchByUsername("cnorris");
        client.text("gmail@chucknorris.com");

        client.step("Select account and revert previous changes");
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
