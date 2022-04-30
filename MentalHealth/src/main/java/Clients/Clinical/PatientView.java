package Clients.Clinical;

import java.awt.EventQueue;

import javax.swing.JFrame;

import Objects.Condition;
import Objects.Doctor;
import Objects.Drug;
import Objects.Patient;
import Objects.PatientRecord;
import Tools.CustomColours;
import Tools.Query;
import Tools.RecordReport;
import Tools.Viewpoint;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.border.Border;

import com.google.gson.Gson;

import Clients.Client;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import java.awt.Color;

public class PatientView {

	private JFrame frmPatientView;
	private PatientRecord last = new PatientRecord();

	/**
	 * Launch the application.
	 */
	public static void openWindow(Client client, Doctor doctor, Patient patient, ArrayList<Drug> drugs) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PatientView window = new PatientView(client, doctor, patient, drugs);
					window.frmPatientView.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PatientView(Client client, Doctor doctor, Patient patient, ArrayList<Drug> drugs) {
		initialize(client, doctor, patient, drugs);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Client client, Doctor doctor, Patient patient, ArrayList<Drug> drugs) {
		ArrayList<PatientRecord> patient_records = new ArrayList<>();

		Query q = new Query(Viewpoint.Clinical);
		q.setFunction("getPatientRecords");
		q.addArgument("" + patient.getPatient_id());
		q.addArgument("" + doctor.getId());
		client.send(q);

		Integer size = new Gson().fromJson(client.read(), Integer.class);
		System.out.println(size);
		for (int i = 0; i < size; i++)
			patient_records.add(new Gson().fromJson(client.read(), PatientRecord.class));

		;
		last.setSelf_harm(false);
		if (patient_records.size() != 0)
			last = patient_records.get(patient_records.size() - 1);

		frmPatientView = new JFrame();

		frmPatientView.getContentPane().setBackground(Tools.CustomColours.interChangableWhite());
		frmPatientView.setTitle("Patient View - " + patient.getName() + " " + patient.getSurname());
		frmPatientView.setResizable(false);
		frmPatientView.setBounds(100, 100, 1129, 521);
		frmPatientView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmPatientView.getContentPane().setLayout(null);

		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmPatientView.dispose();

			}
		});
		backButton.setBackground(Tools.CustomColours.Red());
		backButton.setForeground(Tools.CustomColours.interChangableWhite());
		backButton.setBounds(10, 20, 85, 27);
		frmPatientView.getContentPane().add(backButton);

		JLabel patientName = new JLabel(patient.getName() + " " + patient.getSurname());
		patientName.setHorizontalAlignment(SwingConstants.CENTER);

		if (last.isThreat())
			patientName.setForeground(Tools.CustomColours.Red());
		else
			patientName.setForeground(Tools.CustomColours.interChangableBlack());
		patientName.setFont(new Font("Tahoma", Font.PLAIN, 25));
		patientName.setBounds(397, 21, 319, 37);
		frmPatientView.getContentPane().add(patientName);

		JTextPane commentsText = new JTextPane();
		// create a line border with the specified color and width
		Border border = BorderFactory.createLineBorder(CustomColours.interChangableBlack(), 1);

		// set the border of this component
		commentsText.setBorder(border);
		commentsText.setBackground(CustomColours.White());
		commentsText.setBounds(294, 218, 524, 174);
		frmPatientView.getContentPane().add(commentsText);

		JButton addComment = new JButton("Add Comment");
		addComment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(commentsText.getText());
				if (commentsText.getText().length() > 0) {
					Query q = new Query(Viewpoint.Clinical);
					q.setFunction("addComment");
					q.addArgument("" + doctor.getClinic_id());
					q.addArgument("" + patient.getPatient_id());
					q.addArgument(commentsText.getText());
					client.send(q);
					String answer = client.read();
					System.out.println(answer);
					if (answer.equals("SUCCESS")) {
						JOptionPane.showMessageDialog(null, "          Comment Submitted!" + '\n', "Submitted Comment",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "          Something went wrong!" + '\n', "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				commentsText.setText("");
			}
		});
		addComment.setBackground(CustomColours.Mint());
		addComment.setForeground(CustomColours.interChangableWhite());
		addComment.setBounds(664, 409, 133, 23);
		frmPatientView.getContentPane().add(addComment);

		JButton ViewComments = new JButton("View Comments");
		ViewComments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CommentList.openWindow(client, doctor, patient);
			}
		});
		ViewComments.setForeground(CustomColours.interChangableWhite());
		ViewComments.setBackground(CustomColours.Yellow());
		ViewComments.setBounds(304, 409, 140, 23);
		frmPatientView.getContentPane().add(ViewComments);

		JLabel name = new JLabel("Name: " + patient.getName());
		name.setForeground(CustomColours.interChangableBlack());
		name.setFont(new Font("Tahoma", Font.PLAIN, 16));
		name.setBounds(10, 129, 176, 27);
		frmPatientView.getContentPane().add(name);

		JLabel surname = new JLabel("Surname: " + patient.getSurname());
		surname.setForeground(CustomColours.interChangableBlack());
		surname.setFont(new Font("Tahoma", Font.PLAIN, 16));
		surname.setBounds(10, 167, 176, 27);
		frmPatientView.getContentPane().add(surname);

		JLabel birthday = new JLabel("Birthday: " + patient.getDate());
		birthday.setForeground(CustomColours.interChangableBlack());
		birthday.setFont(new Font("Tahoma", Font.PLAIN, 16));
		birthday.setBounds(10, 205, 176, 27);
		frmPatientView.getContentPane().add(birthday);

		JLabel email = new JLabel("Email: " + patient.getEmail());
		email.setForeground(CustomColours.interChangableBlack());
		email.setFont(new Font("Tahoma", Font.PLAIN, 16));
		email.setBounds(10, 243, 176, 27);
		frmPatientView.getContentPane().add(email);

		JLabel telephone = new JLabel("Telephone: " + patient.getTelephone());
		telephone.setForeground(CustomColours.interChangableBlack());
		telephone.setFont(new Font("Tahoma", Font.PLAIN, 16));
		telephone.setBounds(10, 281, 176, 27);
		frmPatientView.getContentPane().add(telephone);

		JButton export = new JButton("Export Records");
		export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Query q = new Query(Viewpoint.Clinical);
				q.setFunction("getConditions");
				client.send(q);
				Integer size = new Gson().fromJson(client.read(), Integer.class);
				System.out.println(size);
				ArrayList<Condition> conds = new ArrayList<>();

				for (int i = 0; i < size; i++)
					conds.add(new Gson().fromJson(client.read(), Condition.class));
				RecordReport.create(patient_records, patient, drugs, conds);
			}
		});
		export.setForeground(CustomColours.interChangableWhite());
		export.setBackground(CustomColours.Pink());
		export.setBounds(930, 171, 133, 23);
		frmPatientView.getContentPane().add(export);

		JButton btnNewButton = new JButton("Add Diagnosis");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Diagnosis.openWindow(client, doctor, patient, drugs, last);
			}
		});
		btnNewButton.setBounds(866, 313, 197, 68);
		frmPatientView.getContentPane().add(btnNewButton);
	}
}
