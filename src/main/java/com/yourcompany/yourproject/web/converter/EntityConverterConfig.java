/*
 * (c) Copyright 2005-2011 JAXIO, www.jaxio.com
 * Source code generated by Celerio, a Jaxio product
 * Want to use Celerio within your company? email us at info@jaxio.com
 * Follow us on twitter: @springfuse
 * Template pack-jsf2-primefaces:src/main/java/EntityConverterConfig.p.vm.java
 */
package com.yourcompany.yourproject.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.yourcompany.yourproject.domain.Account;
import com.yourcompany.yourproject.domain.Address;
import com.yourcompany.yourproject.domain.Book;
import com.yourcompany.yourproject.domain.ContactInfo;
import com.yourcompany.yourproject.domain.Document;
import com.yourcompany.yourproject.domain.Role;
import com.yourcompany.yourproject.domain.more.MoreTypesDemo;
import com.yourcompany.yourproject.service.AccountService;
import com.yourcompany.yourproject.service.AddressService;
import com.yourcompany.yourproject.service.BookService;
import com.yourcompany.yourproject.service.ContactInfoService;
import com.yourcompany.yourproject.service.DocumentService;
import com.yourcompany.yourproject.service.RoleService;
import com.yourcompany.yourproject.service.more.MoreTypesDemoService;

/**
 * Responsible for creating Entity JSF converters.
 * Each converter provides a 'print' method to convert an entity instance to a friendly string representation (readable by humans...).
 */
@Configuration
public class EntityConverterConfig {

    // -- AccountConverter

    @Autowired
    AccountService accountService;

    @Bean
    public AccountConverter accountConverter() {
        return new AccountConverter(accountService);
    }

    public class AccountConverter extends GenericConverter<Account, String> {
        public AccountConverter(AccountService accountService) {
            super(accountService, "username");
        }
    }

    // -- AddressConverter

    @Autowired
    AddressService addressService;

    @Bean
    public AddressConverter addressConverter() {
        return new AddressConverter(addressService);
    }

    public class AddressConverter extends GenericConverter<Address, Integer> {
        public AddressConverter(AddressService addressService) {
            super(addressService, "streetName + '/' + city");
        }
    }

    // -- BookConverter

    @Autowired
    BookService bookService;

    @Bean
    public BookConverter bookConverter() {
        return new BookConverter(bookService);
    }

    public class BookConverter extends GenericConverter<Book, Integer> {
        public BookConverter(BookService bookService) {
            super(bookService, "title");
        }
    }

    // -- ContactInfoConverter

    @Autowired
    ContactInfoService contactInfoService;

    @Bean
    public ContactInfoConverter contactInfoConverter() {
        return new ContactInfoConverter(contactInfoService);
    }

    public class ContactInfoConverter extends GenericConverter<ContactInfo, Integer> {
        public ContactInfoConverter(ContactInfoService contactInfoService) {
            super(contactInfoService, "civility + '/' + lastName + '/' + firstName");
        }
    }

    // -- DocumentConverter

    @Autowired
    DocumentService documentService;

    @Bean
    public DocumentConverter documentConverter() {
        return new DocumentConverter(documentService);
    }

    public class DocumentConverter extends GenericConverter<Document, String> {
        public DocumentConverter(DocumentService documentService) {
            super(documentService, "documentContentType + '/' + documentFileName");
        }
    }

    // -- MoreTypesDemoConverter

    @Autowired
    MoreTypesDemoService moreTypesDemoService;

    @Bean
    public MoreTypesDemoConverter moreTypesDemoConverter() {
        return new MoreTypesDemoConverter(moreTypesDemoService);
    }

    public class MoreTypesDemoConverter extends GenericConverter<MoreTypesDemo, Integer> {
        public MoreTypesDemoConverter(MoreTypesDemoService moreTypesDemoService) {
            super(moreTypesDemoService, "numberInt + '/' + numberLong + '/' + numberDouble");
        }
    }

    // -- RoleConverter

    @Autowired
    RoleService roleService;

    @Bean
    public RoleConverter roleConverter() {
        return new RoleConverter(roleService);
    }

    public class RoleConverter extends GenericConverter<Role, Integer> {
        public RoleConverter(RoleService roleService) {
            super(roleService, "roleName");
        }
    }
}