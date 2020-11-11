package com.mapper;

import java.util.List;

import org.springframework.stereotype.Service;

import com.po.Emp;
import com.po.PageBean;

@Service("EmpDAO")
public interface IEmpMapper {
  public int save(Emp emp);
  public int update(Emp emp);
  public int delById(Integer eid);
  public Emp findById(Integer eid);
  public List<Emp> findPageAll(PageBean pb);
  public int findMaxRows();
  //��ȡ���id
  public int findmaxId();//����biz��ȥʵ�ֹ��ܣ��ɽ�ÿ����ӵ�Ա��ID��ȡ�����棬����IDȥ����н�ʺ͸���
}
