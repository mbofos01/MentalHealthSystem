package Tools;

import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import Objects.ReportData;

/**
 * This tool is used to create a PDF report for the health service management
 * viewpoint.
 * 
 * @author Michail Panagiotis Bofos
 * @author Demetra Hadjicosti
 * @author Ioanna Theofilou
 * @author Lucía Jiménez García
 */
public class CreatePDF {

	private static String tab = "                                                              ";
	private static String space = "                 ";

	/**
	 * This function creates a PDF report, based on the ReportData object passed as
	 * an argument.
	 * 
	 * @param data ReportData all the data that are used to create the report
	 */
	public static void createReport(ReportData data) {
		Document doc = new Document();
		try {

			Paragraph doted_line = new Paragraph(
					"-------------------------------------------------------------------------------------------------------------------------------");

			// generate a PDF at the specified location
			PdfWriter writer = PdfWriter.getInstance(doc,
					new FileOutputStream("Report " + data.getWeek() + " for " + data.getClinic().getName() + ".pdf"));
			// opens the PDF
			doc.open();
			// adding paragraphs to the PDF

			doc.add(new Paragraph("Report " + data.getWeek() + " for " + data.getClinic().getName() + " Clinic"));
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
			doc.add(new Paragraph(space));
			for (Counter count : data.getConditionCounter()) {
				// Create Table object, Here 4 specify the no. of columns
				PdfPTable pdfPTable = new PdfPTable(2);

				// Create cells
				PdfPCell pdfPCell1 = new PdfPCell(new Paragraph(count.getName()));
				PdfPCell pdfPCell2 = new PdfPCell(new Paragraph(count.getValue() + " "));

				// Add cells to table
				pdfPTable.addCell(pdfPCell1);
				pdfPTable.addCell(pdfPCell2);

				// Add content to the document using Table objects.
				doc.add(pdfPTable);
			}
			doc.add(new Paragraph(space));
			doc.add(doted_line);
			doc.add(new Paragraph("Prescriptions per drug"));
			doc.add(doted_line);
			doc.add(new Paragraph(space));
			for (Counter count : data.getTreatmentCounter()) {
				// Create Table object, Here 4 specify the no. of columns
				PdfPTable pdfPTable = new PdfPTable(2);

				// Create cells
				PdfPCell pdfPCell1 = new PdfPCell(new Paragraph(count.getName()));
				PdfPCell pdfPCell2 = new PdfPCell(new Paragraph(count.getValue() + " "));

				// Add cells to table
				pdfPTable.addCell(pdfPCell1);
				pdfPTable.addCell(pdfPCell2);

				// Add content to the document using Table objects.
				doc.add(pdfPTable);
			}
			doc.add(new Paragraph(space));
			doc.add(doted_line);

			doc.close();
			writer.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}