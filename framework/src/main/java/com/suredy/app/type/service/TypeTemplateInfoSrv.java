package com.suredy.app.type.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.app.type.model.Type;
import com.suredy.app.type.model.TypeTemplateInfo;
import com.suredy.core.service.BaseSrvWithEntity;

@Service
public class TypeTemplateInfoSrv extends BaseSrvWithEntity<TypeTemplateInfo>{
	
	@Override
	public DetachedCriteria getDc(TypeTemplateInfo t) {
		DetachedCriteria dc = super.getDc(t);

		if (t == null) {
			return dc;
		}
		if(t.getType() != null) {
			dc.add(Restrictions.eqOrIsNull("type", t.getType()));
		}
		return dc;
		}
	
	public TypeTemplateInfo gettemplateinfo(String typeId) {
		Type t=new Type();
		t.setId(typeId);
		TypeTemplateInfo tti=new TypeTemplateInfo();
		tti.setType(t);
		
		return this.readSingleByEntity(tti);
	}


}
