package com.suredy.app.consumable.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.app.consumable.model.ConsumableTemplateInfo;
import com.suredy.app.consumable.model.ConsumerType;
import com.suredy.app.type.model.Type;
import com.suredy.app.type.model.TypeTemplateInfo;
import com.suredy.core.service.BaseSrvWithEntity;

@Service
public class ConsumTemplateInfoSrv extends BaseSrvWithEntity<ConsumableTemplateInfo>{
	
	@Override
	public DetachedCriteria getDc(ConsumableTemplateInfo t) {
		DetachedCriteria dc = super.getDc(t);

		if (t == null) {
			return dc;
		}
		if(t.getType() != null) {
			dc.add(Restrictions.eqOrIsNull("type", t.getType()));
		}
		return dc;
		}
	
	public ConsumableTemplateInfo gettemplateinfo(String typeId) {
		ConsumerType t=new ConsumerType();
		t.setId(typeId);
		ConsumableTemplateInfo tti=new ConsumableTemplateInfo();
		tti.setType(t);
		
		return this.readSingleByEntity(tti);
	}


}
