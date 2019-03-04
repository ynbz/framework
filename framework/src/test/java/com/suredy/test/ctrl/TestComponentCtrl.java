package com.suredy.test.ctrl;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.core.mvc.model.TreeMenu;

@Controller
@RequestMapping("/test/component")
public class TestComponentCtrl extends BaseCtrl {

	private ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		return "test/component";
	}

	@RequestMapping(value = "/tree-menu", method = RequestMethod.GET)
	public String treeMenu(Model model) throws JsonProcessingException {
		model.addAttribute("data", mapper.writeValueAsString(this.createTreeMenuData()));
		return "test/component/tree-menu";
	}

	@RequestMapping(value = "/tree-selector", method = RequestMethod.GET)
	public String treeSelecotr(Model model) throws JsonProcessingException {
		return "test/component/tree-selector";
	}

	@RequestMapping(value = "/tree-selector-for-modal", method = RequestMethod.GET)
	public String treeSelecotrForModal(Model model) throws JsonProcessingException {
		return "test/component/tree-selector-for-modal";
	}

	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public String tree(Model model) {
		return "test/component/tree";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		return "test/component/list";
	}

	@RequestMapping(value = "/modal", method = RequestMethod.GET)
	public String modal(Model model) {
		return "test/component/modal";
	}

	@RequestMapping(value = "/datetime-picker", method = RequestMethod.GET)
	public String datetimePicker(Model model) {
		return "test/component/datetime-picker";
	}

	@RequestMapping(value = "/validation", method = RequestMethod.GET)
	public String validation(Model model) {
		return "test/component/validation";
	}

	@RequestMapping(value = "/qrcode", method = RequestMethod.GET)
	public String qrcode(Model model) {
		return "test/component/qrcode";
	}

	private Object createTreeMenuData() {
		TreeMenu data = new TreeMenu();

		data.setText("Node1");
		data.setChildren(new ArrayList<TreeMenu>());

		TreeMenu child = new TreeMenu();
		child.setText("Node2");
		data.getChildren().add(child);

		child = new TreeMenu();
		child.setText("Node3");
		data.getChildren().add(child);

		child = new TreeMenu();
		child.setText("Node4");
		data.getChildren().add(child);

		child = new TreeMenu();
		child.setText("Node5");
		data.getChildren().add(child);

		return data;
	}

}
