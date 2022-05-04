package com.epam.esm.persistence.repository.sort;

public enum SortColumn {

    NAME("name"), DATE("create_date");

    private final String sortTable;

    SortColumn(String sortTable) {
        this.sortTable = sortTable;
    }

    public String getSortTable() {
        return sortTable;
    }

}
