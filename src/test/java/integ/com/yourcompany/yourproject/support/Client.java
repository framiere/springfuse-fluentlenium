package integ.com.yourcompany.yourproject.support;

import java.lang.reflect.Field;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

public class Client {
    public final WebDriver driver;

    private long driverWaitBeforeStopInSeconds = 10;
    private long waitAfterClickInMs = 800;
    private long waitAfterClearInMs = 150;
    private long waitAfterStepInMs = 2000;
    private long waitAfterFillMs = 250;
    private long waitAfterNotificationMs = 600;

    public Client(WebDriver driver) {
        this.driver = driver;
    }

    public void text(String text) {
        message("Expect : " + text);
        function(contains(text));
    }

    public void text(WebElement webElement, String text) {
        message("Expect in attribute : " + text);
        function(new TextEquals(webElement, text));
    }

    public void difference(WebElement webElement, String text) {
        function(new TextNotEquals(webElement, text));
    }

    public void function(Function<WebDriver, Boolean> function) {
        browserWait().until(function);
    }

    public WebDriverWait browserWait() {
        return new WebDriverWait(driver, driverWaitBeforeStopInSeconds);
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
        sleep(waitAfterStepInMs);
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
        String addHeader = "" //
                + "{                                                                                \n" //
                + "var headID = document.getElementsByTagName('head')[0];                           \n" //
                + "                                                                                 \n" //
                + "var js = document.createElement('script');                                       \n" //
                + "js.type = 'text/javascript';                                                     \n" //
                + "js.src = '/springdata/resources/jquery_notification/jquery_notification_v.1.js'; \n" //
                + "headID.appendChild(js);                                                          \n" //
                + "                                                                                 \n" //
                + "var css  = document.createElement('link');                                       \n" //
                + "css.type = 'text/css';                                                           \n" //
                + "css.rel = 'stylesheet';                                                          \n" //
                + "css.media = 'screen';                                                            \n" //
                + "css.href = '/springdata/resources/jquery_notification/css/jquery_notification.css';    \n" //
                + "headID.appendChild(css);                                                         \n" //
                + "}\n";
        System.out.println(addHeader);
        ((JavascriptExecutor) driver).executeScript(addHeader);

        String showNotification = "" //
                + "showNotification({                               \n" //
                + "    type : \"information\",                      \n" //
                + "    message: '" + text.replace("'", "\\'") + "', \n" //
                + "    autoClose: true,                             \n" //
                + "    duration: 4                                  \n" //
                + "});                                              \n";
        ((JavascriptExecutor) driver).executeScript(showNotification);
        sleep(waitAfterNotificationMs);
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
        sleep(waitAfterClickInMs);
    }

    public void clear(WebElement... webElements) {
        for (WebElement webElement : webElements) {
            webElement.clear();
            sleep(waitAfterClearInMs);
        }
    }

    public void fill(WebElement webElement, String text) {
        webElement.clear();
        webElement.sendKeys(text);
        sleep(waitAfterFillMs);
    }

    public void initElements(Object object) {
        try {
            Class<?> cls = Class.forName(object.getClass().getName());
            for (Field field : cls.getDeclaredFields()) {
                if (field.isAnnotationPresent(Page.class)) {
                    field.setAccessible(true);
                    field.set(object, initPage(field));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object initPage(Field field) throws ClassNotFoundException {
        return PageFactory.initElements(driver, Class.forName(field.getType().getName()));
    }
}