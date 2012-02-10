package com.yourcompany.yourproject.pages;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.yourcompany.yourproject.support.Client;
import com.yourcompany.yourproject.support.Page;

@Page
public class LoginPage {
    @FindBy(id = "j_username")
    public WebElement user;
    @FindBy(id = "j_password")
    public WebElement password;
    @FindBy(name = "submit")
    public WebElement submit;
    Client client;

    public void login(String _user, String _password) {
        client.fill(user, _user);
        client.fill(password, _password);
        client.click(submit);
    }
}