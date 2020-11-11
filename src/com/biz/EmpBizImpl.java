package com.biz;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.IEmpBiz;
import com.po.Emp;
import com.po.EmpWelfare;
import com.po.PageBean;
import com.po.Salary;
import com.po.Welfare;
import com.service.DaoService;
@Service("EmpBiz")
@Transactional
public class EmpBizImpl implements IEmpBiz {
	@Resource(name="DaoService")
    private DaoService daoService;
	 
	public DaoService getDaoService() {
		return daoService;
	}

	public void setDaoService(DaoService daoService) {
		this.daoService = daoService;
	}

	@Override
	public boolean save(Emp emp) {
		/*
		��ӣ�
		1.��ȡEmpmapper().save(emp)��
		2.��ȡ�ո���ӵ����eid������eid����Salarymapper().save(sa)����
		3.��ȡEmp�и������飨��ǰ̨��ѡ���л�ȡ��������ÿ����������ֵ��Ա��������������Empwelfaremapper().save(ewf)����
		*/
		int code=daoService.getEmpmapper().save(emp);//eid������ÿ����Ӷ������ֵ��Ա�������н�ʺ͸�����չʾ��ͼ��Ч��������н�ʺ͸�����Ҫ������ӣ�����eid�����ӣ���
		if(code>0){
			//����н��
			Integer eid=daoService.getEmpmapper().findmaxId();//��ȡ�ոձ����eid,Ȼ�������eidȥ����н�ʺ͸���
			Salary sa=new Salary(eid,emp.getEmoney());//н���е�sid������ֻ�����eid��emoney����
			daoService.getSalarymapper().save(sa);
			//���渣��
			String[] wids=emp.getWids();//��ȡ�������飬һ��Ա���ж������
			if(wids!=null&&wids.length>0){
				for(int i=0;i<wids.length;i++){
					EmpWelfare ewf=new EmpWelfare(eid,new Integer(wids[i]));//��ÿ��������ֵ��Ա��������
					daoService.getEmpwelfaremapper().save(ewf);//��󱣴�Ա����������
				}
			}
			return true;
		}
		
		return false;
	}

	@Override
	public boolean update(Emp emp) {
		/*
		���£�
		1.getEmpmapper().update(emp)��
		2.��ȡԭ����н��getSalarymapper().findSalaryByEid(emp.getEid())
			2.1 �µĸ�ֵsetEmoney(emp.getEmoney())
			2.2��ԭ����ֵ�ٱ���һ��getSalarymapper().save(sa)
		3.��ȡԭ����Ա������getEmpwelfaremapper().findByEid(emp.getEid())
			3.1 ɾ��ԭ��Ա������getEmpwelfaremapper().delByEid(emp.getEid())
			3.2����µĸ���getEmpwelfaremapper().save(ewf)
		*/
		int code=daoService.getEmpmapper().update(emp);
		if(code>0){//���´ӱ�н�ʱ��Ա��������н�ʱ��Ա�����������
			 //����н�ʲ���
				//��ȡԭ����н��
			Salary oldsa=daoService.getSalarymapper().findSalaryByEid(emp.getEid());
			if(oldsa!=null&&oldsa.getEmoney()!=null){
				oldsa.setEmoney(emp.getEmoney());//н���޸�
				daoService.getSalarymapper().updateByEid(oldsa);
			}else{
				Salary sa=new Salary(emp.getEid(),emp.getEmoney());
				daoService.getSalarymapper().save(sa);
			}
			//����Ա��������
			  //��ȡԭ����Ա������
			List<Welfare>  lswf=daoService.getEmpwelfaremapper().findByEid(emp.getEid());
			if(lswf!=null&&lswf.size()>0){
				//ɾ��ԭ��Ա������
				daoService.getEmpwelfaremapper().delByEid(emp.getEid());
			}
			//��Ӹ���
			String[] wids=emp.getWids();
			if(wids!=null&&wids.length>0){
				for(int i=0;i<wids.length;i++){
					EmpWelfare ewf=new EmpWelfare(emp.getEid(),new Integer(wids[i]));
					daoService.getEmpwelfaremapper().save(ewf);
				}
			}
			/**����Ա��������end***/
			return true;
		}
		return false;
	}

	@Override
	public boolean delById(Integer eid) {
/*		//ɾ���ӱ�
		daoService.getSalarymapper().delByEid(eid);
		daoService.getEmpwelfaremapper().delByEid(eid);
		//ɾ��Ա����
		int code=daoService.getEmpmapper().delById(eid);
		if(code>0){
			return true;
		}*/
		return false;
	}

	@Override
	public Emp findById(Integer eid) {
		/*//��ȡԱ������
		Emp oldemp=daoService.getEmpmapper().findById(eid);
		*//******��ȡн��*******//*
		Salary oldsa=daoService.getSalarymapper().findSalaryByEid(eid);
		if(oldsa!=null&&oldsa.getEmoney()!=null){
			oldemp.setEmoney(oldsa.getEmoney());
		}
		*//******��ȡн��end*******//*
		*//******��ȡ����*******//*
		 //��ȡԭ����Ա������
		List<Welfare>  lswf=daoService.getEmpwelfaremapper().findByEid(oldemp.getEid());
		if(lswf!=null&&lswf.size()>0){
			//������������
			String[] wids=new String[lswf.size()];
			for(int i=0;i<lswf.size();i++){
				Welfare wf=lswf.get(i);
				wids[i]=wf.getWid().toString();
			}
			oldemp.setWids(wids);
		}
		*//******��ȡ����end*******//*
		oldemp.setLswf(lswf);//��ϸҳ��
*/		return oldemp;
	}

	@Override
	public List<Emp> findPageAll(PageBean pb) {
/*		if(pb!=null){
			return daoService.getEmpmapper().findPageAll(pb);
		}*/
		return null;
	}

	@Override
	public int findMaxRows() {
		// TODO Auto-generated method stub
		return daoService.getEmpmapper().findMaxRows();
	}

}
