package ru.narbut.axel.gallery.view.hullScreenPhotoActivity;

import com.hannesdorfmann.mosby3.mvp.viewstate.lce.AbsLceViewState;

import ru.narbut.axel.gallery.model.FullPhotoModel;

public class FullScreenPhotoViewState extends AbsLceViewState<FullPhotoModel, FullPhotoMvpView> {
    private final int STATE_SAVING_NON = 0;
    private final int STATE_SHOW_SAVING = 1;
    private final int STATE_SHOW_FINISH_SAVING = 2;

    private int saveState = STATE_SAVING_NON;

    @Override
    public void apply(FullPhotoMvpView view, boolean retained) {
        super.apply(view, retained);
        if(saveState == STATE_SAVING_NON)
            view.cancelLoadingDialog();
        else if(saveState == STATE_SHOW_SAVING){
            view.showLoadingProgressDialog();
        }else if(saveState == STATE_SHOW_FINISH_SAVING)
            view.showFinishLoadingDialog();
    }

    public void setSavingState(boolean pullToRefresh) {
        saveState = STATE_SHOW_SAVING;
        this.pullToRefresh = pullToRefresh;
    }

    public void setFinishSavingState(boolean pullToRefresh) {
        saveState = STATE_SHOW_FINISH_SAVING;
        this.pullToRefresh = pullToRefresh;
    }

    public void setResetSavingState(boolean pullToRefresh) {
        saveState = STATE_SAVING_NON;
        this.pullToRefresh = pullToRefresh;
    }

}