package com.suredy.test.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.suredy.core.ctrl.BaseCtrl;

@Controller
@RequestMapping("/test/chart")
public class TestChartCtrl extends BaseCtrl {

	@RequestMapping(value = "/echarts-charts", method = RequestMethod.GET)
	public String eCharts(Model model) {
		return "test/chart/echarts-index1";
	}
	
	@RequestMapping(value = "/echarts-charts-dmic1", method = RequestMethod.GET)
	public String eChartsDmic1(Model model) {
		return "test/chart/echarts-index2";
	}
	
	@RequestMapping(value = "/echarts-charts-dmic2", method = RequestMethod.GET)
	public String eChartsDmic2(Model model) {
		return "test/chart/echarts-index3";
	}
	
	@RequestMapping(value = "/echarts-charts-dmic4", method = RequestMethod.GET)
	public String eChartsDmic4(Model model) {
		return "test/chart/echarts-index5";
	}
	
	
	@RequestMapping(value = "/echarts-charts-dmic6", method = RequestMethod.GET)
	public String eChartsDmic6(Model model) {
		return "test/chart/echarts-index7";
	}
	
	@RequestMapping(value = "/echarts-charts-dmic7", method = RequestMethod.GET)
	public String eChartsDmic7(Model model) {
		return "test/chart/echarts-index8";
	}
	
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String testOfficeShell(Model model) {
		return "test/chart/test-OfficeShell";
	}
	

}
