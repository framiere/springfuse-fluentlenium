/*
 * (c) Copyright 2005-2011 JAXIO, www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-jsf2-primefaces:src/main/java/flow/FlowsMenuExecutionListener.p.vm.java
 */
package com.yourcompany.yourproject.web.flow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.webflow.definition.StateDefinition;
import org.springframework.webflow.engine.EndState;
import org.springframework.webflow.execution.EnterStateVetoException;
import org.springframework.webflow.execution.FlowExecutionListenerAdapter;
import org.springframework.webflow.execution.RequestContext;

/**
 * Support class for 'active flows' menu.
 */
public class FlowsMenuExecutionListener extends FlowExecutionListenerAdapter {

    @Autowired
    private FlowsMenuHandler flowsMenuHandler;

    /**
     * Remove the ending flow from the 'active flows' menu.
     * Note: it is simpler to do it from this listener than from the flow.
     */
    @Override
    public void stateEntering(RequestContext context, StateDefinition state) throws EnterStateVetoException {
        if (state instanceof EndState) {
            flowsMenuHandler.removeMenu(context);
        }
    }
}