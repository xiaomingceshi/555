package com.biz;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.IWelfareBiz;
import com.po.Welfare;
import com.service.DaoService;
@Service("WelfareBiz")
@Transactional
public class WelfareBizImpl implements IWelfareBiz {
	@Resource(name="DaoService")
    private DaoService daoService;
	 
	public DaoService getDaoService() {
		return daoService;
	}

	public void setDaoService(DaoService daoService) {
		this.daoService = daoService;
	}

	@Override
	public List<Welfare> findAll() {
		// TODO Auto-generated method stub
		return daoService.getWelfaremapper().findAll();
	}

}
