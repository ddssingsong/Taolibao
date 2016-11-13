package com.jhs.taolibao.code.guess.view;

import com.jhs.taolibao.entity.GuessHistory;

import java.util.List;

/**
 * Created by dds on 2016/7/15.
 *
 * @TODO
 */
public interface GuessHistoryView {
    void getHistoryList(List<GuessHistory.DirectoryResultsEntity> list);
    void getHistoryListFaild();
}
