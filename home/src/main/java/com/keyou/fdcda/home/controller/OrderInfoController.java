package com.keyou.fdcda.home.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.keyou.fdcda.api.model.CustomerInfo;
import com.keyou.fdcda.api.service.CustomerInfoService;
import com.keyou.fdcda.api.utils.Assert;
import com.keyou.fdcda.api.utils.DateUtil;
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
import com.keyou.fdcda.api.model.OrderInfo;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.OrderInfoService;
import com.keyou.fdcda.home.controller.base.BaseController;

@Controller
@RequestMapping("/order")
public class OrderInfoController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(OrderInfoController.class);

    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private CustomerInfoService customerInfoService;
    @Autowired
    private UrlConfig urlConfig;

    @RequestMapping(value="/new")
    public String add() throws Exception {
        return "/page/orderInfo/new";
    }

    @RequestMapping(value="/find")
    public String find(Long id, Model model,HttpServletRequest request){
        try {
            OrderInfo orderInfo = orderInfoService.findById(id);
            Assert.isTrue(orderInfo.getUserRowId() != null
                    && !getUser(request).getId().equals(orderInfo.getUserRowId()), "非法操作");
            model.addAttribute("param", orderInfo);
            model.addAttribute(Constants.SUCCESS, true);
            return "/page/orderInfo/update";
        } catch (Exception e) {
            commonError(logger, e, "修改跳转异常", model);
            return "/page/orderInfo";
        }
    }

    @RequestMapping(value="/findCustomer")
    @ResponseBody
    public Map<String, Object> findCustomer(String phone, Model model, HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Assert.isBlank(phone, "手机号不能为空");
            PaginationQuery query = new PaginationQuery();
            query.addQueryData("userRowId", getUser(request).getId().toString());
            query.addQueryData("phone", phone);
            PageResult<CustomerInfo> customerInfoPage = customerInfoService.findPage(query);
            if (customerInfoPage.getRowCount() == 0) {
                map.put(Constants.SUCCESS, false);
                map.put(Constants.MESSAGE, "查无此人");
                map.put("data", null);
                return map;
            }
            CustomerInfo customerInfo = customerInfoPage.getRows().get(0);
            Assert.isTrue(customerInfo.getUserRowId() != null
                    && !getUser(request).getId().equals(customerInfo.getUserRowId()), "非法操作");
            if (StringUtil.isNotBlank(customerInfo.getImageUrl())) {
                customerInfo.setImageUrl(customerInfo.getImageUrl().replaceAll("/mnt/facepics", urlConfig.getImgPath()));
            }
            model.addAttribute(Constants.SUCCESS, true);
            map.put(Constants.SUCCESS, true);
            map.put(Constants.MESSAGE, "查询成功");
            map.put("data", customerInfo);

        } catch (Exception e) {
            commonError(logger, e, "查询异常",map);
        }
        return map;
    }

    @RequestMapping(value="/save")
    @ResponseBody
    public Map<String, Object> save(@ModelAttribute("orderInfo") OrderInfo orderInfo,Model model,HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Assert.isBlank(orderInfo.getCustomerPhone(), "手机号不能为空");
            Assert.isNull(orderInfo.getOrderAmt(), "金额不能为空");
            Assert.isBlank(orderInfo.getOrderContent(), "交易内容不能为空");
            PaginationQuery query = new PaginationQuery();
            query.addQueryData("userRowId", getUser(request).getId().toString());
            query.addQueryData("phone", orderInfo.getCustomerPhone());
            PageResult<CustomerInfo> customerInfoPage = customerInfoService.findPage(query);
            if (customerInfoPage.getRowCount() == 0) {
                map.put(Constants.SUCCESS, false);
                map.put(Constants.MESSAGE, "查无此人");
                map.put("data", null);
                return map;
            }
            CustomerInfo customerInfo = customerInfoPage.getRows().get(0);
            orderInfo.setCustomerRowId(customerInfo.getId());
            orderInfo.setCreateDate(new Date());
            orderInfo.setUserRowId(getUser(request).getId());
            if (StringUtil.isNotBlank(orderInfo.getGoodDateStr())) {
                orderInfo.setGoodDate(DateUtil.getDate(orderInfo.getGoodDateStr(), "yyyy-MM-dd"));
            }
            orderInfoService.save(orderInfo);
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
    public Map<String, Object> update(@ModelAttribute("orderInfo") OrderInfo orderInfo,Model model,HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            OrderInfo orderInfo2 = orderInfoService.findById(orderInfo.getId());
            Assert.isTrue(orderInfo2.getUserRowId() != null
                    && !getUser(request).getId().equals(orderInfo2.getUserRowId()), "非法操作");
            orderInfoService.update(orderInfo);
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
    public Map<String, Object> delete(HttpServletRequest request, Long id,Model model) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            OrderInfo orderInfo = orderInfoService.findById(id);
            Assert.isTrue(orderInfo.getUserRowId() != null
                    && !getUser(request).getId().equals(orderInfo.getUserRowId()), "非法操作");
            orderInfoService.deleteById(id);
            map.put(Constants.SUCCESS, true);
            map.put(Constants.MESSAGE, "删除成功");
        } catch (Exception e) {
            commonError(logger, e,"删除异常",map);
        }
        return map;
    }

    @RequestMapping
    public String list(HttpServletRequest request, PaginationQuery query,Model model) throws Exception {
        query.addQueryData("userRowId", getUser(request).getId().toString());
        model.addAttribute("query", query.getQueryData());
        return "/page/orderInfo/list";
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

}


