package com.janita.plugin.cr.remote;

import com.intellij.openapi.ui.MessageType;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.remote.vo.ApiResponse;
import com.janita.plugin.rest.RestTemplateFactory;
import com.janita.plugin.util.CommonUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zhucj
 * @since 20220415
 */
public class QuestionRemote {

    private static final RestTemplate restTemplate = RestTemplateFactory.getRestTemplate();

    private static final AtomicLong id = new AtomicLong(2);

    public static void add(CrQuestion question) {
        ApiResponse<Map> response = restTemplate.getForObject(UrlConstants.TEST, ApiResponse.class);
        System.out.println(response);
        question.setId(id.incrementAndGet());
    }

    public static void update(CrQuestion question) {
        ApiResponse<Map> response = restTemplate.getForObject(UrlConstants.TEST, ApiResponse.class);
        System.out.println(response);
    }

    public static List<CrQuestion> query(CrQuestionQueryRequest request) {
        CommonUtils.showNotification("抱歉，暂时只支持本地，重启idea数据会丢失，请及时导出", MessageType.WARNING);
        ApiResponse<Map> response = restTemplate.getForObject(UrlConstants.TEST, ApiResponse.class);
        System.out.println(response);
        return null;
    }
}