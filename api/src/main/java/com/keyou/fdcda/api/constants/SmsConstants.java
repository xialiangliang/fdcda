package com.keyou.fdcda.api.constants;

/**
 * Created by zzq on 2017-09-01.
 */
public enum SmsConstants {
    NULL(0L),
    REGISTER_SUCCESS(1L),
    RESET_PASSWORD(2L);
    
    private Long type;
    
    SmsConstants(Long type) {
        this.type = type;
    }

    public Long getType() {
        return type;
    }
}
