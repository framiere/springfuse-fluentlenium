package integ.com.yourcompany.yourproject.support;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

public class Client {
    public final WebDriver driver;

    public Client(WebDriver driver) {
        this.driver = driver;
    }

    public void text(String text) {
        message("text expected", text);
        function(contains(text));
    }

    public void text(WebElement webElement, String text) {
        message(webElement.getAttribute("id"), text);
        function(new TextEquals(webElement, text));
    }

    public void difference(WebElement webElement, String text) {
        function(new TextNotEquals(webElement, text));
    }

    public void function(Function<WebDriver, Boolean> function) {
        sleep(100);
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

    public void step(String text) {
        message("------NEW STEP------", text);
        sleep(1500);
    }

    public void message(String title, String text) {
        notification(title, text, "");
    }

    public void message(String text) {
        notification("", text, "");
    }

    public void warning(String title, String text) {
        notification(title, text, "red");
    }

    public void warning(String text) {
        notification("", text, "red");
    }

    public void notification(String title, String text, String color) {
        ((JavascriptExecutor) driver).executeScript("" //
                + "if ($.notifier) {\n" //
                + "    $.notifier.broadcast({ " //
                + "    " + (title.isEmpty() ? "" : "ttl:'" + title.replace("'", "\'") + "',") //
                + "    msg:'" + text.replace("'", "\\'") + "'," //
                + "    skin:'rounded" + (color.isEmpty() ? "" : "," + color).replace("'", "\\'") + "'" //
                + "    });\n" //
                + "};");
        sleep(100);
    }

    public void sleep(long sleepInMs) {
        try {
            Thread.sleep(sleepInMs);
        } catch (InterruptedException e) {
            //
        }
    }

    public void click(By by) {
        click(driver.findElement(by));
    }

    public void click(WebElement webElement) {
        webElement.click();
        sleep(800);
    }

    public void clear(WebElement... webElements) {
        for (WebElement webElement : webElements) {
            webElement.clear();
            sleep(100);
        }
    }

    public void write(WebElement webElement, String text) {
        webElement.clear();
        webElement.sendKeys(text);
        sleep(200);
    }
}