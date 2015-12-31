package com.ys.dao;

import java.util.List;

import com.ys.interceptor.Page;
import com.ys.model.Company;

public interface CompanyMapper {

    int insert(Company company);
    
    List<Company> selectByPage(Page<Company> page);
}
