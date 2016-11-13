package com.jhs.taolibao.code.guess.presenter;

import com.jhs.taolibao.code.guess.model.StreetModelImpl;
import com.jhs.taolibao.code.guess.view.GuessHistoryView;
import com.jhs.taolibao.entity.GuessHistory;

import java.util.List;

/**
 * Created by dds on 2016/7/15.
 *
 * @TODO
 */
public class GuessHistoryPresenterImpl implements GuessHistoryPresenter {

    private GuessHistoryView guessHistoryView;
    private StreetModelImpl streetModel;

    public GuessHistoryPresenterImpl(GuessHistoryView guessHistoryView) {
        this.guessHistoryView = guessHistoryView;
        streetModel = new StreetModelImpl();
    }

    @Override
    public void getGuessHistoryList(String userId, int page) {
        streetModel.getGuesshistory(userId, page, new StreetModelImpl.onGetGetGuesshistoryListener() {
            @Override
            public void onSuccess(List<GuessHistory.DirectoryResultsEntity> DirectoryResults) {
                guessHistoryView.getHistoryList(DirectoryResults);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                guessHistoryView.getHistoryListFaild();
            }
        });
    }


}
