package dev.answer.yichunzkcx.util;
import android.content.Context;
import android.content.res.AssetManager;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import androidx.core.content.ContextCompat;
import java.util.List;
import androidx.appcompat.R;

public class ProtocolUtil {
   public static String readTextFromAssets(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();
        StringBuilder stringBuilder = new StringBuilder();
        
        try {
            InputStream inputStream = assetManager.open(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static CharSequence parseProtocol(String text, Context context) {
        List<Token> tokens = new ArrayList<>();
        StringBuilder cache = new StringBuilder();
        boolean inTitleContext = false;
        boolean inBoldContext = false;
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '<' && !inTitleContext) {
                inTitleContext = true;
                if (cache.length() > 0) {
                    tokens.add(new Token(cache.toString(), "text"));
                    cache.setLength(0);
                }
            } else if (ch == '>' && inTitleContext) {
                inTitleContext = false;
                if (cache.length() > 0) {
                    tokens.add(new Token(cache.toString(), "title"));
                    cache.setLength(0);
                }
            } else if (ch == '(' && !inBoldContext) {
                inBoldContext = true;
                if (cache.length() > 0) {
                    tokens.add(new Token(cache.toString(), "text"));
                    cache.setLength(0);
                }
            } else if (ch == ')' && inBoldContext) {
                inBoldContext = false;
                if (cache.length() > 0) {
                    tokens.add(new Token(cache.toString(), "bold"));
                    cache.setLength(0);
                }
            } else if (ch == '{') {
                cache.append("(");
            } else if (ch == '}') {
                cache.append(")");
            } else {
                cache.append(ch);
            }
        }
        if (cache.length() > 0) {
            if (inTitleContext) {
                tokens.add(new Token(cache.toString(), "title"));
                cache.setLength(0);
            } else {
                tokens.add(new Token(cache.toString(), "text"));
                cache.setLength(0);
            }
            if (inBoldContext) {
                tokens.add(new Token(cache.toString(), "bold"));
                cache.setLength(0);
            } else {
                tokens.add(new Token(cache.toString(), "text"));
                cache.setLength(0);
            }
        }

        SpannableStringBuilder builder = new SpannableStringBuilder();
        for (Token token : tokens) {
            switch (token.getType()) {
                case "title":
                    builder.append("\n"+token.getText());
                    
                    builder.setSpan(new TextAppearanceSpan(context, R.style.TextAppearance_AppCompat_Large), builder.length() - token.getText().length(), builder.length(), 0);
                    builder.setSpan(new android.text.style.AbsoluteSizeSpan(22, true), builder.length() - token.getText().length(), builder.length(), 0);
                    builder.append("\n");
                    break;
                case "bold":
                    builder.append(token.getText());
                    builder.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), builder.length() - token.getText().length(), builder.length(), 0);
                    break;
                default:
                    builder.append(token.getText());
                    builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, com.google.android.material.R.color.m3_default_color_primary_text)), builder.length() - token.getText().length(), builder.length(), 0);
                    break;
            }
        }

        return builder;
    }
    
    public static class Token {
    private final String text;
    private final String type;

    public Token(String text, String type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }
}

}
