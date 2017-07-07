//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hyena.knowboxbase.coretext;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.text.TextUtils;
import com.hyena.coretext.TextEnv;
import com.hyena.coretext.blocks.CYEditFace;
import com.hyena.coretext.blocks.CYParagraphStyle;
import com.hyena.coretext.blocks.ICYEditable;
import com.hyena.framework.utils.UIUtils;
import com.knowbox.base.coretext.BlankBlock;

public class EditFace extends CYEditFace {
    private String mClass;
    private int mRoundCorner;
    private RectF mRectF;
    private Rect mRect;
    private int padding;

    public EditFace(TextEnv textEnv, ICYEditable editable) {
        super(textEnv, editable);
        this.mClass = BlankBlock.CLASS_CHOICE;
        this.mRoundCorner = UIUtils.dip2px(8.0F);
        this.mRectF = new RectF();
        this.mRect = new Rect();
        this.padding = UIUtils.dip2px(5.0F);
    }

    public void setClass(String clazz) {
        this.mClass = clazz;
    }

    protected void drawBorder(Canvas canvas, Rect blockRect, Rect contentRect) {
        if(this.getTextEnv().isEditable()) {
            if(BlankBlock.CLASS_CHOICE.equals(this.mClass) && this.hasFocus() || BlankBlock.CLASS_FILL_IN.equals(this.mClass)) {
                this.mRectF.set(contentRect);
                this.mBorderPaint.setStrokeWidth((float)UIUtils.dip2px(this.getTextEnv().getContext(), 1.0F));
                this.mBorderPaint.setColor(-13527298);
                this.mBorderPaint.setStyle(Style.STROKE);
                canvas.drawRoundRect(this.mRectF, (float)this.mRoundCorner, (float)this.mRoundCorner, this.mBorderPaint);
            }

        }
    }

    protected void drawBackGround(Canvas canvas, Rect blockRect, Rect contentRect) {
        if(this.getTextEnv().isEditable()) {
            this.mBackGroundPaint.setStyle(Style.FILL);
            this.mRectF.set(contentRect);
            if(BlankBlock.CLASS_FILL_IN.equals(this.mClass)) {
                if(this.hasFocus()) {
                    this.mBackGroundPaint.setColor(-1);
                } else {
                    this.mBackGroundPaint.setColor(-1971726);
                }
            } else if(BlankBlock.CLASS_CHOICE.equals(this.mClass)) {
                if(this.hasFocus()) {
                    this.mBackGroundPaint.setColor(-1971726);
                } else {
                    this.mBackGroundPaint.setColor(-1);
                }
            }

            canvas.drawRoundRect(this.mRectF, (float)this.mRoundCorner, (float)this.mRoundCorner, this.mBackGroundPaint);
        }
    }

    protected void drawFlash(Canvas canvas, Rect contentRect) {
        if(this.getTextEnv().isEditable()) {
            this.mRect.set(contentRect);
            this.mRect.top += this.padding;
            this.mRect.bottom -= this.padding;
            this.mFlashPaint.setColor(-12669953);
            this.mFlashPaint.setStrokeWidth((float)UIUtils.dip2px(1.0F));
            if(BlankBlock.CLASS_FILL_IN.equals(this.mClass)) {
                super.drawFlash(canvas, this.mRect);
            }

        }
    }

    protected void drawText(Canvas canvas, String text, Rect contentRect) {
        if(!this.getTextEnv().isEditable()) {
            if(BlankBlock.CLASS_FILL_IN.equals(this.mClass)) {
                super.drawText(canvas, text, contentRect);
                canvas.drawLine((float)contentRect.left, (float)contentRect.bottom, (float)contentRect.right, (float)contentRect.bottom, this.mTextPaint);
            } else if(TextUtils.isEmpty(text)) {
                super.drawText(canvas, "( )", contentRect);
            } else {
                super.drawText(canvas, "(" + text + ")", contentRect);
            }
        } else {
            super.drawText(canvas, text, contentRect);
        }

    }

    public void setParagraphStyle(CYParagraphStyle style) {
        super.setParagraphStyle(style);
        if(style != null && this.mTextPaint != null) {
            this.mTextPaint.setTextSize((float)style.getTextSize());
        }

    }

    public String getText() {
        String text = super.getText();
        return TextUtils.isEmpty(text)?"":super.getText();
    }
}
