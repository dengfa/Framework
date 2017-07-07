//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hyena.knowboxbase.coretext;

import android.graphics.Color;
import android.text.TextUtils;
import com.hyena.coretext.TextEnv;
import com.hyena.coretext.blocks.CYHorizontalAlign;
import com.hyena.coretext.blocks.CYParagraphStartBlock;
import com.hyena.coretext.blocks.CYParagraphStyle;
import com.hyena.framework.utils.UIUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class ParagraphStartBlock extends CYParagraphStartBlock {
    public ParagraphStartBlock(TextEnv textEnv, String content) {
        super(textEnv, content);
        this.init(content);
    }

    private void init(String content) {
        try {
            JSONObject e = new JSONObject(content);
            if(e.has("size")) {
                this.getStyle().setTextSize(UIUtils.dip2px((float)e.optInt("size") * this.getTextEnv().getFontScale() / 2.0F));
            } else {
                this.getStyle().setTextSize(this.getTextEnv().getFontSize());
            }

            if(e.has("color")) {
                this.getStyle().setTextColor(Color.parseColor(e.optString("color")));
            } else {
                this.getStyle().setTextColor(this.getTextEnv().getTextColor());
            }

            if(e.has("margin")) {
                this.getStyle().setMarginBottom(UIUtils.dip2px((float)(e.optInt("margin") / 2)));
            }

            String align = e.optString("align");
            if(!"left".equals(align) && !TextUtils.isEmpty(align) && this.getTextEnv().isEditable()) {
                if("mid".equals(align)) {
                    this.getStyle().setHorizontalAlign(CYHorizontalAlign.CENTER);
                } else {
                    this.getStyle().setHorizontalAlign(CYHorizontalAlign.RIGHT);
                }
            } else {
                this.getStyle().setHorizontalAlign(CYHorizontalAlign.LEFT);
            }

            String style = e.optString("style");
            this.getStyle().setStyle(style);
        } catch (JSONException var5) {
            var5.printStackTrace();
        }

    }

    public CYParagraphStyle getStyle() {
        return super.getStyle();
    }
}
