package integ.com.yourcompany.yourproject.support;

public class Sleeps {
    public static final void sleep() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            //
        }
    }
}
