package me.lancer.sevenpounds.ui.view.cardstackview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.view.animation.AccelerateDecelerateInterpolator;

public abstract class AnimatorAdapter {
    static final int ANIMATION_DURATION = 400;

    protected me.lancer.sevenpounds.ui.view.cardstackview.CardStackView mCardStackView;
    protected AnimatorSet mSet;

    public AnimatorAdapter(me.lancer.sevenpounds.ui.view.cardstackview.CardStackView cardStackView) {
        mCardStackView = cardStackView;
    }

    protected void initAnimatorSet() {
        mSet = new AnimatorSet();
        mSet.setInterpolator(new AccelerateDecelerateInterpolator());
        mSet.setDuration(getDuration());
    }

    public void itemClick(final me.lancer.sevenpounds.ui.view.cardstackview.CardStackView.ViewHolder viewHolder, int position) {
        if (mSet != null && mSet.isRunning()) return;
        initAnimatorSet();
        if (mCardStackView.getSelectPosition() == position) {
            onItemCollapse(viewHolder);
        } else {
            onItemExpand(viewHolder, position);
        }
        if (mCardStackView.getChildCount() == 1)
            mSet.end();
    }

    protected abstract void itemExpandAnimatorSet(me.lancer.sevenpounds.ui.view.cardstackview.CardStackView.ViewHolder viewHolder, int position);

    protected abstract void itemCollapseAnimatorSet(me.lancer.sevenpounds.ui.view.cardstackview.CardStackView.ViewHolder viewHolder);

    private void onItemExpand(final me.lancer.sevenpounds.ui.view.cardstackview.CardStackView.ViewHolder viewHolder, int position) {
        final int preSelectPosition = mCardStackView.getSelectPosition();
        final me.lancer.sevenpounds.ui.view.cardstackview.CardStackView.ViewHolder preSelectViewHolder = mCardStackView.getViewHolder(preSelectPosition);
        if (preSelectViewHolder != null) {
            preSelectViewHolder.onItemExpand(false);
        }
        mCardStackView.setSelectPosition(position);
        itemExpandAnimatorSet(viewHolder, position);
        mSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mCardStackView.setScrollEnable(false);
                if (preSelectViewHolder != null) {
                    preSelectViewHolder.onAnimationStateChange(me.lancer.sevenpounds.ui.view.cardstackview.CardStackView.ANIMATION_STATE_START, false);
                }
                viewHolder.onAnimationStateChange(me.lancer.sevenpounds.ui.view.cardstackview.CardStackView.ANIMATION_STATE_START, true);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                viewHolder.onItemExpand(true);
                if (preSelectViewHolder != null) {
                    preSelectViewHolder.onAnimationStateChange(me.lancer.sevenpounds.ui.view.cardstackview.CardStackView.ANIMATION_STATE_END, false);
                }
                viewHolder.onAnimationStateChange(me.lancer.sevenpounds.ui.view.cardstackview.CardStackView.ANIMATION_STATE_END, true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                if (preSelectViewHolder != null) {
                    preSelectViewHolder.onAnimationStateChange(me.lancer.sevenpounds.ui.view.cardstackview.CardStackView.ANIMATION_STATE_CANCEL, false);
                }
                viewHolder.onAnimationStateChange(me.lancer.sevenpounds.ui.view.cardstackview.CardStackView.ANIMATION_STATE_CANCEL, true);
            }
        });
        mSet.start();
    }

    private void onItemCollapse(final me.lancer.sevenpounds.ui.view.cardstackview.CardStackView.ViewHolder viewHolder) {
        itemCollapseAnimatorSet(viewHolder);
        mSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                viewHolder.onItemExpand(false);
                mCardStackView.setScrollEnable(true);
                viewHolder.onAnimationStateChange(me.lancer.sevenpounds.ui.view.cardstackview.CardStackView.ANIMATION_STATE_START, false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mCardStackView.setSelectPosition(me.lancer.sevenpounds.ui.view.cardstackview.CardStackView.DEFAULT_SELECT_POSITION);
                viewHolder.onAnimationStateChange(me.lancer.sevenpounds.ui.view.cardstackview.CardStackView.ANIMATION_STATE_END, false);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                viewHolder.onAnimationStateChange(me.lancer.sevenpounds.ui.view.cardstackview.CardStackView.ANIMATION_STATE_CANCEL, false);
            }
        });
        mSet.start();
    }

    protected int getCollapseStartTop(int collapseShowItemCount) {
        return mCardStackView.getOverlapGapsCollapse()
                * (mCardStackView.getNumBottomShow() - collapseShowItemCount - (mCardStackView.getNumBottomShow() - (mCardStackView.getChildCount() - mCardStackView.getSelectPosition() > mCardStackView.getNumBottomShow()
                ? mCardStackView.getNumBottomShow()
                : mCardStackView.getChildCount() - mCardStackView.getSelectPosition() - 1)));
    }

    public int getDuration() {
        return mCardStackView.getDuration();
    }
}
