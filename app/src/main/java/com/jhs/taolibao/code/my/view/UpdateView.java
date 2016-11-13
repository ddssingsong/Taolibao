package com.jhs.taolibao.code.my.view;

import com.jhs.taolibao.entity.VersionInfo;

/**
 * Created by dds on 2016/7/18.
 *
 * @TODO
 */
public interface UpdateView {
    void needUpdate(VersionInfo versionInfo);

    void dontneedUpdate();


}
