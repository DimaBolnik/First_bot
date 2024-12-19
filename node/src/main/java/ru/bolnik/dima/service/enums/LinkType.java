package ru.bolnik.dima.service.enums;

public enum LinkType {
    GET_DOC("file/get-doc"),
    GET_PHOTO("file/get-photo");
    private final String link;


    LinkType(String value) {
        this.link = value;
    }

    @Override
    public String toString() {
        return link;
    }
}
