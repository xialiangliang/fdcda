package com.keyou.fdcda.api.utils.config;

import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

/**
 * Created by zzq on 2017/8/24.
 */
public class UrlConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Value("#{staticUrlConfig['url.web_address']}")
    private String webPath;

    public String getWebPath() {
        return webPath;
    }

    public void setWebPath(String webPath) {
        this.webPath = webPath;
    }
}
