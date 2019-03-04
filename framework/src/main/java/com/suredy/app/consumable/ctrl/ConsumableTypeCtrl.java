package com.suredy.app.consumable.ctrl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.app.consumable.model.ConsumableAndEquipType;
import com.suredy.app.consumable.model.ConsumerType;
import com.suredy.app.consumable.service.ConAndEquipSrv;
import com.suredy.app.consumable.service.ConsumerTypeService;
import com.suredy.core.mvc.model.MessageModel;
import com.suredy.core.mvc.model.Tree;

@Controller
@RequestMapping(value="/ConsumableTypeCtrl")
public class ConsumableTypeCtrl {
	
	@Autowired
	private ConsumerTypeService consumerTypeSrv;
	@Autowired
	private ConAndEquipSrv conAndEquipSrv;
	
	@RequestMapping(value="/getConsumableTypeList")
	@ResponseBody
	public ModelAndView getConsumableTypeList(){		
		ModelAndView view=new ModelAndView("/app/consumableType/consumableType-list");		
		return view;
		
	}
	@RequestMapping({"/Consumabletree"})
	@ResponseBody
	public Object Consumabletree(){
		return consumerTypeSrv.getConsumerTypeTree();
	}
	@RequestMapping({"/info"})
	public ModelAndView info(String id){
		ConsumerType type;
		if(StringUtils.isEmpty(id)){
			type=new ConsumerType();
			type.setIsChildNode(2);
		}else{
			type=consumerTypeSrv.getByIdData(id);
			type.setResourcename(conAndEquipSrv.getEquipmentTypeName(id));
			type.setResourceid(conAndEquipSrv.getEquipmentTypeID(id));
		}
		ModelAndView view = new ModelAndView("/app/consumableType/consumableType-info");
		view.addObject("type",type);
		return view;
	}
	
	@RequestMapping({"/save"})
	@ResponseBody
	public Object save(ConsumerType type){
		ConsumerType t1=consumerTypeSrv.getOnlyType(type.getConsumerName().trim());
		if(t1!=null){
			if(type.getId()==null){
				return MessageModel.createSuccessMessage("name","名字重复！");
			}else if(!t1.getId().equals(type.getId())){
				return MessageModel.createSuccessMessage("name","名字重复！");
			}
		}
			
		if(StringUtils.isEmpty(type.getParent().getId())){
			type.setParent(null);
		}else{
			Tree t=consumerTypeSrv.createTree(type);
			List<Tree> ts=t.getChildren();
			if(ts!=null){
				for (Tree tree : ts) {
					//
					if(!jugertree(tree,type.getParent().getId()))
					return MessageModel.createSuccessMessage("msg", false);
				}	
			}
			
		}
		if(type.getId()!=null&&StringUtils.isEmpty(type.getId())){
			type.setId(null);
			}
		String resourceid=type.getResourceid();
		ConsumerType contype=this.consumerTypeSrv.saveOrUpdate(type);
		this.conAndEquipSrv.deleteData(type.getId());
		if(!StringUtils.isEmpty(resourceid)){						
			String[] resourceids=resourceid.split(",");
		
			for (String s : resourceids) {
				ConsumableAndEquipType cet=new ConsumableAndEquipType();
				cet.setConsumableID(contype.getId());
				cet.setEquipTypeID(s);
				this.conAndEquipSrv.saveOrUpdate(cet);
			}
		}
		
		return MessageModel.createSuccessMessage(null, null);
	}
	
	public boolean jugertree(Tree tree,String id){
		if(tree.getData().equals(id)) return false;
		if(tree.getChildren()!=null)
			for (Tree trr : tree.getChildren()) {
				if(!jugertree(trr,id))
				return jugertree(trr,id);
			}		
		return true;		
	}
	
	
	@RequestMapping({"/list"})
	public ModelAndView consumerTypeList(){
		ModelAndView view = new ModelAndView("/app/consumableType/consumableType-list");
		return view;
	}
	
	@RequestMapping({"/delete"})
	@ResponseBody
	public Object delete(String id){
		ConsumerType type = this.consumerTypeSrv.get(id);
		if(type!=null){
			this.consumerTypeSrv.delete(type);			
			this.conAndEquipSrv.deleteData(type.getId());
		}
		return MessageModel.createSuccessMessage(null, null);
	}
}
