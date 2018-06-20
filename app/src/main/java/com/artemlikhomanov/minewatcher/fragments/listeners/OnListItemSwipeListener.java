package com.artemlikhomanov.minewatcher.fragments.listeners;

import android.support.v7.widget.RecyclerView;

public interface OnListItemSwipeListener {
    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
}
