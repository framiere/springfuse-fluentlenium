package com.yourcompany.yourproject.pages.document;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.yourcompany.yourproject.support.Page;

@Page
public class DocumentSearchPage {
    // search box
    @FindBy(id = "form:search")
    public WebElement searchButton;

    @FindBy(id = "form:sendNew")
    public WebElement createNew;
}