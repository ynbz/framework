package com.suredy.report;


import java.io.File;

import com.lowagie.text.pdf.PdfWriter;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.AbstractSampleApp;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

public class PdfEncryptApp extends AbstractSampleApp {

	/**
	 *
	 */
	public static void main(String[] args) {
		main(new PdfEncryptApp(), args);
	}

	/**
	 *
	 */
	public void test() throws JRException {
		fill();
		pdf();
	}

	/**
	 *
	 */
	public void fill() throws JRException {
		long start = System.currentTimeMillis();
		JasperFillManager.fillReportToFile("build/reports/PdfEncryptReport.jasper", null, new JREmptyDataSource());
		System.err.println("Filling time : " + (System.currentTimeMillis() - start));
	}

	/**
	 *
	 */
	public void pdf() throws JRException {
		long start = System.currentTimeMillis();
		File sourceFile = new File("build/reports/PdfEncryptReport.jrprint");

		JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObject(sourceFile);

		File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".pdf");

		JRPdfExporter exporter = new JRPdfExporter();

		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(destFile));
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		configuration.setEncrypted(true);
		configuration.set128BitKey(true);
		configuration.setUserPassword("test");
		configuration.setOwnerPassword("suredy");
		configuration.setPermissions(PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING);
		exporter.setConfiguration(configuration);
		exporter.exportReport();

		System.err.println("PDF creation time : " + (System.currentTimeMillis() - start));
	}

}
