/*
 * (c) Copyright 2005-2011 JAXIO, www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-backend:src/test/java/manager/ModelGenerator.e.vm.java
 */
package com.yourcompany.yourproject.service;

import org.springframework.stereotype.Service;

import com.yourcompany.yourproject.domain.Address;

/**
 * Helper class to create transient entities instance for testing purposes.
 * Simple properties are pre-filled with random values.
 */
@Service
public class AddressGenerator {

    /**
     * Returns a new Address instance filled with random values.
     */
    public Address getAddress() {
        Address address = new Address();

        // simple attributes follows
        address.setStreetName("d");
        address.setCity("d");
        return address;
    }

}