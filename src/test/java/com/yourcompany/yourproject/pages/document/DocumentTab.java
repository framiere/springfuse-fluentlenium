package com.yourcompany.yourproject.pages.document;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.yourcompany.yourproject.support.Page;

@Page
public class DocumentTab {
    @FindBy(css = "button[title=\"Add document\"]")
    public WebElement addButton;
    @FindBy(id = "form:ok")
    public WebElement submit;
}