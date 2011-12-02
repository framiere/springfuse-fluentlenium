package integ.com.yourcompany.yourproject.pages.document;

import static com.google.common.base.Preconditions.checkArgument;
import static integ.com.yourcompany.yourproject.support.Sleeps.sleep;

import java.io.File;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
        sleep();
    }
}