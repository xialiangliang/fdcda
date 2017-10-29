package com.keyou.fdcda.api.constants;

import java.util.HashMap;
import java.util.Map;

public class AreaConstants {
    public static Map<Long, String> countryMap = new HashMap<Long, String>() {{
        put(null, "");
        put(1L, "中国");
    }};
    
    public static Map<Long, String> provinceMap = new HashMap<Long, String>() {{
       put(null, "");
       put(1L, "浙江省");
    }};

    public static Map<Long, String> cityMap = new HashMap<Long, String>() {{
        put(null, "");
        put(1L, "杭州市");
    }};
}