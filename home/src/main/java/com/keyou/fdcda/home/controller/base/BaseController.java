package com.keyou.fdcda.home.controller.base;

import com.keyou.fdcda.api.constants.Constants;
import org.slf4j.Logger;
import org.springframework.ui.Model;

import java.util.Map;

/**
 * Created by zzq on 2017-08-28.
 */
public class BaseController {
    public void commonError(Logger logger, Exception e, String msg, Map<String, Object> map) {
        map.put(Constants.SUCCESS, false);
        map.put(Constants.MESSAGE, msg + ":" + e.getMessage());
        logger.error(msg, e);
    }

    public void commonError(Logger logger, Exception e, String msg, Model model) {
        model.addAttribute(Constants.SUCCESS, false);
        model.addAttribute(Constants.MESSAGE, msg + ":" + e.getMessage());
        logger.error(msg, e);
    }
}
