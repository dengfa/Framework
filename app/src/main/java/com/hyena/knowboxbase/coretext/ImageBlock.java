//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hyena.knowboxbase.coretext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import com.hyena.coretext.TextEnv;
import com.hyena.coretext.blocks.CYImageBlock;
import com.hyena.coretext.blocks.CYPlaceHolderBlock.AlignStyle;
import com.hyena.framework.utils.UIUtils;
import com.hyena.framework.utils.UiThreadHandler;
import com.knowbox.base.R.drawable;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageBlock extends CYImageBlock {
    private String mUrl = "";
    private Paint mPaint = new Paint(1);
    private boolean isLoading = false;
    private String size;
    private Drawable mFailSmallDrawable = null;
    private Drawable mFailBigDrawable = null;
    private static final int DP_14 = UIUtils.dip2px(14.0F);
    private static final int DP_38 = UIUtils.dip2px(38.0F);
    private static final int DP_199 = UIUtils.dip2px(199.0F);
    private static final int DP_79 = UIUtils.dip2px(79.0F);
    Rect mTempRect = new Rect();

    public ImageBlock(TextEnv textEnv, String content) {
        super(textEnv, content);
        this.init(textEnv.getContext(), content);
    }

    private void init(Context context, String content) {
        try {
            this.mPaint.setColor(-16777216);
            this.mPaint.setTextSize((float)DP_14);
            JSONObject e = new JSONObject(content);
            String url = e.optString("src");
            String size = e.optString("size");
            this.mFailSmallDrawable = context.getResources().getDrawable(drawable.block_image_fail_small);
            this.mFailBigDrawable = context.getResources().getDrawable(drawable.block_image_fail_big);
            this.size = size;
            if("big_image".equals(size)) {
                this.setAlignStyle(AlignStyle.Style_MONOPOLY);
                this.setWidth(this.getTextEnv().getPageWidth());
                this.setHeight((int)((float)this.getTextEnv().getPageWidth() / 2.52F));
            } else if("small_img".equals(size)) {
                this.setWidth(DP_38);
                this.setHeight(DP_38);
            } else {
                this.setWidth(DP_199);
                this.setHeight(DP_79);
            }

            this.setResUrl(url);
        } catch (JSONException var6) {
            var6.printStackTrace();
        }

    }

    public int getContentWidth() {
        return "big_image".equals(this.size)?this.getTextEnv().getPageWidth():super.getContentWidth();
    }

    public int getContentHeight() {
        return "big_image".equals(this.size)?this.getTextEnv().getPageWidth() / 2:super.getContentHeight();
    }

    public boolean onTouchEvent(int action, float x, float y) {
        super.onTouchEvent(action, x, y);
        switch(action) {
        case 0:
            this.retry();
        default:
            return super.onTouchEvent(action, x, y);
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if((this.mBitmap == null || this.mBitmap.isRecycled()) && !this.isLoading) {
            this.drawFail(canvas);
        }

    }

    protected void drawFail(Canvas canvas) {
        Drawable drawable = this.mFailBigDrawable;
        Rect contentRect = this.getContentRect();
        if(drawable.getIntrinsicWidth() > contentRect.width() || drawable.getIntrinsicHeight() > contentRect.height()) {
            drawable = this.mFailSmallDrawable;
        }

        int x = contentRect.left + (this.getContentWidth() - drawable.getIntrinsicWidth()) / 2;
        int y = contentRect.top + (this.getContentHeight() - drawable.getIntrinsicHeight()) / 2;
        this.mTempRect.set(x, y, x + drawable.getIntrinsicWidth(), y + drawable.getIntrinsicHeight());
        drawable.setBounds(this.mTempRect);
        drawable.draw(canvas);
    }

    protected void setBitmap(Bitmap bitmap) {
        this.isLoading = false;
        super.setBitmap(bitmap);
    }

    public CYImageBlock setResUrl(String url) {
        this.mUrl = url;
        this.isLoading = true;
        UiThreadHandler.postDelayed(new Runnable() {
            public void run() {
                ImageBlock.this.isLoading = false;
                ImageBlock.this.postInvalidateThis();
            }
        }, 3000L);
        this.postInvalidateThis();
        return super.setResUrl(url);
    }

    private void retry() {
        if(!TextUtils.isEmpty(this.mUrl) && (this.mBitmap == null || this.mBitmap.isRecycled())) {
            this.setResUrl(this.mUrl);
        }
    }
}
