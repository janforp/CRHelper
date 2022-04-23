package com.janita.plugin.cr.dao;

import com.janita.plugin.common.domain.Pair;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.domain.CrQuestionQueryRequest;

import java.util.List;

/**
 * ICrQuestionDAO
 *
 * @author zhucj
 * @since 20220324
 */
public interface ICrQuestionDAO {

    /**
     * 添加
     *
     * @param question 问题
     * @return 成功/失败
     */
    boolean insert(CrQuestion question);

    /**
     * 修改
     *
     * @param question 问题
     * @return 成功/失败
     */
    boolean update(CrQuestion question);

    /**
     * 删除
     *
     * @param questionIdList 问题id列表
     * @return 成功/失败
     */
    boolean batchDelete(List<Integer> questionIdList);

    /**
     * 查询
     *
     * @param request 参数
     * @return 成功/失败
     */
    Pair<Boolean, List<CrQuestion>> query(CrQuestionQueryRequest request);
}