package com.ys.service;

import java.util.List;
import java.util.Map;

import com.ys.interceptor.Page;
import com.ys.model.Company;


public interface ICompanyService {

    public Map<String,String> writeInformationDB(Company company);
    
    List<Company> getCompany(String page, String amountPerPage);
}
