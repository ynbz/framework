package com.suredy.security.service;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.core.mvc.model.Tree;
import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.security.model.TemplateManage;
import com.suredy.security.model.TemplateType;
@Service
public class TemplateManageSrv extends BaseSrvWithEntity<TemplateManage>{
	
	@Override
	public DetachedCriteria getDc(TemplateManage t) {
		DetachedCriteria dc = super.getDc(t);

		if (t == null) {
			return dc;
		}
		if (!StringUtils.isBlank(t.getId())) {
			dc.add(Restrictions.eq("id", t.getId()));
		}
		
		if (t.getTypes() != null && !t.getTypes().isEmpty()) {
			dc.add(Restrictions.in("type", t.getTypes()));
		}
		return dc;
	}

	/**
	 * 根据模板类型id得到树形模板数据
	 * @param typeId 类型id
	 * @return
	 */
	public List<Tree> getTemplateTree(String typeId){
		List<Tree> ret = new ArrayList<Tree>();
		String sql="SELECT T.templateTitel,T.templateUrlId FROM tb_template_manage T WHERE T.type_id=?";
		List listdata=this.readBySQL(sql,typeId);
		for(int i=0;i<listdata.size();i++){
			Object object=listdata.get(i);
			Tree tree = new Tree();
			tree.setText(((Object[])object)[0].toString());
			tree.setData(((Object[])object)[1].toString());
			ret.add(tree);
		}
		return ret;
	}
	
	public List<TemplateManage> getFmAll(int page, int size,TemplateType type) {	
		
		TemplateManage search = new TemplateManage();
		DetachedCriteria dc = this.getDc(search);
		if(type!=null){
			dc.add(Restrictions.eq("type",type));
		}
		// TODO:
		@SuppressWarnings("unchecked")
		List<TemplateManage> pos =(List<TemplateManage>) this.readPageByCriteria(dc, page, size);
		
		return pos == null?null:pos;
	}
	
	public int count(TemplateType type) {	
		TemplateManage search = new TemplateManage();
		DetachedCriteria dc = this.getDc(search);
		if(type!=null){
			dc.add(Restrictions.eq("type",type));
		}
		// TODO:
		@SuppressWarnings("unchecked")
		List<TemplateManage> pos =(List<TemplateManage>) this.readByCriteria(dc);
		return pos == null?null:pos.size();
	}
	
	public Integer count(List<TemplateType> types) {

		TemplateManage search = new TemplateManage();
		if (types != null && !types.isEmpty()) {
			search.setTypes(types);
		}
		int ret = this.getCountByEntity(search);
		return ret;
	}

}
