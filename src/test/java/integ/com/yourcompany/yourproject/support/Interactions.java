package integ.com.yourcompany.yourproject.support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Interactions {
    WebDriver driver;

    public static void click(WebElement webElement) {
        webElement.click();
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            //
        }
    }

    public static void clear(WebElement... webElements) {
        for (WebElement webElement : webElements) {
            webElement.clear();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                //
            }
        }
    }

    public static void write(WebElement webElement, String text) {
        webElement.clear();
        webElement.sendKeys(text);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            //
        }
    }
}
