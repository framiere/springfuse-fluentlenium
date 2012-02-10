package com.yourcompany.yourproject.pages;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.yourcompany.yourproject.support.Page;

@Page
public class LoggedHomePage extends HomePage {
    @FindBy(partialLinkText = "Logout")
    public WebElement logoutLink;
}
