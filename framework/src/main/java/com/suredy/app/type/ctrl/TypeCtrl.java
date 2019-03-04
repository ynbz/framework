package com.suredy.app.type.ctrl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.app.type.model.Type;
import com.suredy.app.type.service.TypeSrv;
import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.core.mvc.model.MessageModel;
import com.suredy.core.mvc.model.Tree;

@Controller
@RequestMapping({"/type"})
public class TypeCtrl extends BaseCtrl{

	@Autowired
	private TypeSrv typesrv;
	
	@RequestMapping({"/tree"})
	@ResponseBody
	public Object typeTree(){
		return typesrv.getTypeTree();
	}
	
	@RequestMapping({"/list"})
	public ModelAndView typeList(){
		ModelAndView view = new ModelAndView("/app/type/type-list");
		return view;
	}
	
	@RequestMapping({"/info"})
	public ModelAndView info(String id){
		Type type;
		if(StringUtils.isEmpty(id)){
			type=new Type();
			type.setIsChildNode(2);
		}else{
			type=typesrv.getByIdEager(id);
		}
		ModelAndView view = new ModelAndView("/app/type/type-info");
		view.addObject("type",type);
		return view;
	}
	
	@RequestMapping({"/save"})
	@ResponseBody
	public Object save(Type type){
		Type t1=typesrv.getOnlyType(type.getTypeName().trim());
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
			Tree t=typesrv.createTree(type);
			List<Tree> ts=t.getChildren();
			if(ts!=null){
				for (Tree tree : ts) {
					//
					if(!jugertree(tree,type.getParent().getId()))
					return MessageModel.createSuccessMessage("msg", false);
				}	
			}
			
		}
		this.typesrv.saveOrUpdate(type);
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
	@RequestMapping({"/delete"})
	@ResponseBody
	public Object delete(String id){
		Type type = this.typesrv.get(id);
		if(type!=null){
			this.typesrv.delete(type);
		}
		return MessageModel.createSuccessMessage(null, null);
	}
	
	
	
}
