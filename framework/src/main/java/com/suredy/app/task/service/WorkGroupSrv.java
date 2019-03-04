package com.suredy.app.task.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.suredy.app.task.entity.WorkGroupEntity;
import com.suredy.core.mvc.model.Tree;
import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.core.service.OrderEntity;

@Service("WorkGroupSrv")
public class WorkGroupSrv extends BaseSrvWithEntity<WorkGroupEntity>{
	
	public List<WorkGroupEntity> getAll(Integer page, Integer size) {
		OrderEntity oe = new OrderEntity();
		oe.desc("createTime");
		WorkGroupEntity search = new WorkGroupEntity();
		List<WorkGroupEntity> pos =(List<WorkGroupEntity>) this.readPageByEntity(search, page, size, oe);
		
		return pos;
	}

	public Integer Count() {
		WorkGroupEntity search = new WorkGroupEntity();
		return this.getCountByEntity(search);
	}
	
	public List<Tree> getGroupTree(){
		List<Tree> ret = new ArrayList<Tree>();
		OrderEntity oe = new OrderEntity();
		oe.desc("createTime");
		WorkGroupEntity search = new WorkGroupEntity();
		List<WorkGroupEntity> pos = this.readByEntity(search, oe);
		if(pos!=null){
			for(WorkGroupEntity group:pos){
				Tree tree = new Tree();
				tree.setText(group.getName());
				tree.setData(group);
				ret.add(tree);
			}
		}
		return ret;
	}

}
