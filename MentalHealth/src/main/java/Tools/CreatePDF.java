package Tools;

import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import Objects.ReportData;

public class CreatePDF {

	public static String tab = "                                                              ";
	public static String space = "                 ";

	public static void createReport(ReportData data) {
		Document doc = new Document();
		try {

			Paragraph doted_line = new Paragraph(
					"---------------------------------------------------------------------------------------------------------------------");

			// generate a PDF at the specified location
			PdfWriter writer = PdfWriter.getInstance(doc,
					new FileOutputStream("Report " + data.getWeek() + " for " + data.getClinic() + ".pdf"));
			// opens the PDF
			doc.open();
			// adding paragraphs to the PDF

			doc.add(new Paragraph("Report " + data.getWeek() + " for " + data.getClinic() + " Clinic"));
			doc.add(new Paragraph("                                       "));
			doc.add(doted_line);
			for (int i = 0; i < 7; i++) {
				if (data.getVisitors()[i] < 9)
					doc.add(new Paragraph("                 " + data.getDates()[i] + space + tab + " "
							+ data.getVisitors()[i] + " visitors"));
				else
					doc.add(new Paragraph("                 " + data.getDates()[i] + space + tab + data.getVisitors()[i]
							+ " visitors"));
			}
			doc.add(doted_line);
			doc.add(new Paragraph("Total visitors for the week : " + data.getTotal_visitors()));
			doc.add(doted_line);
			doc.add(new Paragraph("Cases per condition"));
			doc.add(doted_line);
			doc.add(doted_line);
			doc.add(new Paragraph("Prescriptions per drug"));
			doc.add(doted_line);
			doc.add(doted_line);

			doc.close();
			writer.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {

		ReportData rep = new ReportData();
		rep.setClinic("Rosewood");
		rep.setDates(Clock.getLastWeek());
		int[] vis = { 1, 2, 3, 4, 50, 6, 7 };
		rep.setVisitors(vis);
		createReport(rep);

	}
}