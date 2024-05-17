package com.example.demo.toolkits;

//import com.eve.wms.common.constant.Constants;
//import com.eve.wms.common.core.text.StrFormatter;
import org.springframework.util.AntPathMatcher;

import java.math.BigDecimal;
import java.util.*;


/**
 * 字符串工具类
 *
 * @author ruoyi
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    /**
     * 空字符串
     */
    private static final String NULLSTR = "";

    /**
     * 下划线
     */
    private static final char SEPARATOR = '_';

    /**
     * 获取参数不为空值
     *
     * @param value defaultValue 要判断的value
     * @return value 返回值
     */
    public static <T> T nvl(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    /**
     * * 判断一个Collection是否为空， 包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Collection<?> coll) {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * * 判断一个Collection是否非空，包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * * 判断一个对象数组是否为空
     *
     * @param objects 要判断的对象数组
     *                * @return true：为空 false：非空
     */
    public static boolean isEmpty(Object[] objects) {
        return isNull(objects) || (objects.length == 0);
    }

    /**
     * * 判断一个对象数组是否非空
     *
     * @param objects 要判断的对象数组
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Object[] objects) {
        return !isEmpty(objects);
    }

    /**
     * * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    /**
     * * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * * 判断一个字符串是否为空串
     *
     * @param str String
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(String str) {
        return isNull(str) || NULLSTR.equals(str.trim());
    }

    /**
     * * 判断一个字符串是否为非空串
     *
     * @param str String
     * @return true：非空串 false：空串
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * * 判断一个对象是否为空
     *
     * @param object Object
     * @return true：为空 false：非空
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * * 判断一个对象是否非空
     *
     * @param object Object
     * @return true：非空 false：空
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * * 判断一个对象是否是数组类型（Java基本型别的数组）
     *
     * @param object 对象
     * @return true：是数组 false：不是数组
     */
    public static boolean isArray(Object object) {
        return isNotNull(object) && object.getClass().isArray();
    }

    /**
     * 去空格
     */
    public static String trim(String str) {
        return (str == null ? "" : str.trim());
    }

    /**
     * 去空格，isEmpty返回null
     */
    public static String trimNoEmpty(String str) {
        return ( isEmpty(str) ? null : str.trim());
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @return 结果
     */
    public static String substring(final String str, int start) {
        if (str == null) {
            return NULLSTR;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return NULLSTR;
        }

        return str.substring(start);
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @param end   结束
     * @return 结果
     */
    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return NULLSTR;
        }

        if (end < 0) {
            end = str.length() + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return NULLSTR;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * 格式化文本, {} 表示占位符<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
     * 转义\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param params   参数值
     * @return 格式化后的文本
     */
//    public static String format(String template, Object... params) {
//        if (isEmpty(params) || isEmpty(template)) {
//            return template;
//        }
//        return StrFormatter.format(template, params);
//    }

    /**
     * 是否为http(s)://开头
     *
     * @param link 链接
     * @return 结果
     */
//    public static boolean ishttp(String link) {
//        return StringUtils.startsWithAny(link, Constants.HTTP, Constants.HTTPS);
//    }

    /**
     * 字符串转set
     *
     * @param str 字符串
     * @param sep 分隔符
     * @return set集合
     */
    public static final Set<String> str2Set(String str, String sep) {
        return new HashSet<String>(str2List(str, sep, true, false));
    }

    /**
     * 字符串转list
     *
     * @param str         字符串
     * @param sep         分隔符
     * @param filterBlank 过滤纯空白
     * @param trim        去掉首尾空白
     * @return list集合
     */
    public static final List<String> str2List(String str, String sep, boolean filterBlank, boolean trim) {
        List<String> list = new ArrayList<String>();
        if (StringUtils.isEmpty(str)) {
            return list;
        }

        // 过滤空白字符串
        if (filterBlank && StringUtils.isBlank(str)) {
            return list;
        }
        String[] split = str.split(sep);
        for (String string : split) {
            if (filterBlank && StringUtils.isBlank(string)) {
                continue;
            }
            if (trim) {
                string = string.trim();
            }
            list.add(string);
        }

        return list;
    }

    /**
     * 查找指定字符串是否包含指定字符串列表中的任意一个字符串同时串忽略大小写
     *
     * @param cs                  指定字符串
     * @param searchCharSequences 需要检查的字符串数组
     * @return 是否包含任意一个字符串
     */
    public static boolean containsAnyIgnoreCase(CharSequence cs, CharSequence... searchCharSequences) {
        if (isEmpty(cs) || isEmpty(searchCharSequences)) {
            return false;
        }
        for (CharSequence testStr : searchCharSequences) {
            if (containsIgnoreCase(cs, testStr)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 驼峰转下划线命名
     */
    public static String toUnderScoreCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // 前置字符是否大写
        boolean preCharIsUpperCase = true;
        // 当前字符是否大写
        boolean curreCharIsUpperCase = true;
        // 下一字符是否大写
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i > 0) {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            } else {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1)) {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
                sb.append(SEPARATOR);
            } else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
                sb.append(SEPARATOR);
            }
            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inStringIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equalsIgnoreCase(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。 例如：HELLO_WORLD->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String convertToCamelCase(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母大写
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String[] camels = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 首字母大写
            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }

    /**
     * 驼峰式命名法 例如：user_name->userName
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 查找指定字符串是否匹配指定字符串列表中的任意一个字符串
     *
     * @param str  指定字符串
     * @param strs 需要检查的字符串数组
     * @return 是否匹配
     */
    public static boolean matches(String str, List<String> strs) {
        if (isEmpty(str) || isEmpty(strs)) {
            return false;
        }
        for (String pattern : strs) {
            if (isMatch(pattern, str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断url是否与规则配置:
     * ? 表示单个字符;
     * * 表示一层路径内的任意字符串，不可跨层级;
     * ** 表示任意层路径;
     *
     * @param pattern 匹配规则
     * @param url     需要匹配的url
     * @return
     */
    public static boolean isMatch(String pattern, String url) {
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match(pattern, url);
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }

    /**
     * 数字左边补齐0，使之达到指定长度。注意，如果数字转换为字符串后，长度大于size，则只保留 最后size个字符。
     *
     * @param num  数字对象
     * @param size 字符串指定长度
     * @return 返回数字的字符串格式，该字符串为指定长度。
     */
    public static final String padl(final Number num, final int size) {
        return padl(num.toString(), size, '0');
    }

    /**
     * 字符串左补齐。如果原始字符串s长度大于size，则只保留最后size个字符。
     *
     * @param s    原始字符串
     * @param size 字符串指定长度
     * @param c    用于补齐的字符
     * @return 返回指定长度的字符串，由原字符串左补齐或截取得到。
     */
    public static final String padl(final String s, final int size, final char c) {
        final StringBuilder sb = new StringBuilder(size);
        if (s != null) {
            final int len = s.length();
            if (s.length() <= size) {
                for (int i = size - len; i > 0; i--) {
                    sb.append(c);
                }
                sb.append(s);
            } else {
                return s.substring(len - size, len);
            }
        } else {
            for (int i = size; i > 0; i--) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * map转字符串，map("name", "xiaoming") kvSymbol = "@" nodeSymbol = "/" name@xinaoming/....
     *
     * @param maps
     * @param kvSymbol
     * @param nodeSymbol
     * @return
     */
    public static final String map2str(Map<String, String> maps, String kvSymbol, String nodeSymbol) {
        StringBuffer sb = new StringBuffer();
        if (isNotEmpty(maps)) {
            maps.forEach((k, v) -> {
                sb.append(nodeSymbol).append(k).append(kvSymbol).append(v);
            });
            if (sb.length() > 0) {
                sb.deleteCharAt(0);
            }
        }
        return sb.toString();
    }

    /**
     * * 允许为null字符串比较
     *
     * @param a String
     * @param b String
     * @return true：相同 false：不同
     */
    public static boolean isEquals(String a, String b) {
        if (nvl(a, "").equals(nvl(b, "")))
            return true;
        return false;
    }

    /**
     * * 允许为null字符串比较
     *
     * @param a String
     * @param b String
     * @return true：不相同 false：相同
     */
    public static boolean isNotEquals(String a, String b) {
        return !isEquals(a, b);
    }

    /**
     * * 允许为null长整型比较
     *
     * @param a Long
     * @param b Long
     * @return true：相同 false：不同
     */
    public static boolean isEquals(Long a, Long b) {
        if (nvl(a, Long.MIN_VALUE).equals(nvl(b, Long.MIN_VALUE)))
            return true;
        return false;
    }

    /**
     * * 允许为null日期比较
     *
     * @param a Date
     * @param b Date
     * @return true：相同 false：不同
     */
//    public static boolean isEquals(Date a, Date b) {
//        String strA = DateUtils.dateTime(nvl(a, DateUtils.getNowDate()));
//        String strB = DateUtils.dateTime(nvl(b, DateUtils.getNowDate()));
//        if (strA.equals(strB))
//            return true;
//        return false;
//    }

    /**
     * * 允许为null日期时间比较
     *
     * @param a Date
     * @param b Date
     * @return true：相同 false：不同
     */
//    public static boolean isEqualsDateTime(Date a, Date b) {
//        String strA = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, nvl(a, DateUtils.getNowDate()));
//        String strB = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, nvl(b, DateUtils.getNowDate()));
//        return strA.equals(strB);
//    }

    /**
     * 判断字符串是否存在数组中
     *
     * @param value 判断值
     * @param array 包含数组
     * @return 结果
     */
    public static boolean isInclude(String value, String[] array) {
        boolean result = false;
        for (String matchValue : array) {
            if (value.equals(matchValue)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 如果字符串为空则返回"" 否则返回自身
     * @param value 入参
     * @return 字符串
     */
    public static String strIsEmptyToEmptyString(String value) {
        if (isEmpty(value)) {
            return "";
        }
        return value;
    }

    /**
     * 多字符串匹配
     * @param value
     * @param arr
     * @return
     */
    public static boolean isEqualsStringList(String value,String... arr){
        boolean flag = false;
        for (String str : arr){
            if(StringUtils.isEquals(value,str)){
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 字符转数字
     *
     * @param value
     * @return
     */
    public static BigDecimal strToBigDecimal(String value) {
        if (isEmpty(value))
            return BigDecimal.ZERO;
        return new BigDecimal(value);
    }

    /**
     * 获取字符参数不为空值
     *
     * @param value        要判断的value
     * @param defaultValue 默认值
     * @return value 返回值
     */
    public static String nvl(String value, String defaultValue) {
        return isNotEmpty(value) ? value : defaultValue;
    }

    /**
     * 获取指定字符串中特定字符的前缀
     *
     * @param str    输入的字符串
     * @param param  要查找的字符
     * @param index  要获取该字符的第几个位置的前缀
     * @return       指定字符的前缀，如果找不到指定字符，则返回整个字符串
     */
    public static String getPrefixBeforeChar(String str, char param, int index) {
        int count = 0;
        int lastIndex = -1;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == param) {
                count++;
                if (count == index) {
                    lastIndex = i;
                    break;
                }
            }
        }
        if (lastIndex != -1) {
            return str.substring(0, lastIndex);
        } else {
            return str;
        }
    }

    /**
     * 获取字符串中指定位置的字符内容。
     * 如果字符串为空或索引越界，则返回默认值"1"。
     *
     * @param input 要操作的字符串
     * @param index 要获取字符的位置索引
     * @return 指定位置的字符内容或默认值"1"
     */
    public static String getCharAtIndex(String input, int index) {
        // 检查输入字符串是否为空
        if (input == null || input.isEmpty()) {
            return "1";
        }

        // 检查索引是否越界
        if (index < 0 || index >= input.length()) {
            return "1";
        }

        // 返回指定位置的字符
        return String.valueOf(input.charAt(index));
    }
    /**
     * 判断字符串是否正整数
     * @param str 入参
     * @return 是否
     */
    public static boolean checkParsePositiveInteger(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
