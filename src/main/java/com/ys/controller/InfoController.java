package com.ys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ys.constant.ConstantCode;
import com.ys.crawler.PostRequestCrawler;
import com.ys.model.Campaign;
import com.ys.model.Company;
import com.ys.service.ICampaignService;
import com.ys.service.ICompanyService;

@Controller
public class InfoController {
    
    @Autowired
    private ICampaignService campaignService;
    @Autowired
    private ICompanyService companyService;
    @RequestMapping("/admin/getUserId")
    @ResponseBody
    public Map<String, String> getUserId(HttpServletRequest request) {
        Map<String, String> retMap = new HashMap<>();
        String userId = (String)request.getSession().getAttribute(ConstantCode.USER_SESSION_ID);
        retMap.put("userId", userId);
        return retMap;
    }
    
    @RequestMapping("/admin/getCampaignData")
    @ResponseBody
    public List<Campaign> getCampaignData(HttpServletRequest request) {
        String currentPage = request.getParameter("currentPage");
        String amountPerPage = request.getParameter("amountPerPage");
        List<Campaign> retList = campaignService.getCampaign(currentPage, amountPerPage);
        return retList;
    }
    @RequestMapping("/admin/getCompanyData")
    @ResponseBody
    public List<Company> getCompanyData(HttpServletRequest request) {
        String currentPage = request.getParameter("currentPage");
        String amountPerPage = request.getParameter("numPerPage");
        List<Company> retList = companyService.getCompany(currentPage, amountPerPage);
        return retList;
    }
}
