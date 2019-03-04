package com.suredy.flow.manager.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.core.mvc.model.Tree;
import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.core.service.OrderEntity;
import com.suredy.flow.manager.entity.FlowEntity;
@Service("FlowManagerSrv")
public class FlowManagerSrv extends BaseSrvWithEntity<FlowEntity> {

	@Override
	public DetachedCriteria getDc(FlowEntity t) {
		DetachedCriteria dc = super.getDc(t);

		if (t == null)
			return dc;

		if (t.getCode() != null) {
			dc.add(Restrictions.eq("code", t.getCode()));
		}

		return dc;
	}
	
	public List<Tree> getFlTree(){
		List<Tree> ret = new ArrayList<Tree>();
		String ql = "SELECT T FROM FlowEntity T ";
		List<FlowEntity> fls=(List<FlowEntity>)this.readByQL(ql);
		if(fls!=null){
			for(FlowEntity f:fls){
				Tree tree = new Tree();
				tree.setText(f.getName());
				tree.setData(f.getId());
				ret.add(tree);
			}
		}
		return ret;
	}
	
	public FlowEntity getById(String dicid) {
		if (StringUtils.isBlank(dicid)) {
			return null;
		}
		FlowEntity po = this.get(dicid);
		return po;
	}
	
	public FlowEntity getOnlyCode(String code) {
		FlowEntity t=new FlowEntity();
		t.setCode(code);
		FlowEntity t1=this.readSingleByEntity(t);
		return t1;
	}

	public List<FlowEntity> getAll(int page, int size) {
		FlowEntity search = new FlowEntity();
		OrderEntity order = new OrderEntity();
		order.add("createtime", false);
		List<FlowEntity> pos = this.readPageByEntity(search, page, size, order);
		
		return pos;
	}
	
	public List<FlowEntity> getAll() {
		FlowEntity search = new FlowEntity();
		List<FlowEntity> pos = this.readByEntity(search);
		return pos;
	}

	public Long Count() {
		String ql = "";
		Object ret = null;
		ql = "SELECT COUNT(*) FROM FlowEntity T";
		ret = this.readSingleByQL(ql);

		return (Long) ret;
	}

}
