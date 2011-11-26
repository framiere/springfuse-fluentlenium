/*
 * (c) Copyright 2005-2011 JAXIO, www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-jsf2-primefaces:src/main/java/PrimeFacesUtil.p.vm.java
 */
package com.yourcompany.yourproject.web.util;

import org.primefaces.context.RequestContext;
import org.springframework.stereotype.Component;

/**
 * Use this bean from your flow to execute JavaScript on client side...
 */
@Component
public class PrimeFacesUtil {

    public void showAskForSaveDialog(boolean show) {
        RequestContext rc = RequestContext.getCurrentInstance();
        if (show) {
            rc.execute("askForSaveDialog.show(); APP.focusAskForSaveDialog()");
        } else {
            rc.execute("APP.menu.forceClose()");
        }
    }

    public void forceClose() {
        RequestContext.getCurrentInstance().execute("APP.menu.forceClose()");
    }
}
