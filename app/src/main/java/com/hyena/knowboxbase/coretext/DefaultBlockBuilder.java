//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hyena.knowboxbase.coretext;

import android.text.TextUtils;
import com.hyena.coretext.AttributedString;
import com.hyena.coretext.TextEnv;
import com.hyena.coretext.blocks.CYBlock;
import com.hyena.coretext.blocks.CYBreakLineBlock;
import com.hyena.coretext.blocks.CYParagraphEndBlock;
import com.hyena.coretext.builder.CYBlockProvider.CYBlockBuilder;
import com.knowbox.base.coretext.AudioBlock;
import com.knowbox.base.coretext.BlankBlock;
import com.knowbox.base.coretext.ImageBlock;
import com.knowbox.base.coretext.ParagraphStartBlock;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

public class DefaultBlockBuilder implements CYBlockBuilder {
    public DefaultBlockBuilder() {
    }

    public List<CYBlock> build(TextEnv textEnv, String content) {
        return this.analysisCommand(textEnv, content).buildBlocks();
    }

    private AttributedString analysisCommand(TextEnv textEnv, String content) {
        AttributedString attributedString = new AttributedString(textEnv, content);
        if(!TextUtils.isEmpty(content)) {
            Pattern pattern = Pattern.compile("#\\{(.*?)\\}#");
            Matcher matcher = pattern.matcher(content);

            while(matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                String data = matcher.group(1);
                CYBlock block = this.getBlock(textEnv, "{" + data + "}");
                if(block != null) {
                    attributedString.replaceBlock(start, end, block);
                }
            }
        }

        return attributedString;
    }

    protected <T extends CYBlock> T getBlock(TextEnv textEnv, String data) {
        try {
            JSONObject e = new JSONObject(data);
            String type = e.optString("type");
            return this.newBlock(textEnv, type, data);
        } catch (JSONException var5) {
            var5.printStackTrace();
            return null;
        }
    }

    protected <T extends CYBlock> T newBlock(TextEnv textEnv, String type, String data) {
        return (CYBlock)("blank".equals(type)?new BlankBlock(textEnv, data):("img".equals(type)?new ImageBlock(textEnv, data):("P".equals(type)?new CYBreakLineBlock(textEnv, data):("para_begin".equals(type)?new ParagraphStartBlock(textEnv, data):("para_end".equals(type)?new CYParagraphEndBlock(textEnv, data):("audio".equals(type)?new AudioBlock(textEnv, data):null))))));
    }
}
