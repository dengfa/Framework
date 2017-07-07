//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hyena.knowboxbase.coretext;

import android.text.TextUtils;
import com.hyena.coretext.TextEnv;
import com.hyena.coretext.blocks.CYEditBlock;
import com.hyena.coretext.blocks.CYEditFace;
import com.hyena.coretext.blocks.CYParagraphStyle;
import com.hyena.coretext.blocks.ICYEditable;
import com.hyena.coretext.blocks.CYPlaceHolderBlock.AlignStyle;
import com.hyena.framework.utils.UIUtils;
import com.knowbox.base.coretext.EditFace;
import org.json.JSONException;
import org.json.JSONObject;

public class BlankBlock extends CYEditBlock {
    public static String CLASS_FILL_IN = "fillin";
    public static String CLASS_CHOICE = "choice";
    private String mClass;
    private String size;
    private int mWidth;
    private int mHeight;
    private static final int DP_3 = UIUtils.dip2px(3.0F);
    private static final int DP_1 = UIUtils.dip2px(1.0F);

    public BlankBlock(TextEnv textEnv, String content) {
        super(textEnv, content);
        this.mClass = CLASS_CHOICE;
        this.init(content);
    }

    private void init(String content) {
        try {
            JSONObject e = new JSONObject(content);
            this.setTabId(e.optInt("id"));
            this.setDefaultText(e.optString("default"));
            this.size = e.optString("size", "line");
            this.mClass = e.optString("class", CLASS_CHOICE);
            if(this.getTextEnv().isEditable()) {
                if("line".equals(this.size)) {
                    this.getEditFace().getTextPaint().setTextSize((float)UIUtils.dip2px(20.0F));
                    this.getEditFace().getDefaultTextPaint().setTextSize((float)UIUtils.dip2px(20.0F));
                    this.setAlignStyle(AlignStyle.Style_MONOPOLY);
                }

                this.setPadding(DP_3, DP_1, DP_3, DP_1);
            } else {
                this.mClass = CLASS_FILL_IN;
                this.setPadding(DP_1, DP_1, DP_1, DP_1);
            }

            ((EditFace)this.getEditFace()).setClass(this.mClass);
        } catch (JSONException var3) {
            var3.printStackTrace();
        }

        this.updateSize();
        this.getEditFace().postInit();
    }

    public void setText(String text) {
        if(this.getTextEnv() != null) {
            this.getTextEnv().setEditableValue(this.getTabId(), text);
            this.updateSize();
            this.getTextEnv().getEventDispatcher().requestLayout();
        }

    }

    private void updateSize() {
        int textHeight = this.getTextHeight(this.getEditFace().getTextPaint());
        if(!this.getTextEnv().isEditable()) {
            String text = this.getEditFace().getText();
            if(CLASS_CHOICE.equals(this.mClass)) {
                text = "(" + text + ")";
            }

            int width = (int)this.getEditFace().getTextPaint().measureText(text);
            this.mWidth = width;
            this.mHeight = textHeight;
        } else if("letter".equals(this.size)) {
            this.mWidth = UIUtils.dip2px(32.0F);
            this.mHeight = UIUtils.dip2px(40.0F);
        } else if("line".equals(this.size)) {
            this.mWidth = UIUtils.dip2px(265.0F);
            this.mHeight = UIUtils.dip2px(40.0F);
        } else if("express".equals(this.size)) {
            this.mWidth = UIUtils.dip2px(50.0F);
            this.mHeight = UIUtils.dip2px(40.0F);
        } else {
            this.mWidth = UIUtils.dip2px(50.0F);
            this.mHeight = textHeight;
        }

    }

    public void setParagraphStyle(CYParagraphStyle style) {
        super.setParagraphStyle(style);
        if(style != null) {
            this.updateSize();
        }

    }

    public void onMeasure() {
        super.onMeasure();
    }

    public int getContentWidth() {
        return this.mWidth;
    }

    public int getContentHeight() {
        return this.mHeight;
    }

    protected CYEditFace createEditFace(TextEnv textEnv, ICYEditable editable) {
        return new EditFace(textEnv, editable);
    }

    public boolean isValid() {
        return !this.getTextEnv().isEditable() && TextUtils.isEmpty(this.getEditFace().getText())?false:super.isValid();
    }
}
