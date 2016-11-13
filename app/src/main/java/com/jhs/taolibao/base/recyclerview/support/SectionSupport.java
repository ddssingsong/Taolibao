package com.jhs.taolibao.base.recyclerview.support;

/**
 * Created by dds on 16/4/9.
 */
public interface SectionSupport<T>
{
    public int sectionHeaderLayoutId();

    public int sectionTitleTextViewId();

    public String getTitle(T t);
}
