package com.janita.plugin.cr.component;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.ProjectManager;
import com.janita.plugin.common.constant.PersistentKeys;
import com.janita.plugin.common.util.JsonUtils;
import com.janita.plugin.cr.domain.CrProjectInfo;
import com.janita.plugin.cr.persistent.CrDataStoragePersistent;
import com.janita.plugin.cr.remote.strategy.CrQuestionStorageFactory;
import com.janita.plugin.cr.remote.strategy.CrQuestionStorageStrategy;

import java.util.List;

/**
 * CrProjectApplication
 *
 * @author zhucj
 * @since 20220324
 */
@SuppressWarnings("all")
public class CrQueryProjectInfo implements ProjectComponent {

    @Override
    public void projectOpened() {
        CrQuestionStorageStrategy strategy = CrQuestionStorageFactory.getCrQuestionStorage(CrDataStoragePersistent.getPersistentData().getStorageWay());
        ProjectManager instance = ProjectManager.getInstance();
        // TODO 获取 gitUserName
        List<CrProjectInfo> projectInfoList = strategy.queryProject(null);
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();
        propertiesComponent.setValue(PersistentKeys.PROJECT_INFO_CACHE_KEY, JsonUtils.toJson(projectInfoList));
    }

}
