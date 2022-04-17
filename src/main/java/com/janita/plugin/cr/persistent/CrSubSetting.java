package com.janita.plugin.cr.persistent;

import com.intellij.openapi.options.Configurable;
import com.janita.plugin.cr.domain.CrDataStorageWay;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * SubSetting
 *
 * @author zhucj
 * @since 20220324
 */
public class CrSubSetting implements Configurable {

    protected CrDataStorageWay storageWay;

    private CrDataStorageWayComponentHolder holder;

    public CrSubSetting() {
        init();
    }

    private void init() {
        CrDataStorageWay storageWay = CrDataStorageWayPersistent.getInstance().getState();
        if (storageWay == null) {
            storageWay = new CrDataStorageWay();
        }
        this.storageWay = storageWay;
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "CRHelper Settings";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        CrDataStorageWayComponentHolder holder = CrDataStorageWayComponentHolder.createCrDataStorageWayPanel(storageWay);
        this.holder = holder;
        return holder.getTotalPanel();
    }

    /**
     * 设置apply按钮是否可用，数据修改时被调用
     */
    @Override
    public boolean isModified() {
        return storageWay.hashCode() != holder.getCrDataStorageWay().hashCode();
    }

    /**
     * 点击apply按钮后被调用
     */
    @Override
    public void apply() {
        CrDataStorageWay way = holder.getCrDataStorageWay();
        CrDataStorageWayPersistent.getInstance().loadState(way);
    }

    /**
     * reset按钮被点击时触发
     */
    @Override
    public void reset() {
        init();
    }
}