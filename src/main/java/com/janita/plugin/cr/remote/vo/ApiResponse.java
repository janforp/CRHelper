package com.janita.plugin.cr.remote.vo;

/**
 * ApiResponse
 *
 * @author zhucj
 * @since 20220324
 */
public class ApiResponse<T> {

    private Head head;

    private T body;

    public ApiResponse() {
    }

    public ApiResponse(Head head, T body) {
        this.head = head;
        this.body = body;
    }

    public ApiResponse(Head head) {
        this.head = head;
    }

    public Head getHead() {
        return this.head;
    }

    public T getBody() {
        return this.body;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public ApiResponse<T> head(Head head) {
        this.setHead(head);
        return this;
    }
}
