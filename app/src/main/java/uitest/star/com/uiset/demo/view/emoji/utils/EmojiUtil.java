package uitest.star.com.uiset.demo.view.emoji.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;

import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmojiUtil {


    public static ArrayList<Integer> getEmojiResource(Context context) {
        ArrayList<Integer> emojiList = new ArrayList<>();
        String resStr;
        final Resources resources = context.getResources();
        String packageName = context.getApplicationInfo().packageName;
        //根据资源名称去拿资源对应得id
        for (int i = 1; i < 110; i++) {
            resStr = String.format(Locale.ENGLISH, "face%03d", i);//用三位 不够用0
            int resId = resources.getIdentifier(resStr, "mipmap", packageName);
            if (resId != 0) {
                emojiList.add(resId);
            }
        }

        return emojiList;
    }

    public static int getResource(String url, Context context) {
        String resStr = String.format(Locale.ENGLISH, "face%s", url);//
        final Resources resources = context.getResources();
        String packageName = context.getApplicationInfo().packageName;
        int resId = resources.getIdentifier(resStr, "mipmap", packageName);
        if (resId != 0) {
            return resId;
        }
        return -1;
    }

    /**
     * 判断当前环境
     * @param context
     * @return
     */
    public static boolean isZh(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh"))
            return true;
        else
            return false;
    }

    /**
     * 得到一个SpanableString对象，通过传入的字符串,并进行正则判断
     *
     * @param context
     * @param str
     * @return
     */
    public SpannableString getExpressionString(Context context, String str) {
        SpannableString spannableString = new SpannableString(str);
        // 正则表达式比配字符串里是否含有表情，如： 我好[开心]啊
        String zhengze = "\\[[^\\]]+\\]";
        // 通过传入的正则表达式来生成一个pattern
        Pattern sinaPatten = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE);
        try {
            dealExpression(context, spannableString, sinaPatten, 0);
        } catch (Exception e) {
            Log.e("dealExpression", e.getMessage());
        }
        return spannableString;
    }


    /**
     * 对spanableString进行正则判断，如果符合要求，则以表情图片代替
     *
     * @param context
     * @param spannableString
     * @param patten
     * @param start
     * @throws Exception
     */
    private void dealExpression(Context context,
                                SpannableString spannableString, Pattern patten, int start)
            throws Exception {
        Matcher matcher = patten.matcher(spannableString);
        while (matcher.find()) {
            String key = matcher.group();
            // 返回第一个字符的索引的文本匹配整个正则表达式,ture 则继续递归
            if (matcher.start() < start) {
                continue;
            }
//            String value = emojiMap.get(key);
            String value ="";
            if (TextUtils.isEmpty(value)) {
                continue;
            }
            int resId = context.getResources().getIdentifier(value, "drawable",
                    context.getPackageName());
            // 通过上面匹配得到的字符串来生成图片资源id，下边的方法可用，但是你工程混淆的时候就有事了，你懂的。不是我介绍的重点
            // Field field=R.drawable.class.getDeclaredField(value);
            // int resId=Integer.parseInt(field.get(null).toString());
            if (resId != 0) {
                Bitmap bitmap = BitmapFactory.decodeResource(
                        context.getResources(), resId);
                bitmap = Bitmap.createScaledBitmap(bitmap, 50, 50, true);
                // 通过图片资源id来得到bitmap，用一个ImageSpan来包装
                ImageSpan imageSpan = new ImageSpan(bitmap);
                // 计算该图片名字的长度，也就是要替换的字符串的长度
                int end = matcher.start() + key.length();
                // 将该图片替换字符串中规定的位置中
                spannableString.setSpan(imageSpan, matcher.start(), end,
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                if (end < spannableString.length()) {
                    // 如果整个字符串还未验证完，则继续。。
                    dealExpression(context, spannableString, patten, end);
                }
                break;
            }
        }
    }
}
