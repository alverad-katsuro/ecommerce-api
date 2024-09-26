package br.com.alverad.ecommerce.commons.dto.generic;

public class GenericBody<T> {

    T data;

    public T getData() {
        return data;
    }
}
