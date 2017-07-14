package com.learning.pinyin;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import org.junit.Test;

/**
 * Created by xuechongyang on 17/7/14.
 */
public class JpinyinDemo {

    @Test
    public void testJpinyin() throws Exception{
        String str = "你好世界";
        System.out.println(PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITH_TONE_MARK)); // nǐ,hǎo,shì,jiè
        System.out.println(PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITH_TONE_NUMBER)); // ni3,hao3,shi4,jie4
        System.out.println(PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITHOUT_TONE)); // ni,hao,shi,jie
        System.out.println(PinyinHelper.getShortPinyin(str)); // nhsj
//        PinyinHelper.addPinyinDict("user.dict");  // 添加用户自定义字典
    }
}
