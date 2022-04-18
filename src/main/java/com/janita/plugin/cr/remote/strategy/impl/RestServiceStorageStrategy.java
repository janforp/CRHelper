package com.janita.plugin.cr.remote.strategy.impl;

import com.janita.plugin.common.domain.Pair;
import com.janita.plugin.common.enums.CrRestApiEnum;
import com.janita.plugin.common.rest.RestTemplateFactory;
import com.janita.plugin.cr.domain.CrProjectInfo;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.domain.CrQuestionQueryRequest;
import com.janita.plugin.cr.remote.strategy.CrQuestionStorageStrategy;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * RestServiceStorage
 *
 * @author zhucj
 * @since 20220324
 */
public class RestServiceStorageStrategy implements CrQuestionStorageStrategy {

    private static final RestTemplate restTemplate = RestTemplateFactory.getRestTemplate();

    @Override
    public List<CrProjectInfo> queryProject(String gitUserName) {
        return null;
    }

    @Override
    public boolean add(CrQuestion question) {
        String fullUrl = CrRestApiEnum.ADD.getFullUrl();
        return false;
    }

    @Override
    public boolean update(CrQuestion question) {
        String fullUrl = CrRestApiEnum.UPDATE.getFullUrl();
        return false;
    }

    @Override
    public Pair<Boolean, List<CrQuestion>> query(CrQuestionQueryRequest request) {
        String fullUrl = CrRestApiEnum.QUERY.getFullUrl();
        return Pair.of(false, null);
    }
}
