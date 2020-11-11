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
		添加：
		1.获取Empmapper().save(emp)；
		2.获取刚刚添加的最大eid，根据eid调用Salarymapper().save(sa)保存
		3.获取Emp中福利数组（从前台复选框中获取），遍历每个福利并赋值给员工福利，最后调用Empwelfaremapper().save(ewf)保存
		*/
		int code=daoService.getEmpmapper().save(emp);//eid自增，每次添加都是最大值；员工表添加薪资和福利，展示视图的效果，所以薪资和福利需要额外添加（根据eid外键添加），
		if(code>0){
			//保存薪资
			Integer eid=daoService.getEmpmapper().findmaxId();//获取刚刚保存的eid,然后用这个eid去保存薪资和福利
			Salary sa=new Salary(eid,emp.getEmoney());//薪资中的sid自增，只需添加eid和emoney即可
			daoService.getSalarymapper().save(sa);
			//保存福利
			String[] wids=emp.getWids();//获取福利数组，一个员工有多个福利
			if(wids!=null&&wids.length>0){
				for(int i=0;i<wids.length;i++){
					EmpWelfare ewf=new EmpWelfare(eid,new Integer(wids[i]));//将每个福利赋值给员工福利表
					daoService.getEmpwelfaremapper().save(ewf);//最后保存员工福利即可
				}
			}
			return true;
		}
		
		return false;
	}

	@Override
	public boolean update(Emp emp) {
		/*
		更新：
		1.getEmpmapper().update(emp)；
		2.获取原来的薪资getSalarymapper().findSalaryByEid(emp.getEid())
			2.1 新的赋值setEmoney(emp.getEmoney())
			2.2将原来的值再保存一遍getSalarymapper().save(sa)
		3.获取原来的员工福利getEmpwelfaremapper().findByEid(emp.getEid())
			3.1 删除原来员工福利getEmpwelfaremapper().delByEid(emp.getEid())
			3.2添加新的福利getEmpwelfaremapper().save(ewf)
		*/
		int code=daoService.getEmpmapper().update(emp);
		if(code>0){//更新从表薪资表和员工福利表，薪资表和员工需另外更新
			 //更新薪资部分
				//获取原来的薪资
			Salary oldsa=daoService.getSalarymapper().findSalaryByEid(emp.getEid());
			if(oldsa!=null&&oldsa.getEmoney()!=null){
				oldsa.setEmoney(emp.getEmoney());//薪资修改
				daoService.getSalarymapper().updateByEid(oldsa);
			}else{
				Salary sa=new Salary(emp.getEid(),emp.getEmoney());
				daoService.getSalarymapper().save(sa);
			}
			//更新员工福利表
			  //获取原来的员工福利
			List<Welfare>  lswf=daoService.getEmpwelfaremapper().findByEid(emp.getEid());
			if(lswf!=null&&lswf.size()>0){
				//删除原来员工福利
				daoService.getEmpwelfaremapper().delByEid(emp.getEid());
			}
			//添加更新
			String[] wids=emp.getWids();
			if(wids!=null&&wids.length>0){
				for(int i=0;i<wids.length;i++){
					EmpWelfare ewf=new EmpWelfare(emp.getEid(),new Integer(wids[i]));
					daoService.getEmpwelfaremapper().save(ewf);
				}
			}
			/**更新员工福利表end***/
			return true;
		}
		return false;
	}

	@Override
	public boolean delById(Integer eid) {
/*		//删除子表
		daoService.getSalarymapper().delByEid(eid);
		daoService.getEmpwelfaremapper().delByEid(eid);
		//删除员工表
		int code=daoService.getEmpmapper().delById(eid);
		if(code>0){
			return true;
		}*/
		return false;
	}

	@Override
	public Emp findById(Integer eid) {
		/*//获取员工对象
		Emp oldemp=daoService.getEmpmapper().findById(eid);
		*//******获取薪资*******//*
		Salary oldsa=daoService.getSalarymapper().findSalaryByEid(eid);
		if(oldsa!=null&&oldsa.getEmoney()!=null){
			oldemp.setEmoney(oldsa.getEmoney());
		}
		*//******获取薪资end*******//*
		*//******获取福利*******//*
		 //获取原来的员工福利
		List<Welfare>  lswf=daoService.getEmpwelfaremapper().findByEid(oldemp.getEid());
		if(lswf!=null&&lswf.size()>0){
			//创建福利数组
			String[] wids=new String[lswf.size()];
			for(int i=0;i<lswf.size();i++){
				Welfare wf=lswf.get(i);
				wids[i]=wf.getWid().toString();
			}
			oldemp.setWids(wids);
		}
		*//******获取福利end*******//*
		oldemp.setLswf(lswf);//详细页面
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
