package com.app.musicapp.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.app.musicapp.util.Tool;

public class GetDpView extends View {
    private static final String TAG = "GettingDpView";

    public GetDpView(Context context) {
        super(context);
    }

    public GetDpView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GetDpView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float oneDp = getWidth() / 100.0f;
        Tool.setOneDps(getWidth() / 100.0f);
    }
}
