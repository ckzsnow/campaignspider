package com.ys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ys.constant.ConstantCode;
import com.ys.dao.CompanyMapper;
import com.ys.interceptor.Page;
import com.ys.model.Campaign;
import com.ys.model.Company;
import com.ys.service.ICompanyService;
@Service("companyService")
public class CompanyServiceImpl implements ICompanyService {
	@Autowired
	private CompanyMapper companyMapper;
	@Override
	public Map<String, String> writeInformationDB(Company company) {
		System.out.println("进来了");
		Map<String, String> retMap = new HashMap<>();
		boolean writeSuccess = true;
		if(company == null ) {
			retMap.put(ConstantCode.CAMPAIGN_SERVICE_WRITE_DATABASE_EMPTY_ERROR_CODE,
					ConstantCode.CAMPAIGN_SERVICE_WRITE_DATABASE_EMPTY_ERROR_MSG);
			return retMap;
		}
		try{
			if(companyMapper.insert(company) == 0) writeSuccess = false;
		}catch(Exception e){
			e.printStackTrace();
		}
			
		if(writeSuccess) {
			retMap.put(ConstantCode.CAMPAIGN_SERVICE_WRITE_DATABASE_SUCCESS_CODE,
					ConstantCode.CAMPAIGN_SERVICE_WRITE_DATABASE_SUCCESS_MSG);
		} else {
			retMap.put(ConstantCode.CAMPAIGN_SERVICE_WRITE_DATABASE_WRITE_ERROR_CODE,
					ConstantCode.CAMPAIGN_SERVICE_WRITE_DATABASE_WRITE_ERROR_MSG);
		}
		return retMap;
	}
	@Override
	public List<Company> getCompany(String currentPage, String amountPerPage) {
		Page<Company> page = new Page<>();
		int currentPage_ = 1;
		int amountPerPage_ = 5;
		try {
			currentPage_ = Integer.valueOf(currentPage);
			amountPerPage_ =Integer.valueOf(amountPerPage);
		} catch(NumberFormatException e) {
			//ignore
		}
		page.setPageNo(currentPage_);
		page.setPageSize(amountPerPage_);
		return companyMapper.selectByPage(page);
	}

}
