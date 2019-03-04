package com.suredy.app.consumable.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.app.consumable.model.ConsumerType;
import com.suredy.core.mvc.model.Tree;
import com.suredy.core.service.BaseSrvWithEntity;

@Service
public class ConsumerTypeService extends BaseSrvWithEntity<ConsumerType>{
	@Override
	public DetachedCriteria getDc(ConsumerType t) {
		DetachedCriteria dc = super.getDc(t);
		if (t == null) {
			return dc;
		}
		if (t.getConsumerName() != null) {
			dc.add(Restrictions.eqOrIsNull("consumerName", t.getConsumerName()));
		}
		return dc;		
		}
	public List<Tree> getConsumerTypeTree(){
		List<Tree> ret=new ArrayList<Tree>();
		String ql = "SELECT T FROM ConsumerType T where T.parent is null";
		List<ConsumerType> consumerType=(List<ConsumerType>)this.readByQL(ql);
		if(consumerType!=null){
			for(ConsumerType t:consumerType){
				ret.add(createTree(t));
			}
		}
		return ret;	
	}

	public Tree createTree(ConsumerType consumerType){
		Tree tree = new Tree();
		tree.setText(consumerType.getConsumerName());
		tree.setData(consumerType.getId());
		String ql = "SELECT T FROM ConsumerType T where T.parent.id=?";
		List<ConsumerType> list = (List<ConsumerType>)this.readByQL(ql, consumerType.getId());
		for(ConsumerType t:list){
			if(tree.getChildren()==null||tree.getChildren().isEmpty())
				tree.setChildren(new ArrayList<Tree>());
				tree.getChildren().add(createTree(t));
		}
		return tree;
	}

	public ConsumerType getByIdData(String id) {
		ConsumerType t = this.get(id);
		if(t.getParent()!=null)
			t.getParent().getId();
		return t;
	}

	public ConsumerType getOnlyType(String typeName) {
		ConsumerType t=new ConsumerType();
			t.setConsumerName(typeName);
			ConsumerType t1=this.readSingleByEntity(t);
			return t1;
		}
	public List<Tree> getconsumableTypeByEquipTypeid(String equipTypeid) {
		String sql="select consumableID,consumerName,parentId from  t_app_consumerandequiptype  ce"
				+" join t_app_consumtype ct  on ce.consumableID= ct.id"
				+" where equipTypeID='"+equipTypeid+"' ";
		List listdata=this.readBySQL(sql);
		List<Tree> ret=new ArrayList<Tree>();
		List<ConsumerType> listconsum=new ArrayList<ConsumerType>();
		for(int i=0;i<listdata.size();i++){
			Object object=listdata.get(i);
			ConsumerType ct=new ConsumerType();
			ct.setId(((Object[])object)[0]==null?"":((Object[])object)[0].toString());
			ct.setConsumerName(((Object[])object)[1]==null?"":((Object[])object)[1].toString());
			listconsum.add(ct);
		}
		if(listconsum!=null){
			for(ConsumerType t:listconsum){
				ret.add(createTree(t));
			}
		}
		return ret;
	}	
}
