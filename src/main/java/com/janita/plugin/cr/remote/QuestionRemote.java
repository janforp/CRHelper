package com.janita.plugin.cr.remote;

import com.janita.plugin.common.domain.Pair;
import com.janita.plugin.cr.domain.CrDeveloper;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.domain.CrQuestionQueryRequest;
import com.janita.plugin.cr.persistent.CrDataStoragePersistent;
import com.janita.plugin.cr.remote.strategy.CrQuestionStorageFactory;
import com.janita.plugin.cr.remote.strategy.CrQuestionStorageStrategy;
import com.janita.plugin.cr.remote.strategy.impl.SqliteStrategy;
import org.apache.commons.lang3.ObjectUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 这一层主要负责存储数据
 *
 * @author zhucj
 * @since 20220415
 */
public class QuestionRemote {

    public static Set<String> queryDeveloperNameSet(String projectName) {
        CrQuestionStorageStrategy strategy = CrQuestionStorageFactory.getCrQuestionStorage(CrDataStoragePersistent.getPersistentData().getStorageWay());
        Set<CrDeveloper> developerSet = strategy.queryDeveloper(projectName);
        developerSet = ObjectUtils.defaultIfNull(developerSet, new HashSet<>(0));
        return developerSet.stream().map(CrDeveloper::getRealName).collect(Collectors.toSet());
    }

    public static boolean add(CrQuestion question) {
        new SqliteStrategy().add(question);
        CrQuestionStorageStrategy strategy = CrQuestionStorageFactory.getCrQuestionStorage(CrDataStoragePersistent.getPersistentData().getStorageWay());
        return strategy.add(question);
    }

    public static boolean update(CrQuestion question) {
        new SqliteStrategy().update(question);
        CrQuestionStorageStrategy strategy = CrQuestionStorageFactory.getCrQuestionStorage(CrDataStoragePersistent.getPersistentData().getStorageWay());
        return strategy.update(question);
    }

    public static Pair<Boolean, List<CrQuestion>> query(CrQuestionQueryRequest request) {
        new SqliteStrategy().query(request);
        CrQuestionStorageStrategy strategy = CrQuestionStorageFactory.getCrQuestionStorage(CrDataStoragePersistent.getPersistentData().getStorageWay());
        return strategy.query(request);
    }
}