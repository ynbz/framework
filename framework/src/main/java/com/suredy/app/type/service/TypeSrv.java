package com.suredy.app.type.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.app.type.model.Type;
import com.suredy.core.mvc.model.Tree;
import com.suredy.core.service.BaseSrvWithEntity;


@Service
public class TypeSrv extends BaseSrvWithEntity<Type>{

	@Override
	public DetachedCriteria getDc(Type t) {
		DetachedCriteria dc = super.getDc(t);
		if (t == null) {
			return dc;
		}
		if (t.getTypeName() != null) {
			dc.add(Restrictions.eqOrIsNull("typeName", t.getTypeName()));
		}
		return dc;		
		}
	public List<Tree> getTypeTree(){
		List<Tree> ret = new ArrayList<Tree>();
		String ql = "SELECT T FROM Type T where T.parent is null";
		List<Type> type=(List<Type>)this.readByQL(ql);
		if(type!=null){
			for(Type t:type){
				ret.add(createTree(t));
			}
		}
		return ret;
	}
	
	public Tree createTree(Type type){
		Tree tree = new Tree();
		tree.setText(type.getTypeName());
		tree.setData(type.getId());
		String ql = "SELECT T FROM Type T where T.parent.id=?";
		List<Type> list = (List<Type>)this.readByQL(ql, type.getId());
		for(Type t:list){
			if(tree.getChildren()==null||tree.getChildren().isEmpty())
				tree.setChildren(new ArrayList<Tree>());
			tree.getChildren().add(createTree(t));
		}
		return tree;
	}
	
	@Transactional
	public Type getByIdEager(String id){
		Type t = this.get(id);
		if(t.getParent()!=null)
			t.getParent().getId();
		return t;
	}

	public List<Type> getAllType() {
		Type t=new Type();
		return this.readByEntity(t);
	}
	
	public Type getOnlyType(String name) {
		Type t=new Type();
		t.setTypeName(name);
		Type t1=this.readSingleByEntity(t);
		return t1;
	}
	
	/**
	 * 
	 * @return
	 * 
	 * 返回有需求项的设备分类
	 */
	public List<Tree> getEquipTypeBeNeed() {
		
		List<Tree> ts=new ArrayList<Tree>();		
		String ql = "SELECT T FROM Type T where T.parent is null";
		List<Type> type=(List<Type>)this.readByQL(ql);
		List<Type> temptype=new ArrayList<Type>();
		if(type!=null){
			for(Type t:type){
				if(jugerChildNode(t)){
					temptype.add(t);
				}
			}
		}
		if(temptype!=null){
			for(Type tp:temptype){
				Tree t1=equipTypeCreateTree(tp);
				if(t1!=null){
					ts.add(t1);
				}
				
			}
		}	
		return ts;
	}
	
	/**
	 * 
	 * @return
	 *	返回有待分配的设备的类型
	 */
	public List<Tree> getEquipTypeBeBeiyong() {
		
			List<Tree> ts=new ArrayList<Tree>();			
			String ql = "SELECT T FROM Type T where T.parent is null";
			List<Type> type=(List<Type>)this.readByQL(ql);
			List<Type> temptype=new ArrayList<Type>();
			if(type!=null){
				for(Type t:type){
					if(jugerChildNodeBY(t)){
						temptype.add(t);
					}
				}
			}
			if(temptype!=null){
				for(Type tp:temptype){
					Tree t1=createTreeBeBeiyong(tp);
					if(t1!=null){
						ts.add(t1);
					}
					
				}
			}	
			return ts;
	}
	
	/*****************************处理需求项树************************************/
	public Tree equipTypeCreateTree(Type type){
		Tree tree = new Tree();
			tree.setText(type.getTypeName());
			tree.setData(type.getId());
			tree=noParentId(type,tree);
		return tree;
		
	}
	
	
	public Tree noParentId(Type type,Tree tree){
			Tree treenew = new Tree();
			Map<String,String> map=new HashMap<String,String>();
			map.put("id",type.getId());
			map.put("hint",type.getHint());
			treenew.setData(map);
			treenew.setText(type.getTypeName());		
			String ql = "SELECT T FROM Type T where T.parent.id=?";
			List<Type> listChilds = (List<Type>)this.readByQL(ql, type.getId());
			for(Type t:listChilds){
				if(treenew.getChildren()==null||treenew.getChildren().isEmpty())
					treenew.setChildren(new ArrayList<Tree>());
				//判断子节点有没有需求项
				if(jugerChildNode(t)){
					treenew.getChildren().add(noParentId(t,tree));
				}		
			}
			return treenew;
		
	}
	//判断子节点和本身是否有需求项
	public  Boolean jugerChildNode(Type type){
		Boolean flg=false;
		String sql="SELECT tp.hint,tp.id,tp.isChildNode,tp.parentId,tp.sort,tp.typeName"
				+" FROM t_app_type tp"
				+" LEFT JOIN t_app_classifyproperty tc ON tp.id = tc.typeid"
				+" where tc.isNeed=1 and tc.typeid='"+type.getId()+"'"
				+" GROUP BY tp.hint,tp.id,tp.isChildNode,tp.parentId,tp.sort,tp.typeName";
		List list=this.readBySQL(sql);
		
		if(list.size()>0){
			flg=true;
			return flg;
		}else{
			String ql = "SELECT T FROM Type T where T.parent.id=?";
			List<Type> typelist = (List<Type>)this.readByQL(ql, type.getId());
			for(Type t:typelist){
				return	jugerChildNode(t);
			}
		}
		return flg;
		}
	
	/****************************处理有待分配数据的树************************************/
	
	public Tree createTreeBeBeiyong(Type type){
		Tree tree = new Tree();
			tree.setText(type.getTypeName());
			tree.setData(type.getId());
			tree=creatTreeParentId(type,tree);
		return tree;
		
	}
	public Tree creatTreeParentId(Type type,Tree tree){
				Tree treenew = new Tree();
				Map<String,String> map=new HashMap<String,String>();
				map.put("id",type.getId());
				map.put("hint",type.getHint());
				treenew.setData(map);
				treenew.setText(type.getTypeName());		
				String ql = "SELECT T FROM Type T where T.parent.id=?";
				List<Type> listChilds = (List<Type>)this.readByQL(ql, type.getId());
				for(Type t:listChilds){
					if(treenew.getChildren()==null||treenew.getChildren().isEmpty())
						treenew.setChildren(new ArrayList<Tree>());
					//判断子节点有没有待分配设备
					if(jugerChildNodeBY(t)){
						treenew.getChildren().add(creatTreeParentId(t,tree));
					}		
				}
		return treenew;
	
	}
	//判断子节点是否有待分配设备
		public  Boolean jugerChildNodeBY(Type type){
			Boolean flg=false;
			String sql="SELECT tp.hint,tp.id,tp.isChildNode,tp.parentId,tp.sort,tp.typeName"
					+" FROM t_app_type tp "
					+"	LEFT JOIN t_app_equipasset te on tp.id=te.assetType"
					+" LEFT JOIN t_app_classifyproperty tc ON tp.id = tc.typeid"
					+" where te.`status`='1' and te.assetType='"+type.getId()+"' and tc.isNeed=1"
					+" GROUP BY tp.hint,tp.id,tp.isChildNode,tp.parentId,tp.sort,tp.typeName";
			
			List list=this.readBySQL(sql);
			if(list.size()>0){
				flg=true;
				return flg;
			}else{
				String ql = "SELECT T FROM Type T where T.parent.id=?";
				List<Type> listChilds = (List<Type>)this.readByQL(ql, type.getId());
				for(Type t:listChilds){
					return	jugerChildNodeBY(t);
				}
			}
			return flg;
			}
}
