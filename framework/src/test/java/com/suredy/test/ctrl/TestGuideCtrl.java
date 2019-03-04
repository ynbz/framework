package com.suredy.test.ctrl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.core.j2ee.ContextLoader;

@Controller
@RequestMapping("/test/guide")
public class TestGuideCtrl extends BaseCtrl {

	private final static Logger log = LoggerFactory.getLogger(TestGuideCtrl.class);

	@RequestMapping(method = RequestMethod.GET)
	public String guide(Model model) {
		return "test/guide/index";
	}

	@RequestMapping(value = "/web-xml", method = RequestMethod.GET)
	@ResponseBody
	public String webXml() {
		return this.readText("WEB-INF" + File.separator + "web.xml");
	}

	@RequestMapping(value = "/springMVC-servlet-xml", method = RequestMethod.GET)
	@ResponseBody
	public String springMVCServletXml() {
		return this.readText("WEB-INF" + File.separator + "springMVC-servlet.xml");
	}

	@RequestMapping(value = "/jdbc-xml", method = RequestMethod.GET)
	@ResponseBody
	public String jdbcXml() {
		return this.readText("WEB-INF" + File.separator + "spring-jdbc.xml");
	}

	private String readText(String filePath) {
		if (StringUtils.isBlank(filePath))
			return "";

		File f = new File(System.getProperty(ContextLoader._string(WebUtils.WEB_APP_ROOT_KEY_PARAM)) + File.separator + filePath);

		if (!f.isFile())
			return "";

		StringBuilder text = new StringBuilder("");

		FileReader fr = null;

		try {
			fr = new FileReader(f);

			char[] buffer = new char[1024];
			int readed = -1;

			while ((readed = fr.read(buffer)) != -1) {
				text.append(buffer, 0, readed);
			}
		} catch (IOException e) {
			log.error("Read file content error.", e);
		} finally {
			try {
				if (fr != null)
					fr.close();
			} catch (IOException e) {
				log.error("Close file reader error.", e);
			}
		}

		return text.toString();
	}

}
