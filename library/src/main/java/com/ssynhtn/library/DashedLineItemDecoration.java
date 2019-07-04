package com.ssynhtn.library;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.Gravity;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by huangtongnao on 2019/3/25.
 * Email: huangtongnao@gmail.com
 */
public class DashedLineItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = DashedLineItemDecoration.class.getSimpleName();

    private final boolean isScrollWithRecyclerView;
    private Paint paint;
    private Path path;

    private int scrolledX;
    private int scrolledY;

    private int gravity;
    private float length;
    private float gap;
    private float thickness;
    private final float offset;

    /**
     * @param length                   of each line segment
     * @param gap                      between two line segments
     * @param thickness                of line segments
     * @param offset                   to the recycler view edge|padding
     * @param color                    of the dash line
     * @param isScrollWithRecyclerView if set to true, the dash line would scroll with the recycler view, otherwise the dash line would be fixed in position
     */
    public DashedLineItemDecoration(@NonNull RecyclerView recyclerView,
                                    int gravity,
                                    float length, float gap, float thickness, float offset, int color,
                                    boolean isScrollWithRecyclerView) {
        this.gravity = GravityCompat.getAbsoluteGravity(gravity, recyclerView.getLayoutDirection());
        this.offset = offset;
        this.isScrollWithRecyclerView = isScrollWithRecyclerView;
        this.length = length;
        this.gap = gap;
        this.thickness = thickness;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(thickness);
        paint.setColor(color);
        paint.setPathEffect(new DashPathEffect(new float[]{length, gap}, 0));

        path = new Path();

        if (isScrollWithRecyclerView) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    scrolledX += dx;
                    scrolledY += dy;
                }
            });
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);

        switch (gravity) {
            case Gravity.LEFT:
                drawLeft(c, parent);
                break;
            case Gravity.RIGHT:
                drawRight(c, parent);
                break;
            case Gravity.TOP:
                drawTop(c, parent);
                break;
            case Gravity.BOTTOM:
                drawBottom(c, parent);
                break;
            default:
                Log.w(TAG, "gravity only supports left|start, right|end, top, bottom");
                break;
        }
    }

    private void drawLeft(Canvas c, RecyclerView parent) {
        int restoreCount = c.save();

        float top = isScrollWithRecyclerView ? (-scrolledY) % (length + gap) : 0;
        float x = offset + thickness / 2.0f;
        float bottom = parent.getHeight();
        if (parent.getClipToPadding()) {
            top += parent.getPaddingTop();
            x += parent.getPaddingLeft();
            bottom -= parent.getPaddingBottom();
            c.clipRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getWidth() - parent.getPaddingRight(), parent.getHeight() - parent.getPaddingBottom());
        }

        path.reset();
        path.moveTo(x, top);
        path.lineTo(x, bottom);

        c.drawPath(path, paint);

        c.restoreToCount(restoreCount);
    }

    private void drawRight(Canvas c, RecyclerView parent) {
        int restoreCount = c.save();

        float top = isScrollWithRecyclerView ? (-scrolledY) % (length + gap) : 0;
        float x = parent.getWidth() - offset - thickness / 2.0f;
        float bottom = parent.getHeight();
        if (parent.getClipToPadding()) {
            top += parent.getPaddingTop();
            x -= parent.getPaddingRight();
            bottom -= parent.getPaddingBottom();
            c.clipRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getWidth() - parent.getPaddingRight(), parent.getHeight() - parent.getPaddingBottom());
        }

        path.reset();
        path.moveTo(x, top);
        path.lineTo(x, bottom);

        c.drawPath(path, paint);

        c.restoreToCount(restoreCount);

    }

    private void drawTop(Canvas c, RecyclerView parent) {
        int restoreCount = c.save();

        float left = isScrollWithRecyclerView ? (-scrolledX) % (length + gap) : 0;
        float y = offset + thickness / 2.0f;
        float right = parent.getWidth();
        if (parent.getClipToPadding()) {
            left += parent.getPaddingLeft();
            y += parent.getPaddingTop();
            right -= parent.getPaddingRight();
            c.clipRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getWidth() - parent.getPaddingRight(), parent.getHeight() - parent.getPaddingBottom());
        }

        path.reset();
        path.moveTo(left, y);
        path.lineTo(right, y);

        c.drawPath(path, paint);

        c.restoreToCount(restoreCount);

    }

    private void drawBottom(Canvas c, RecyclerView parent) {
        int restoreCount = c.save();

        float left = isScrollWithRecyclerView ? (-scrolledX) % (length + gap) : 0;
        float y = parent.getHeight() - offset - thickness / 2.0f;
        float right = parent.getWidth();
        if (parent.getClipToPadding()) {
            left += parent.getPaddingLeft();
            y -= parent.getPaddingBottom();
            right -= parent.getPaddingRight();
            c.clipRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getWidth() - parent.getPaddingRight(), parent.getHeight() - parent.getPaddingBottom());
        }

        path.reset();
        path.moveTo(left, y);
        path.lineTo(right, y);

        c.drawPath(path, paint);

        c.restoreToCount(restoreCount);
    }


}
