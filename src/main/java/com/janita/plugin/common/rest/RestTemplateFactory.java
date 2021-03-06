package com.janita.plugin.common.rest;

import org.springframework.web.client.RestTemplate;

/**
 * RestTemplateFactory
 *
 * @author zhucj
 * @since 20220324
 */
public class RestTemplateFactory {

    public static RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return restTemplate;
    }
}