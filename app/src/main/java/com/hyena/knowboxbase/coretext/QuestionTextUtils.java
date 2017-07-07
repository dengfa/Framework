//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hyena.knowboxbase.coretext;

import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestionTextUtils {
    public QuestionTextUtils() {
    }

    public static String formatQuestionText(String rawQuestion, String tabId, String text) {
        Pattern pattern = Pattern.compile("#\\{.*?\\}#");
        Matcher matcher = pattern.matcher(rawQuestion);
        String result = rawQuestion;

        while(matcher.find()) {
            String data = matcher.group();
            if(!TextUtils.isEmpty(data) && data.contains("\"id\":" + tabId)) {
                result = result.replace(data, "#{\"type\":\"para_begin\",\"style\":\"under_line\"}#" + text + "#{\"type\":\"para_end\"}#");
            }
        }

        return result;
    }
}
