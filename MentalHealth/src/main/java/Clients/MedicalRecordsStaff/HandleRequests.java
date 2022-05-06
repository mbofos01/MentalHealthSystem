package Clients.MedicalRecordsStaff;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;

import Clients.Client;
import Objects.Patient;
import Objects.PatientRecord;
import Objects.Request;
import Objects.Transaction;
import Tools.CustomColours;
import Tools.Query;
import Tools.RequestType;
import Tools.Viewpoint;

/**
 * The class that handles each request differently according to the type.
 * 
 * @author Ioanna Theophilou
 *
 */
public class HandleRequests {
	/**
	 * the frame
	 */
	private JFrame frmRequestDescription;
	/**
	 * the content pane
	 */
	private JPanel contentPane;
	/**
	 * the table used
	 */
	private JTable table;
	/**
	 * a label used bellow
	 */
	@SuppressWarnings("unused")
	private JLabel lblNewLabel;
	/**
	 * The client
	 */
	@SuppressWarnings("unused")
	private Client client;
	/**
	 * the button used bellow
	 */
	private JButton btnNewButton;
	/**
	 * a cancel button
	 */
	private JButton cancel;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application. The constructor of this class that gets a selected
	 * request from the Request table of the Request.java table.
	 * 
	 * @param client Client object
	 * @param req    Request object
	 */
	public HandleRequests(Client client, Request req) {
		initialize(client, req);
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @param client Client object
	 * @param req    Request object
	 */
	private void initialize(Client client, Request re) {
		frmRequestDescription = new JFrame();
		frmRequestDescription.setTitle("Request Description");
		frmRequestDescription.setBounds(100, 100, 395, 467);
		frmRequestDescription.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel lblNewLabel_4 = new JLabel("       View all Requests");
		lblNewLabel_4.setFont(new Font("Segoe Script", Font.BOLD, 32));
		frmRequestDescription.getContentPane().add(lblNewLabel_4, BorderLayout.NORTH);

		contentPane = new JPanel();
		contentPane.setBackground(CustomColours.interChangableWhite());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frmRequestDescription.setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Request Description :");
		lblNewLabel_1.setFont(new Font("Segoe Script", Font.BOLD, 30));
		lblNewLabel_1.setBounds(10, 10, 459, 49);
		contentPane.add(lblNewLabel_1);
		if (re.getType().equals(RequestType.Allergies)) {

			// SHOW
			String col[] = { "Drug" };
			int index = 0;
			String data[][] = new String[1][col.length];

			data[index][0] = re.getInfoA().toString();
			index++;

			DefaultTableModel model = new DefaultTableModel(data, col);
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(33, 154, 249, 128);
			contentPane.add(scrollPane);

			table = new JTable();
			scrollPane.setViewportView(table);
			table = new JTable(model);
			scrollPane.setViewportView(table);

			btnNewButton = new JButton("Accept");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					Query insert = new Query(Viewpoint.MedicalRecords);
					insert.setFunction("changeAllAccept");
					insert.addArgument(new Gson().toJson(re.getId()));
					client.send(insert);
					/*
					 * int size = Requests.getTable().getRowCount();
					 * 
					 * for(int i=0;i< size; i++) { if(Requests.getTable().getValueAt(i,
					 * 0).equals(re.getDescr())) { Requests.getTable().remove(i); } }
					 */
					re.setAccepted(1);
					Transaction.req.add(re);
					frmRequestDescription.dispose();
					Requests.openWindow(client);
				}
			});
			btnNewButton.setForeground(CustomColours.interChangableWhite());
			btnNewButton.setBackground(CustomColours.Green());
			btnNewButton.setBounds(43, 342, 85, 49);
			contentPane.add(btnNewButton);

			cancel = new JButton("Decline");
			cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					re.setAccepted(0);
					Transaction.req.add(re);
					frmRequestDescription.dispose();

				}
			});
			cancel.setBackground(CustomColours.Red());
			cancel.setForeground(CustomColours.interChangableWhite());
			cancel.setBounds(192, 342, 85, 49);
			contentPane.add(cancel);

		} else if (re.getType().equals(RequestType.Treatment)) {

			String col[] = { "Comments" };
			int index = 0;
			String data[][] = new String[1][col.length];

			data[index][0] = re.getInfoT().toString();
			index++;

			DefaultTableModel model = new DefaultTableModel(data, col);
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(33, 154, 249, 128);
			contentPane.add(scrollPane);

			table = new JTable();
			scrollPane.setViewportView(table);
			table = new JTable(model);
			scrollPane.setViewportView(table);

			btnNewButton = new JButton("Accept");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					Query insert = new Query(Viewpoint.MedicalRecords);
					insert.setFunction("changeTreatAccept");
					insert.addArgument(new Gson().toJson(re.getId()));
					client.send(insert);

					re.setAccepted(1);
					Transaction.req.add(re);

					frmRequestDescription.dispose();
					Requests.openWindow(client);
				}
			});
			btnNewButton.setForeground(CustomColours.interChangableWhite());
			btnNewButton.setBackground(CustomColours.Green());
			btnNewButton.setBounds(43, 342, 85, 49);
			contentPane.add(btnNewButton);

			cancel = new JButton("Decline");
			cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					re.setAccepted(0);
					Transaction.req.add(re);
					frmRequestDescription.dispose();
				}
			});
			cancel.setBackground(CustomColours.Red());
			cancel.setForeground(CustomColours.interChangableWhite());
			cancel.setBounds(192, 342, 85, 49);
			contentPane.add(cancel);

		} else if (re.getType().equals(RequestType.Reccord)) {

			String col[] = { "Condition" };
			int index = 0;
			String data[][] = new String[1][col.length];

			data[index][0] = re.getInfoR().toString();
			index++;

			DefaultTableModel model = new DefaultTableModel(data, col);
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(33, 154, 249, 128);
			contentPane.add(scrollPane);

			table = new JTable();
			scrollPane.setViewportView(table);
			table = new JTable(model);
			scrollPane.setViewportView(table);

			btnNewButton = new JButton("Accept");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					Query insert = new Query(Viewpoint.MedicalRecords);
					insert.setFunction("changeRecAccept");
					insert.addArgument(new Gson().toJson(re.getId()));
					client.send(insert);
					// get record
					Query getR = new Query(Viewpoint.MedicalRecords);
					getR.setFunction("getRecordByID");
					getR.addArgument("" + re.getId());
					client.send(getR);
					PatientRecord late = new Gson().fromJson(client.read(), PatientRecord.class);
					int patient_id = late.getPatient_id();
					// get patient
					Query getP = new Query(Viewpoint.MedicalRecords);
					getP.setFunction("getPatientByID");
					getP.addArgument("" + patient_id);
					client.send(getP);
					Patient pate = new Gson().fromJson(client.read(), Patient.class);
					if (pate.isAlive() && late.isSelf_harm()) {
						String message = "Dear caregiver we regret to inform you that " + pate.getName() + " "
								+ pate.getSurname() + " has shown signs of self harm.";
						Tools.EmailService.sendEmail(pate.getEmail(), "URGENT SELF HARM", message, false);
					}
					re.setAccepted(1);
					Transaction.req.add(re);

					frmRequestDescription.dispose();
					Requests.openWindow(client);
				}
			});
			btnNewButton.setForeground(CustomColours.interChangableWhite());
			btnNewButton.setBackground(CustomColours.Green());
			btnNewButton.setBounds(43, 342, 85, 49);
			contentPane.add(btnNewButton);

			cancel = new JButton("Decline");
			cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					re.setAccepted(0);
					Transaction.req.add(re);
					frmRequestDescription.dispose();

				}
			});
			cancel.setBackground(CustomColours.Red());
			cancel.setForeground(CustomColours.interChangableWhite());
			cancel.setBounds(192, 342, 85, 49);
			contentPane.add(cancel);

		} else if (re.getType().equals(RequestType.Death)) {

			String col[] = { "Patient" };
			int index = 0;
			String data[][] = new String[1][col.length];

			data[index][0] = re.getInfoD().toString();
			index++;

			DefaultTableModel model = new DefaultTableModel(data, col);
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(33, 154, 249, 128);
			contentPane.add(scrollPane);

			table = new JTable();
			scrollPane.setViewportView(table);
			table = new JTable(model);
			scrollPane.setViewportView(table);

			btnNewButton = new JButton("Accept");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					Query insert = new Query(Viewpoint.MedicalRecords);
					insert.setFunction("changeDeath");
					insert.addArgument(new Gson().toJson(re.getId()));
					client.send(insert);

					re.setAccepted(1);
					Transaction.req.add(re);

					frmRequestDescription.dispose();
					Requests.openWindow(client);
				}
			});
			btnNewButton.setForeground(CustomColours.interChangableWhite());
			btnNewButton.setBackground(CustomColours.Green());
			btnNewButton.setBounds(43, 342, 85, 49);
			contentPane.add(btnNewButton);

			cancel = new JButton("Decline");
			cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					re.setAccepted(0);
					Transaction.req.add(re);

					frmRequestDescription.dispose();
				}
			});
			cancel.setBackground(CustomColours.Red());
			cancel.setForeground(CustomColours.interChangableWhite());
			cancel.setBounds(192, 342, 85, 49);
			contentPane.add(cancel);

		}

	}

	/**
	 * The function to open this window.
	 * 
	 * @param client Client object
	 * @param req    Request object
	 */

	public static void openWindow(Client client, Request req) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					HandleRequests window = new HandleRequests(client, req);
					window.frmRequestDescription.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
