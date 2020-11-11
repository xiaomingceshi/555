package com.mapper;

import org.springframework.stereotype.Service;

import com.po.Salary;

@Service("SalaryDAO")
public interface ISalaryMapper {
    //����н��
	public int save(Salary sa);
	//����Ա����ţ�����н��
	public int updateByEid(Salary sa);
	//����Ա����ţ�ɾ��н��
	public int delByEid(Integer eid);
	//����Ա����ţ���ѯн��
	public Salary findSalaryByEid(Integer eid);
}
