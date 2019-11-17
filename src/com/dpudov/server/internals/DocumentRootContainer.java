package com.dpudov.server.internals;

public class DocumentRootContainer {
    private String documentRoot;

    public DocumentRootContainer(String documentRoot) {
        this.documentRoot = documentRoot;
    }

    public String getDocumentRoot() {
        return documentRoot;
    }

    public void setDocumentRoot(String documentRoot) {
        this.documentRoot = documentRoot;
    }
}
