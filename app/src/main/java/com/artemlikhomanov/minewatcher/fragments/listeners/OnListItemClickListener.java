package com.artemlikhomanov.minewatcher.fragments.listeners;

import android.view.View;

public interface OnListItemClickListener {

    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
