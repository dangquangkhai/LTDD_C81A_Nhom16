package com.app.musicapp.ui.page;

import android.view.View;

public interface OnClickItemListener<T> {
    void onItemClick(View view, T item, int position);
}