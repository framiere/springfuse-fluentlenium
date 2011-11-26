/*
 * (c) Copyright 2005-2011 JAXIO, www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-jsf2-primefaces:src/main/java/component/MessagesHelper.p.vm.java
 */
package com.yourcompany.yourproject.web.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.stereotype.Component;

/**
 * Helper used from the appcc:messages composite component.
 */
@Component
public class MessagesHelper {

    public Message[] getGlobalMessages() {
        List<Message> res = new ArrayList<Message>();
        Iterator<FacesMessage> msgs = FacesContext.getCurrentInstance().getMessages(null);
        while (msgs.hasNext()) {
            res.add(new Message(null, msgs.next()));
        }
        return res.toArray(new Message[res.size()]);
    }

    public Message[] getNonGlobalMessages() {
        List<Message> res = new ArrayList<Message>();

        Iterator<String> ids = FacesContext.getCurrentInstance().getClientIdsWithMessages();
        while (ids.hasNext()) {
            String id = ids.next();
            Iterator<FacesMessage> msgs = FacesContext.getCurrentInstance().getMessages(id);
            while (msgs.hasNext()) {
                res.add(new Message(id, msgs.next()));
            }
        }

        return res.toArray(new Message[res.size()]);
    }

    public boolean hasGlobalMessages() {
        return FacesContext.getCurrentInstance().getMessages(null).hasNext();
    }

    public boolean hasNonGlobalMessages() {
        Iterator<String> i = FacesContext.getCurrentInstance().getClientIdsWithMessages();
        while (i.hasNext()) {
            String v = i.next();
            if (v != null && !v.equals("null")) { /* the 'null' string is pretty disturbing */
                return true;
            }
        }
        return false;
    }

    public int nonGlobalMessagesCount() {
        int count = 0;
        Iterator<String> i = FacesContext.getCurrentInstance().getClientIdsWithMessages();
        while (i.hasNext()) {
            String v = i.next();
            if (v != null && !v.equals("null")) { /* the 'null' string is pretty disturbing */
                count++;
            }
        }
        return count;
    }
}