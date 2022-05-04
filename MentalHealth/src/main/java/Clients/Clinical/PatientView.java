package Clients.Clinical;

import java.awt.EventQueue;

import javax.swing.JFrame;

import Objects.Appointment;
import Objects.Condition;
import Objects.Doctor;
import Objects.Drug;
import Objects.Patient;
import Objects.PatientRecord;
import Tools.Clock;
import Tools.CustomColours;
import Tools.Query;
import Tools.RecordReport;
import Tools.Viewpoint;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;

import Clients.Client;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

/**
 * This application window presents the patient info to a doctor.
 * 
 * @author Michail Panagiotis Bofos
 *
 */
public class PatientView {
	/**
	 * JFrame that creates the window
	 */
	private JFrame frmPatientView;
	/**
	 * Last patient record
	 */
	private PatientRecord last = new PatientRecord();
	/**
	 * JTable for patient appointments
	 */
	private JTable appointTable;

	/**
	 * Launch the application.
	 * 
	 * @param client  Client object for server client communication
	 * @param doctor  Doctor object - the one how is logged in
	 * @param patient Patient object - the patient whom we want to create a
	 *                diagnosis
	 * @param drugs   An ArrayList of drugs - the list of all drugs in our database
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
	 * 
	 * @param client  Client object for server client communication
	 * @param doctor  Doctor object - the one how is logged in
	 * @param patient Patient object - the patient whom we want to create a
	 *                diagnosis
	 * @param drugs   An ArrayList of drugs - the list of all drugs in our database
	 */
	public PatientView(Client client, Doctor doctor, Patient patient, ArrayList<Drug> drugs) {
		initialize(client, doctor, patient, drugs);
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @param client  Client object for server client communication
	 * @param doctor  Doctor object - the one how is logged in
	 * @param patient Patient object - the patient whom we want to create a
	 *                diagnosis
	 * @param drugs   An ArrayList of drugs - the list of all drugs in our database
	 */
	private void initialize(Client client, Doctor doctor, Patient patient, ArrayList<Drug> drugs) {
		ArrayList<PatientRecord> patient_records = new ArrayList<>();

		Query q = new Query(Viewpoint.Clinical);
		q.setFunction("getPatientRecords");
		q.addArgument("" + patient.getPatient_id());
		q.addArgument("" + doctor.getId());
		client.send(q);

		Integer size = new Gson().fromJson(client.read(), Integer.class);
		if (size == 0) {
			last = new PatientRecord();
			last.setTreatment_id(-1);

		} else {
			for (int i = 0; i < size; i++)
				patient_records.add(new Gson().fromJson(client.read(), PatientRecord.class));

			// System.out.println(size);
			if (patient_records.size() != 0)
				last = patient_records.get(patient_records.size() - 1);
		}

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
				// System.out.println(commentsText.getText());
				if (commentsText.getText().length() > 0) {
					Query q = new Query(Viewpoint.Clinical);
					q.setFunction("addComment");
					q.addArgument("" + doctor.getClinic_id());
					q.addArgument("" + patient.getPatient_id());
					q.addArgument(commentsText.getText());
					client.send(q);
					String answer = client.read();
					// System.out.println(answer);
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
				// System.out.println(size);
				ArrayList<Condition> conds = new ArrayList<>();

				for (int i = 0; i < size; i++)
					conds.add(new Gson().fromJson(client.read(), Condition.class));
				RecordReport.create(patient_records, patient, drugs, conds);
			}
		});
		export.setForeground(CustomColours.interChangableWhite());
		export.setBackground(CustomColours.Pink());
		export.setBounds(916, 114, 145, 23);
		frmPatientView.getContentPane().add(export);

		JButton death = new JButton("Death Of Patient");
		death.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Query q = new Query(Viewpoint.Clinical);
				q.setFunction("requestDeath");
				q.addArgument("" + patient.getPatient_id());
				q.addArgument("" + doctor.getId());
				q.addArgument(Clock.currentSQLTime());
				client.send(q);
			}
		});
		death.setForeground(CustomColours.interChangableWhite());
		death.setBackground(CustomColours.interChangableBlack());
		death.setBounds(916, 33, 145, 25);
		frmPatientView.getContentPane().add(death);

		JButton allergiesBtn = new JButton("Allergies");
		allergiesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AllergyWindow.openWindow(client, patient, drugs);
			}
		});
		allergiesBtn.setForeground(CustomColours.interChangableWhite());
		allergiesBtn.setBackground(CustomColours.Brown());
		allergiesBtn.setBounds(43, 336, 89, 23);
		frmPatientView.getContentPane().add(allergiesBtn);
		/***********************************************/
		Query getApps = new Query(Viewpoint.Clinical);
		getApps.setFunction("getAppointmentsOfDoctorAndPatient");
		getApps.addArgument("" + doctor.getId());
		getApps.addArgument("" + patient.getPatient_id());
		client.send(getApps);

		Integer size_ap = new Gson().fromJson(client.read(), Integer.class);
		// System.out.println(size);
		ArrayList<Appointment> list = new ArrayList<Appointment>();
		for (int i = 0; i < size_ap; i++)
			list.add(new Gson().fromJson(client.read(), Appointment.class));
		String col[] = { "Patient", "Date", "Time", "Type", "Attended", "Updated" };
		int index = 0;
		String data[][] = new String[list.size()][col.length];
		for (Appointment ap : list) {
			data[index][0] = patient.getName() + " " + patient.getSurname();
			data[index][1] = ap.getDate();
			data[index][2] = ap.getTime();
			data[index][3] = ap.getType();
			if (ap.isAttended())
				data[index][4] = "YES";
			else
				data[index][4] = "NO";

			if (ap.getRecord_id() == -1)
				data[index][5] = "NO";
			else
				data[index][5] = "YES";

			index++;
		}
		DefaultTableModel model = new DefaultTableModel(data, col);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(294, 79, 524, 128);
		frmPatientView.getContentPane().add(scrollPane);
		appointTable = new JTable();
		scrollPane.setViewportView(appointTable);
		appointTable = new JTable(model);
		scrollPane.setViewportView(appointTable);
		appointTable.setDefaultEditor(Object.class, null);
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		appointTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int p = appointTable.getSelectedRow();
				PatientRecord specific = last;
				for (PatientRecord s : patient_records)
					if (s.getRecord_id() == list.get(p).getRecord_id())
						specific = s;
				Diagnosis.openWindow(client, doctor, patient, drugs, specific);
				frmPatientView.dispose();

			}
		});

	}
}
