package com.app.musicapp.ui.nowplaying;

import android.graphics.*;
import android.graphics.drawable.Drawable;

public class ColorCircleDrawable extends Drawable {
    private final Paint mPaint;
    private int mRadius = 0;

    public ColorCircleDrawable(final int color) {
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaint.setColor(color);
    }

    public void setColor(int color) {
        this.mPaint.setColor(color);
        this.invalidateSelf();
    }

    @Override
    public void draw(final Canvas canvas) {
        final Rect bounds = getBounds();
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), mRadius, mPaint);
    }

    @Override
    protected void onBoundsChange(final Rect bounds) {
        super.onBoundsChange(bounds);
        mRadius = (int) (Math.min(bounds.width(), bounds.height()) / 2f * 0.85f);
    }

    @Override
    public void setAlpha(final int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(final ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}