package com.suredy.qrcode;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Component
public class QRCodeFactory extends ZXingCodeFactory {

	public QRCodeFactory() {
		this.format = BarcodeFormat.QR_CODE;
	}

	public Map<EncodeHintType, Object> getHints(ErrorCorrectionLevel errorCorrectionLevel, String charset, Integer margin) {
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();

		hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel != null ? errorCorrectionLevel : ErrorCorrectionLevel.L);
		hints.put(EncodeHintType.CHARACTER_SET, !StringUtils.isBlank(charset) ? charset : "UTF-8");
		hints.put(EncodeHintType.DATA_MATRIX_SHAPE, SymbolShapeHint.FORCE_SQUARE);
		hints.put(EncodeHintType.MARGIN, margin != null && margin >= 0 ? margin : 0);

		return hints;
	}

}
