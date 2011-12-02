package integ.com.yourcompany.yourproject.support;

import static integ.com.yourcompany.yourproject.support.Sleeps.sleep;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

public class Waiter {
    private final WebDriver driver;

    public Waiter(WebDriver driver) {
        this.driver = driver;
    }

    public void text(String text) {
        function(contains(text));
    }

    public void text(WebElement webElement, String text) {
        function(new TextEquals(webElement, text));
    }

    public void difference(WebElement webElement, String text) {
        function(new TextNotEquals(webElement, text));
    }

    public void function(Function<WebDriver, Boolean> function) {
        sleep();
        browserWait().until(function);
    }

    public WebDriverWait browserWait() {
        return new WebDriverWait(driver, 4);
    }

    public static ExpectedCondition<Boolean> contains(final String text) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver from) {
                return from.getPageSource().contains(text);
            }
        };
    }
}