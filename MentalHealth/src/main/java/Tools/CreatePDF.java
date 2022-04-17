package Tools;

import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import Objects.ReportData;

public class CreatePDF {
	public static String[] days = { "Monday", "Tuesday", "Wendnesday", "Thursday", "Friday", "Saturday", "Sunday" };
	public static String[] spaces = { "            ", "           ", "    ", "          ", "               ",
			"           ", "             " };
	public static String tab = "                                                              ";

	public static void createReport(ReportData data) {
		Document doc = new Document();
		try {

			Paragraph doted_line = new Paragraph(
					"---------------------------------------------------------------------------------------------------------------------");

			// generate a PDF at the specified location
			PdfWriter writer = PdfWriter.getInstance(doc,
					new FileOutputStream("Report-" + data.getWeek() + "-" + data.getClinic() + ".pdf"));
			// opens the PDF
			doc.open();
			// adding paragraphs to the PDF

			doc.add(new Paragraph("Report for week " + data.getWeek() + " for " + data.getClinic() + " Clinic"));
			doc.add(new Paragraph("                                       "));
			doc.add(doted_line);
			for (int i = 0; i < 7; i++) {
				doc.add(new Paragraph(
						"                 " + days[i] + spaces[i] + tab + data.getVisitors()[i] + " visitors"));
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
		rep.setWeek("2nd");
		rep.setClinic("Rosewood");
		int[] vis = { 1, 2, 3, 4, 5, 6, 7 };
		rep.setVisitors(vis);
		createReport(rep);

	}
}