package dev.answer.yichunzkcx.util;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;

public class ImageUtil {
    
    public static Drawable base64ToDrawable(String base64Image, Context context) {
        byte[] imageBytes = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        return new BitmapDrawable(context.getResources(), bitmap);
    }
}
