package com.suredy.test.ctrl;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.qrcode.ImgFileFormat;
import com.suredy.qrcode.QRCodeFactory;

@Controller
@RequestMapping("/test/component")
public class QRCodeCtrl extends BaseCtrl {

	@Autowired
	private QRCodeFactory factory;

	@RequestMapping(value = "/img-qrcode", produces = "text/html;charset=UTF-8")
	public void getImg(String content, ErrorCorrectionLevel level, String charset, Integer margin, Integer width, String front, String background, HttpServletResponse rsp) throws WriterException, IOException {
		if (StringUtils.isBlank(content))
			content = "扫出来了？但是你没有告诉我你要放什么文字在里面。（这些话是我对你说的）";

		Map<EncodeHintType, Object> hints = factory.getHints(level, charset, margin);

		BitMatrix matrix = factory.getBitMatrix(content, width, hints);

		int f = Long.decode("#FF" + front).intValue();
		int b = Long.decode("#FF" + background).intValue();

		factory.writeToStream(matrix, ImgFileFormat.JPEG, rsp.getOutputStream(), f, b);
	}

}
