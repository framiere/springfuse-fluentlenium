package com.yourcompany.yourproject.support;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;

public class Client {
    public final WebDriver webDriver;
    public final String baseUrl;

    private long driverWaitBeforeStopInSeconds = 10;
    private long waitAfterClickInMs = 800;
    private long waitAfterClearInMs = 150;
    private long waitAfterStepInMs = 4000;
    private long waitAfterFillMs = 250;
    private long waitAfterNotificationMs = 600;
    private boolean followVisually;

    public Client(ClientBuilder builder) {
        this.webDriver = builder.webDriver;
        this.baseUrl = builder.baseUrl;
        this.followVisually = builder.followVisually;
        this.driverWaitBeforeStopInSeconds = builder.waitTimeInSeconds;
        initElements(builder.testInstance);
    }

    public void text(String text) {
        try {
            function(contains(text));
            success("Found [" + text + "]");
        } catch (RuntimeException e) {
            error("Could not find [" + text + "]");
        }
    }

    public void text(WebElement webElement, String text) {
        try {
            function(new TextEquals(webElement, text));
            success("Found [" + text + "]");
        } catch (RuntimeException e) {
            error("Could not find [" + text + "]");
        }
    }

    public void difference(WebElement webElement, String text) {
        try {
            function(new TextNotEquals(webElement, text));
            success("Found different text than [" + text + "]");
        } catch (RuntimeException e) {
            error("Could not find a text different to [" + text + "]");
        }
    }

    public void function(Function<WebDriver, Boolean> function) {
        browserWait().until(function);
    }

    public WebDriverWait browserWait() {
        return new WebDriverWait(webDriver, driverWaitBeforeStopInSeconds);
    }

    public static ExpectedCondition<Boolean> contains(final String text) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver from) {
                return from.getPageSource().contains(text);
            }
        };
    }

    public void step(String text) {
        if (followVisually) {
            message(text);
            sleep(waitAfterStepInMs);
        }
    }

    public void message(String text) {
        notification(text, "information");
    }

    public void warning(String text) {
        notification(text, "warning");
    }

    public void error(String text) {
        if (followVisually) {
            notification(text, "error");
            sleep(60);
        }
        throw new RuntimeException(text);
    }

    public void success(String text) {
        notification(text, "succ_bg");
    }

    public void notification(String text, String type) {
        if (!followVisually) {
            return;
        }
        String addHeader = "" //
                + "if (typeof(jquery_notification_added_in_head) == 'undefined') {                \n" //
                + "   var headID = document.getElementsByTagName('head')[0];                           \n" //
                + "                                                                                    \n" //
                + "   var js = document.createElement('script');                                       \n" //
                + "   js.type = 'text/javascript';                                                     \n" //
                + "   js.src = '" + baseUrl + "/resources/jquery_notification/jquery_notification_v.1.js'; \n" //
                + "   headID.appendChild(js);                                                          \n" //
                + "                                                                                    \n" //
                + "   var css  = document.createElement('link');                                       \n" //
                + "   css.type = 'text/css';                                                           \n" //
                + "   css.rel = 'stylesheet';                                                          \n" //
                + "   css.media = 'screen';                                                            \n" //
                + "   css.href = '" + baseUrl + "/resources/jquery_notification/css/jquery_notification.css';\n" //
                + "   headID.appendChild(css);                                                         \n" //
                + "                                                                                    \n" //
                + "   jquery_notification_added_in_head = true;" //
                + "}\n";
        ((JavascriptExecutor) webDriver).executeScript(addHeader);

        int durationInS = "error".equals(type) ? 100 : 6;
        String showNotification = "" //
                + "showNotification({                               \n" //
                + "    type : \"" + type + "\",                     \n" //
                + "    message: '" + text.replace("'", "\\'") + "', \n" //
                + "    autoClose: true,                             \n" //
                + "    duration: " + durationInS + "                \n" //
                + "});                                              \n";
        ((JavascriptExecutor) webDriver).executeScript(showNotification);
        sleep(waitAfterNotificationMs);
    }

    public void sleep(long sleepInMs) {
        try {
            MILLISECONDS.sleep(sleepInMs);
        } catch (InterruptedException ignore) {
        }
    }

    public void click(By by) {
        click(webDriver.findElement(by));
    }

    public void click(WebElement webElement) {
        webElement.click();
        sleep(waitAfterClickInMs);
    }

    public void page(String relative) {
        System.out.println(baseUrl + relative);
        webDriver.get(baseUrl + relative);
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
                if (field.getType().isAnnotationPresent(Page.class)) {
                    field.setAccessible(true);
                    field.set(object, initPage(field));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object initPage(Field field) throws Exception {
        Object page = createPage(field);
        setupClient(page);
        return page;
    }

    private void setupClient(Object page) throws Exception {
        for (Field field : page.getClass().getDeclaredFields()) {
            if (field.getType() == Client.class) {
                field.setAccessible(true);
                field.set(page, this);
            }
        }
    }

    private Object createPage(Field field) throws ClassNotFoundException {
        return PageFactory.initElements(webDriver, Class.forName(field.getType().getName()));
    }

    public void close() {
        webDriver.close();
    }

    public static class ClientBuilder {
        WebDriver webDriver;
        int waitTimeInSeconds = 10;
        Object testInstance;
        String baseUrl;
        boolean followVisually = true;

        public static ClientBuilder newClient() {
            return new ClientBuilder();
        }

        public ClientBuilder waitTimeInSeconds(int waitTimeInSeconds) {
            this.waitTimeInSeconds = waitTimeInSeconds;
            return this;
        }

        public ClientBuilder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public ClientBuilder onTest(Object testInstance) {
            this.testInstance = testInstance;
            return this;
        }

        public ClientBuilder followVisually(boolean followVisually) {
            this.followVisually = followVisually;
            return this;
        }

        public ClientBuilder webDriver(String driver) {
            if ("htmlunit".equalsIgnoreCase(driver)) {
                this.webDriver = new HtmlUnitDriver(true);
            } else if ("firefox".equalsIgnoreCase(driver)) {
                this.webDriver = new FirefoxDriver();
            } else if ("ie".equalsIgnoreCase(driver)) {
                this.webDriver = new InternetExplorerDriver();
            } else if ("chrome".equalsIgnoreCase(driver)) {
                this.webDriver = new ChromeDriver();
            } else {
                throw new IllegalArgumentException(driver + " is not a valid web driver");
            }
            webDriver.manage().timeouts().implicitlyWait(waitTimeInSeconds, TimeUnit.SECONDS);
            return this;
        }

        public Client build() {
            Preconditions.checkNotNull(baseUrl);
            Preconditions.checkNotNull(testInstance);
            Preconditions.checkNotNull(webDriver);
            return new Client(this);
        }
    }
}