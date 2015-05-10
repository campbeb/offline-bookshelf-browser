package com.kinverarity.offlinebookshelfbrowser;

import android.text.Html;
import android.text.Spanned;

public final class FormatText {

    public final static Spanned asHtml (String s) {
        return Html.fromHtml(fix(s));
    }
    
    public final static String fix (String s) {
        if (s == null)
            s = "";
        s = s.replace("[return]", "\n");
        s = s.replace("\n", "</p><p>");
        return s;
    }
}
