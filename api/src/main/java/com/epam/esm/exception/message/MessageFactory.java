package com.epam.esm.exception.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageFactory {

    private static final String NOT_FOUND_BY_ID = "controllerMessage.notFoundById";
    private static final String NOT_FOUND_PLURAL_BY_ID = "controllerMessage.notFoundPluralById";
    private static final String NOT_FOUND_BY_NAME = "controllerMessage.notFoundByName";
    private static final String NOT_FOUND_BY_TAG = "controllerMessage.notFoundByTag";
    private static final String NOT_FOUND_BY_SEARCH = "controllerMessage.notFoundBySearch";
    private static final String NOT_FOUND_PLURAL = "controllerMessage.notFoundPlural";
    private static final String NOT_FOUND = "controllerMessage.notFound";
    private static final String NOT_ADDED = "controllerMessage.notAdded";
    private static final String NOT_UPDATED_BY_ID = "controllerMessage.notUpdatedById";
    private static final String NOT_DELETED_BY_ID = "controllerMessage.notDeletedById";
    private static final String NOT_DELETED_BY_NAME = "controllerMessage.notDeletedByName";
    private static final String INVALID_DATA = "controllerMessage.invalidData";
    private static final String INVALID_DATA_EXCLUSIVE_PATCH = "controllerMessage.invalidExclusivePatchData";
    private static final String SERVER_ERROR = "controllerMessage.serverError";
    private static final String NOT_ORDERED_BY_ID = "controllerMessage.notOrderedById";
    private static final String USER_EXIST = "controllerMessage.userExist";

    private final MessageSource messageSource;

    @Autowired
    public MessageFactory(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getNotFoundById(Integer id) {
        return String.format(messageSource.getMessage(NOT_FOUND_BY_ID, null, LocaleContextHolder.getLocale()), id);
    }

    public String getNotFoundPluralById(Integer id) {
        return String.format(messageSource.getMessage(NOT_FOUND_PLURAL_BY_ID, null, LocaleContextHolder.getLocale()), id);
    }

    public String getNotFoundByName(String name) {
        return String.format(messageSource.getMessage(NOT_FOUND_BY_NAME, null, LocaleContextHolder.getLocale()), name);
    }

    public String getNotFoundByTag(String tag) {
        return String.format(messageSource.getMessage(NOT_FOUND_BY_TAG, null, LocaleContextHolder.getLocale()), tag);
    }

    public String getNotFoundBySearch(String search) {
        return String.format(messageSource.getMessage(NOT_FOUND_BY_SEARCH, null, LocaleContextHolder.getLocale()), search);
    }

    public String getNotFoundPlural() {
        return messageSource.getMessage(NOT_FOUND_PLURAL, null, LocaleContextHolder.getLocale());
    }

    public String getNotFound() {
        return messageSource.getMessage(NOT_FOUND, null, LocaleContextHolder.getLocale());
    }

    public String getNotAdded() {
        return messageSource.getMessage(NOT_ADDED, null, LocaleContextHolder.getLocale());
    }

    public String getNotUpdatedById(Integer id) {
        return String.format(messageSource.getMessage(NOT_UPDATED_BY_ID, null, LocaleContextHolder.getLocale()), id);
    }

    public String getNotDeletedById(Integer id) {
        return String.format(messageSource.getMessage(NOT_DELETED_BY_ID, null, LocaleContextHolder.getLocale()), id);
    }

    public String getNotDeletedByName(String name) {
        return String.format(messageSource.getMessage(NOT_DELETED_BY_NAME, null, LocaleContextHolder.getLocale()), name);
    }

    public String getInvalidData() {
        return messageSource.getMessage(INVALID_DATA, null, LocaleContextHolder.getLocale());
    }

    public String getInvalidDataExclusivePatch() {
        return messageSource.getMessage(INVALID_DATA_EXCLUSIVE_PATCH, null, LocaleContextHolder.getLocale());
    }

    public String getServerError() {
        return messageSource.getMessage(SERVER_ERROR, null, LocaleContextHolder.getLocale());
    }

    public String getNotOrderedById(Integer id) {
        return String.format(messageSource.getMessage(NOT_ORDERED_BY_ID, null, LocaleContextHolder.getLocale()), id);
    }

    public String getUserExist(String username) {
        return String.format(messageSource.getMessage(USER_EXIST, null, LocaleContextHolder.getLocale()), username);
    }

}

