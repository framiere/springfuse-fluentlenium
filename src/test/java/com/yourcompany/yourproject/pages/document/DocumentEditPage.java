package com.yourcompany.yourproject.pages.document;

import static com.google.common.base.Preconditions.checkArgument;


import java.io.File;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.yourcompany.yourproject.support.Page;

@Page
public class DocumentEditPage {
    // edit box
    @FindBy(id = "form:documentBinary_input")
    public WebElement input;

    // buttons
    @FindBy(id = "form:save")
    public WebElement save;

    public void send(String file) {
        checkArgument(new File(file).exists(), " must exist");
        send(new File(file));
    }

    public void send(File file) {
        input.sendKeys(file.getAbsolutePath());
    }
}