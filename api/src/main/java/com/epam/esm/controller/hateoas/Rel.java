package com.epam.esm.controller.hateoas;

public class Rel {

    public static final String SELF_REL = "self";
    public static final String FIRST_REL = "first";
    public static final String PREV_REL = "prev";
    public static final String NEXT_REL = "next";
    public static final String LAST_REL = "last";
    public static final String GET_ALL_REL = "getAll";
    public static final String GET_BY_NAME_REL = "getByName";
    public static final String GET_BY_ID_REL = "getById";
    public static final String EXCLUSIVE_PATCH_REL = "exclusivePatch";
    public static final String DELETE_TAG_REL = "deleteTag";
    public static final String DELETE_TAG_BY_NAME_REL = "deleteTagByName";
    public static final String GET_BY_TAG_NAME_REL = "getByTagName";
    public static final String GET_ALL_SORTED_REL = "getAllSorted";

    private Rel() {
    }
}
