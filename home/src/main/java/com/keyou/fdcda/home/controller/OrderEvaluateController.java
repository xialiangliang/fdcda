package com.keyou.fdcda.home.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.keyou.fdcda.api.model.CustomerInfo;
import com.keyou.fdcda.api.model.OrderInfo;
import com.keyou.fdcda.api.model.SysUser;
import com.keyou.fdcda.api.service.CustomerInfoService;
import com.keyou.fdcda.api.service.OrderInfoService;
import com.keyou.fdcda.api.service.SysUserService;
import com.keyou.fdcda.api.utils.Assert;
import com.keyou.fdcda.api.utils.GsonUtil;
import com.keyou.fdcda.api.utils.StringUtil;
import com.keyou.fdcda.api.utils.config.UrlConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.keyou.fdcda.api.constants.Constants;
import com.keyou.fdcda.api.model.OrderEvaluate;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.OrderEvaluateService;
import com.keyou.fdcda.home.controller.base.BaseController;

@Controller
@RequestMapping("/evaluate")
public class OrderEvaluateController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(OrderEvaluateController.class);

    @Autowired
    private OrderEvaluateService orderEvaluateService;
    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private CustomerInfoService customerInfoService;
    @Autowired
    private UrlConfig urlConfig;
    @Autowired
    private SysUserService sysUserService;

    @RequestMapping(value="/new")
    public String add() throws Exception {
        return "/page/orderEvaluate/new";
    }

    @RequestMapping(value="/find")
    public String find(Long id, Model model, HttpServletRequest request){
        try {
            OrderEvaluate orderEvaluate = orderEvaluateService.findById(id);
            OrderInfo orderInfo = orderInfoService.findById(orderEvaluate.getOrderRowId());
            Assert.isTrue(orderInfo.getUserRowId() != null
                    && !getUser(request).getId().equals(orderInfo.getUserRowId()), "非法操作");
            model.addAttribute("param", orderEvaluate);
            model.addAttribute(Constants.SUCCESS, true);
            return "/page/orderEvaluate/update";
        } catch (Exception e) {
            commonError(logger, e, "修改跳转异常", model);
            return "/page/orderEvaluate";
        }
    }

    @RequestMapping(value="/save")
    @ResponseBody
    public Map<String, Object> save(@ModelAttribute("orderEvaluate") OrderEvaluate orderEvaluate,Model model, HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            orderEvaluate.setCreateDate(new Date());
            orderEvaluateService.save(orderEvaluate);
            model.addAttribute(Constants.SUCCESS, true);
            map.put(Constants.SUCCESS, true);
            map.put(Constants.MESSAGE, "添加成功");

        } catch (Exception e) {
            commonError(logger, e, "添加异常",map);
        }
        return map;
    }

    @RequestMapping(value="/update")
    @ResponseBody
    public Map<String, Object> update(@ModelAttribute("orderEvaluate") OrderEvaluate orderEvaluate,Model model, HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            OrderEvaluate orderEvaluate2 = orderEvaluateService.findById(orderEvaluate.getId());
            OrderInfo orderInfo2 = orderInfoService.findById(orderEvaluate2.getOrderRowId());
            Assert.isTrue(orderInfo2.getUserRowId() != null
                    && !getUser(request).getId().equals(orderInfo2.getUserRowId()), "非法操作");
            orderEvaluateService.update(orderEvaluate);
            model.addAttribute(Constants.SUCCESS, true);
            map.put(Constants.SUCCESS, true);
            map.put(Constants.MESSAGE, "修改成功");
        } catch (Exception e) {
            commonError(logger, e,"修改异常",map);
        }
        return map;
    }

    @RequestMapping(value="/delete")
    @ResponseBody
    public Map<String, Object> delete(Long id,Model model, HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            OrderEvaluate orderEvaluate = orderEvaluateService.findById(id);
            OrderInfo orderInfo = orderInfoService.findById(orderEvaluate.getOrderRowId());
            Assert.isTrue(orderInfo.getUserRowId() != null
                    && !getUser(request).getId().equals(orderInfo.getUserRowId()), "非法操作");
            orderEvaluateService.deleteById(id);
            map.put(Constants.SUCCESS, true);
            map.put(Constants.MESSAGE, "删除成功");
        } catch (Exception e) {
            commonError(logger, e,"删除异常",map);
        }
        return map;
    }

    @RequestMapping
    public String list(PaginationQuery query,Model model, HttpServletRequest request) throws Exception {
        query.addQueryData("userRowId", getUser(request).getId().toString());
        PageResult<OrderEvaluate> pageList = orderEvaluateService.findPage(query);
        model.addAttribute("result", pageList);
        model.addAttribute("query", query.getQueryData());
        return "/page/orderEvaluate/list";
    }

    @RequestMapping("/listJson")
    @ResponseBody
    public Map<String, Object> userListJson(PaginationQuery query, Model model, HttpServletRequest request, Integer page, Integer limit) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        this.formatPageQuery(query, page, limit);
        query.addQueryData("userRowId", getUser(request).getId().toString());
        PageResult<OrderInfo> pageList = orderInfoService.findPage(query);
        pageList.getRows().forEach(orderInfo -> {
            if (orderInfo.getCustomerRowId() != null) {
                CustomerInfo customerInfo = customerInfoService.findById(orderInfo.getCustomerRowId());
                if (customerInfo != null) {
                    orderInfo.setCustomerName(customerInfo.getName());
                }
            }
        });
        map.put("data", pageList.getRows());
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", pageList.getRowCount());
        map.put("query", query.getQueryData());
        return map;
    }


    @RequestMapping(value="/detail")
    public String detail(Long id, Model model, HttpServletRequest request){
        try {
            OrderInfo orderInfo0 = orderInfoService.findById(id);
            CustomerInfo customerInfo = customerInfoService.findById(orderInfo0.getCustomerRowId());
            Assert.isTrue(customerInfo.getUserRowId() != null
                    && !getUser(request).getId().equals(customerInfo.getUserRowId()), "非法操作");
            if (StringUtil.isNotBlank(customerInfo.getImageUrl())) {
                customerInfo.setImageUrl(customerInfo.getImageUrl().replaceAll("/mnt/facepics", urlConfig.getImgPath()));
            }
            List<Long> customerIds = customerInfoService.findRealCustomerIdBySingleId(customerInfo.getId());
            List<OrderEvaluate> evaluateList = orderEvaluateService.findListByCustomerIds(customerIds);
            evaluateList.forEach(orderEvaluate -> {
                if (StringUtil.isNotBlank(orderEvaluate.getImagesUrl())) {
                    String[] urls = orderEvaluate.getImagesUrl().split(",");
                    orderEvaluate.setImagesUrlList(Arrays.asList(urls));
                }
                OrderInfo orderInfo = orderInfoService.findById(orderEvaluate.getOrderRowId());
                if (orderInfo != null) {
                    SysUser user = sysUserService.findById(orderInfo.getUserRowId());
                    if (user != null) {
                        orderEvaluate.setEvaluateName(StringUtil.hideName(user.getUsername()));
                    }
                }
            });
            model.addAttribute("param", customerInfo);
            model.addAttribute("evaluateList", GsonUtil.serialize(evaluateList));
            model.addAttribute(Constants.SUCCESS, true);
            return "/page/orderEvaluate/detail";
        } catch (Exception e) {
            commonError(logger, e, "修改跳转异常", model);
            return "/page/orderEvaluate/list";
        }
    }

}


