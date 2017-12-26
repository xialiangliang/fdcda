package com.keyou.fdcda.api.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum SexType {
    MALE(1, "男"), FEMALE(2, "女"), SECRET(0, "保密");

    private int code;
    private String desc;

    SexType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

public class IdCardUtil {
    //地区码：http://www.mca.gov.cn/article/sj/tjbz/a/2017/
    
    private static Hashtable<String, String> areaCodeMap = new Hashtable<String, String>();
    private static final Logger logger = LoggerFactory.getLogger(IdCardUtil.class);

    static {
        areaCodeMap.put("11", "北京");
        areaCodeMap.put("12", "天津");
        areaCodeMap.put("13", "河北");
        areaCodeMap.put("14", "山西");
        areaCodeMap.put("15", "内蒙古");
        areaCodeMap.put("21", "辽宁");
        areaCodeMap.put("22", "吉林");
        areaCodeMap.put("23", "黑龙江");
        areaCodeMap.put("31", "上海");
        areaCodeMap.put("32", "江苏");
        areaCodeMap.put("33", "浙江");
        areaCodeMap.put("34", "安徽");
        areaCodeMap.put("35", "福建");
        areaCodeMap.put("36", "江西");
        areaCodeMap.put("37", "山东");
        areaCodeMap.put("41", "河南");
        areaCodeMap.put("42", "湖北");
        areaCodeMap.put("43", "湖南");
        areaCodeMap.put("44", "广东");
        areaCodeMap.put("45", "广西");
        areaCodeMap.put("46", "海南");
        areaCodeMap.put("50", "重庆");
        areaCodeMap.put("51", "四川");
        areaCodeMap.put("52", "贵州");
        areaCodeMap.put("53", "云南");
        areaCodeMap.put("54", "西藏");
        areaCodeMap.put("61", "陕西");
        areaCodeMap.put("62", "甘肃");
        areaCodeMap.put("63", "青海");
        areaCodeMap.put("64", "宁夏");
        areaCodeMap.put("65", "新疆");
        areaCodeMap.put("71", "台湾");
        areaCodeMap.put("81", "香港");
        areaCodeMap.put("82", "澳门");
        areaCodeMap.put("91", "国外");
    }

    public static void main(String[] args) {
          System.out.println(checkIdCard("360429198710203112"));;
    }

    /**
     * 判断身份证号码是否有效
     * @param idCard
     * @return
     */
    public static String checkIdCard(String idCard) {
        idCard = idCard.toUpperCase();
        return checkIdCard(idCard, 150, 1);
    }

    /**
     * 判断身份证是否有效,校验规则不一定在任何情况下有效（说明见:{@link #checkLastOfIdCard(String)}）
     * 校验：
     * 1、长度是15位或18位、15位身份证为全数字，18位身份证除最后一位外，都为数字
     * 2、前6位地区编码,校验前2位
     * 3、校验生日：15位身份证7-8位是年份后两位(19XX年),18位身份证7-14位为年月日
     * 4、15位身份证最后3位无法确定的顺序号，不校验
     * 5、18位身份证最后一位为校验码,校验规则见：{@link #checkLastOfIdCard(String)}
     * @param idCard
     * @param maxYear 最大年数
     * @param minDay 最小天数
     * @return
     */
    public static String checkIdCard(String idCard, int maxYear, int minDay) {
        int len = StringUtils.length(idCard);
        if (!checkLength(len)) {
            return "身份证长度必须是15位或18位";
        }
        if (!isNumeric(StringUtils.substring(idCard, 0, len - 1))) {
            return len == 15 ? "身份证号码都应为数字" : "18位身份证号码除最后一位外,都应为数字。";
        }
        //参数校验
        maxYear = (maxYear <= 0 || maxYear > 150) ? 150 : maxYear;
        minDay = (minDay <= 0) ? 1 : minDay;
        //前6位为地区编码
        String areaCode = StringUtils.substring(idCard, 0, 6);
        //校验前2位地区码是否正确
        if (!areaCodeMap.containsKey(StringUtils.substring(areaCode, 0, 2))) {
            return "身份证地区码无效";
        }
        //15位身份证7-12位为yyMMdd(只能表示19XX年)，18位身份证7-14位为yyyyMMdd
        Date birthDay = getBirthDayOfIdCard(idCard);
        Date currentDate = new Date();

        //非法生日:不是日期、小于等于最小天数(默认1天)、大于最大年数(默认150岁)
        if (null == birthDay || DateUtil.compareDay(birthDay, currentDate) <= minDay || DateUtil.compareYear(birthDay, currentDate) > maxYear) {
            return "身份证生日无效";
        }
        //15位身份证最后3位为无法确定的顺序号（其中最后一位为性别识别码）
        if (len == 15) {
            return "true";
        }
        //15-17位为无法确定的顺序号,最后一位为校验码
        return checkLastOfIdCard(idCard) ? "true" : "身份证校验码错误";
    }

    /**
     * 从身份证号码中提取生日
     * @param idCard
     * @return
     */
    public static Date getBirthDayOfIdCard(String idCard) {
        int len = StringUtils.length(idCard);
        if (!checkLength(len)) {
            return null;
        }
        String birth = StringUtils.substring(idCard, 6, len == 15 ? 12 : 14);
        return DateUtil.getDate(((len == 15) ? ("19" + birth) : birth), "yyyyMMdd");
    }

    /**
     * 从身份证提取性别,默认不校验身份证是否有效,只提取性别识别码进行识别
     * @param idCard
     * @return
     */
    public static SexType getSexOfIdCard(String idCard) {
        return getSexOfIdCard(idCard, false);
    }

    /**
     * 从身份证提取性别
     * @param idCard
     * @param check 是否校验身份证是否有效
     * @return
     */
    public static SexType getSexOfIdCard(String idCard, boolean check) {
        int len = StringUtils.length(idCard);
        if (!checkLength(len) && (check && StringUtils.isNotBlank(checkIdCard(idCard)))) {
            return null;
        }
        String str = String.valueOf(idCard.charAt(len - 2));
        //奇数为男 :i%2!=0 or (i&1)!=1 偶数为女 
        return (Integer.valueOf(StringUtils.equals(str, "X") ? "10" : str) & 1) != 1 ? SexType.MALE : SexType.FEMALE;
    }

    /**
     * 判断身份证位数
     * @param len
     * @return
     */
    private static boolean checkLength(int len) {
        if (len == 15 || len == 18) {
            return true;
        }
        logger.warn("身份证长度必须为15位或18位,当前长度:{}", len);
        return false;
    }

    /**
     * 检验身份证最后一位是否有效,不一定在任何情况下有效(15位没有校验位:522634520829128),例如：420682198611093210、420982198611063210校验都通过
     * 校验规则:身份证前17位数字与其对应的加权因子相乘，在把加积相加，最后除以11取余做转换,得到第18位的校验码
     * @param idCard
     * @return
     */
    public static boolean checkLastOfIdCard(String idCard) {
        int len = StringUtils.length(idCard);
        if (15 == len) {
            logger.warn("15位身份证没有校验码(18位身份证最后一位为校验码)");
            return false;
        }
        //身份证第1到17位的加权因子
        int[] validas = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
        char[] idCards = idCard.toCharArray();
        int count = 0;
        //身份证前17位数字与其对应的加权因子相乘，在把加积相加，最后除以11取余做转换,得到第18位上的校验码
        for (int i = 0; i < validas.length; i++) {
            count += Integer.valueOf(String.valueOf(idCards[i])) * validas[i];
        }
        //余数和校验码转换规则
        String[] temp = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
        return StringUtils.equals(String.valueOf(idCard.charAt(len - 1)), temp[count % 11]);
    }

    /**   
     * 功能：身份证的有效验证   
     * @param IDStr 身份证号   
     * @return 有效：返回"true" 无效：返回String信息   
     * @throws ParseException   
     * {@link #checkLastOfIdCard(String)}
     */
    public static String IDCardValidate(String IDStr) {
        return checkIdCard(IDStr);
    }

    /**   
      * 功能：判断字符串是否为数字   
      * @param str   
      * @return   
      */
    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**验证日期字符串是否是YYYY-MM-DD格式 
      * @param str 
      * @return 
      */
    public static boolean isDataFormat(String str) {
        boolean flag = false;
        //String regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";  
        String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
        Pattern pattern1 = Pattern.compile(regxStr);
        Matcher isNo = pattern1.matcher(str);
        if (isNo.matches()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 根据身份号码获取他的生日
     *
     * @param idcard
     * @return
     * {@link #getBirthDayOfIdCard(String)}
     */
    @Deprecated
    public static Date getBirth(String idcard) {
        Date date = null;
        if (idcard.length() == 18) {
            date = DateUtil.getDate(idcard.substring(6, 14), "yyyyMMdd");
        }

        if (idcard.length() == 15) {
            date = DateUtil.getDate(idcard.substring(6, 12), "yyMMdd");
        }
        return date;
    }

    /**根据身份证号码，返回先生或者女士
     * {@link #getSexOfIdCard(String)}
     * */
    @Deprecated
    public static String getSex(String idcard) {
        String sex = "先生";
        try {
            Integer i = Integer.valueOf(idcard.substring(16, 17));
            if (i % 2 == 0) {
                sex = "女士";
            }
        } catch (Exception e) {
            logger.error("根据身份证号码查询男女错误，身份证号码为{}。", idcard);
        }
        return sex;
    }

    /**   
     * 功能：身份证是否满18周岁
     * @param IDStr 身份证号   
     * @return 有效：返回"" 无效：返回String信息   
     * @throws ParseException   
     * {@link #checkLastOfIdCard(String)}
     */
    public static String IDCardValidateByYears(String IDStr) {
        String idCard = IDStr.toUpperCase();

        Date birthDay = getBirthDayOfIdCard(idCard);
        Date currentDate = new Date();
        int year = DateUtil.compareYear(birthDay, currentDate);
        //非法生日:不是日期、小于等于最小天数(默认1天)、大于最大年数(默认150岁)
        if (year >= 18) {
            return "true";
        } else {
            return "false";
        }
    }

}
