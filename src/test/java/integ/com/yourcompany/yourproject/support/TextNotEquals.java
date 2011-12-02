package integ.com.yourcompany.yourproject.support;

import javax.annotation.Nullable;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.common.base.Function;

public class TextNotEquals implements Function<WebDriver, Boolean> {
    private final WebElement webElement;
    private final String expected;

    public TextNotEquals(WebElement webElement, String expected) {
        this.webElement = webElement;
        this.expected = expected;
    }

    @Override
    public synchronized Boolean apply(@Nullable WebDriver driver) {
        return !expected.equals(webElement.getText());
    }
}