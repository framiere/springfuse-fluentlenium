package integ.com.yourcompany.yourproject;

import javax.annotation.Nullable;

import org.openqa.selenium.WebDriver;

import com.google.common.base.Function;

public class TextExists implements Function<WebDriver, Boolean> {
    private final String expected;

    public TextExists(String expected) {
        this.expected = expected;
    }

    @Override
    public Boolean apply(@Nullable WebDriver driver) {
        System.out.println("source length = " + driver.getPageSource().length());
        return driver.getPageSource().contains(expected);
    }
}