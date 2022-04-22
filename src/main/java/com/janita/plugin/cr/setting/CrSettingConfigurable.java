package com.janita.plugin.cr.setting;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * CrSettingConfigurable
 *
 * @author zhucj
 * @since 20220324
 */
public class CrSettingConfigurable implements Configurable {

    private CrQuestionDataStorageSettingComponent component;

    @Override
    public @Nls(capitalization = Nls.Capitalization.Title)
    String getDisplayName() {
        return "CRHelper Setting";
    }

    @Override
    @Nullable
    public JComponent createComponent() {
        CrQuestionDataStorageSettingComponent settingComponent = SettingBuilder.createSettingComponent();
        this.component = settingComponent;
        return settingComponent.getTotalContent();
    }

    @Override
    public boolean isModified() {
        CrQuestionSetting settingFromCache = CrQuestionSetting.getCrQuestionSettingFromCache();
        CrQuestionSetting settingFromInput = CrQuestionSetting.getCrQuestionSettingFromInput(component);
        return !settingFromCache.equals(settingFromInput);
    }

    @Override
    public void apply() {
        CrQuestionSetting.saveFromInput(component);
    }

    @Override
    public void reset() {
        Configurable.super.reset();
    }

    @Override
    public void disposeUIResources() {
        this.component = null;
    }
}