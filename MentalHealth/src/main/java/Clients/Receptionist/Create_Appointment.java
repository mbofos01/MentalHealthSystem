package Clients.Receptionist;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.google.gson.Gson;

import Clients.Client;
import Objects.Appointment;
import Objects.Doctor;
import Objects.Patient;
import Objects.ReceptionistObj;
import Tools.Query;
import Tools.Viewpoint;

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

/**
 * Interface for Receptionists. Via this Interface a receptionist can create
 * Appointments and update appointments
 * 
 * @author Demetra Hadjicosti
 *
 */
public class Create_Appointment {
	/**
	 * The frame (window of the application)
	 */
	private JFrame crt_app;
	/**
	 * Field for the Appointment ID
	 */
	private JTextField txtAppID;
	/**
	 * Field for the Receptionist ID
	 */
	private JTextField txt_Receptionist;
	/**
	 * Field for the Date
	 */
	private JTextField txtDate;
	/**
	 * Field for the Time
	 */
	private JTextField txtTime;
	/**
	 * Field for the Clinic ID
	 */
	private JTextField txtClinic;

	/**
	 * Main Procedure of the class that initiates the application
	 * 
	 * @param client     Client object for server client communication
	 * @param model      Receptionist instance
	 * @param p          State of the interface (-1 for add, 1 for update)
	 * @param a          ID of the appointment selected (update mode)
	 * @param id_patient ID of the patient
	 */
	public static void main(Client client, ReceptionistObj model, int p, int a, int id_patient) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Create_Appointment window = new Create_Appointment(client, model, p, a, id_patient);
					window.crt_app.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the Application
	 * 
	 * @param client     Client object for server client communication
	 * @param model      Receptionist instance
	 * @param p          State of the interface (-1 for add, 1 for update)
	 * @param a          ID of the appointment selected (update mode)
	 * @param id_patient ID of the patient
	 */
	public Create_Appointment(Client client, ReceptionistObj model, int p, int a, int id_patient) {
		initialize(client, model, p, a, id_patient);
	}

	/**
	 * Initialize the contents of the frame
	 * 
	 * @param client     Client object for server client communication
	 * @param model      Receptionist instance
	 * @param p          State of the interface (-1 for add, 1 for update)
	 * @param a          ID of the appointment selected (update mode)
	 * @param id_patient ID of the patient
	 */
	private void initialize(Client client, ReceptionistObj model, int p, int a, int id_patient) {
		crt_app = new JFrame();
		crt_app.getContentPane().setBackground(Color.WHITE);
		crt_app.setTitle("Appointment");
		crt_app.setBounds(100, 100, 402, 509);
		crt_app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		crt_app.getContentPane().setLayout(null);

		JCheckBox chk_attend = new JCheckBox("Attended");
		chk_attend.setEnabled(false);
		chk_attend.setBounds(142, 383, 93, 21);
		crt_app.getContentPane().add(chk_attend);

		JLabel lblNewLabel = new JLabel("Appointment ID");
		lblNewLabel.setBounds(76, 48, 93, 13);
		crt_app.getContentPane().add(lblNewLabel);

		txtAppID = new JTextField();
		txtAppID.setEnabled(false);
		txtAppID.setEditable(false);
		txtAppID.setBounds(198, 45, 134, 19);
		crt_app.getContentPane().add(txtAppID);
		txtAppID.setColumns(10);

		JLabel lblDoctorId = new JLabel("Patient ");
		lblDoctorId.setBounds(76, 94, 93, 13);
		crt_app.getContentPane().add(lblDoctorId);

		JLabel lblDoctorId_1 = new JLabel("Doctor ");
		lblDoctorId_1.setBounds(76, 140, 93, 13);
		crt_app.getContentPane().add(lblDoctorId_1);

		JLabel lblReceptionistId = new JLabel("Receptionist ID");
		lblReceptionistId.setBounds(76, 186, 93, 13);
		crt_app.getContentPane().add(lblReceptionistId);

		JLabel lblDate = new JLabel("Date");
		lblDate.setBounds(76, 230, 93, 13);
		crt_app.getContentPane().add(lblDate);

		JLabel lblTime = new JLabel("Time");
		lblTime.setBounds(76, 268, 93, 13);
		crt_app.getContentPane().add(lblTime);

		JCheckBox chk_drop = new JCheckBox("Drop-In");
		chk_drop.setBounds(142, 349, 93, 21);
		crt_app.getContentPane().add(chk_drop);

		txt_Receptionist = new JTextField();
		txt_Receptionist.setEnabled(false);
		txt_Receptionist.setEditable(false);
		txt_Receptionist.setColumns(10);
		txt_Receptionist.setBounds(198, 183, 134, 19);
		crt_app.getContentPane().add(txt_Receptionist);

		txtDate = new JTextField();
		txtDate.setColumns(10);
		txtDate.setBounds(198, 227, 134, 19);
		crt_app.getContentPane().add(txtDate);

		txtTime = new JTextField();
		txtTime.setColumns(10);
		txtTime.setBounds(198, 265, 134, 19);
		crt_app.getContentPane().add(txtTime);

		JComboBox<String> cmb_Patient = new JComboBox<String>();
		cmb_Patient.setBounds(179, 90, 165, 21);
		crt_app.getContentPane().add(cmb_Patient);

		JComboBox<String> cmb_Doctor = new JComboBox<String>();
		cmb_Doctor.setBounds(179, 136, 165, 21);
		crt_app.getContentPane().add(cmb_Doctor);
		JButton btnNewButton = new JButton("Submit");
		JLabel lblClinicId = new JLabel("Clinic ID");
		lblClinicId.setBounds(76, 305, 93, 13);
		crt_app.getContentPane().add(lblClinicId);

		txtClinic = new JTextField();
		txtClinic.setEnabled(false);
		txtClinic.setEditable(false);
		txtClinic.setColumns(10);
		txtClinic.setBounds(198, 302, 134, 19);
		crt_app.getContentPane().add(txtClinic);

		Query q = new Query(Viewpoint.Receptionist);
		q.setFunction("getPatients");
		client.send(q);
		Integer size = new Gson().fromJson(client.read(), Integer.class);
		ArrayList<Patient> patient_list = new ArrayList<Patient>();
		for (int i = 0; i < size; i++) {
			Patient toAdd = new Gson().fromJson(client.read(), Patient.class);
			if (toAdd.isAlive() && p == -1)
				patient_list.add(toAdd);
		}
		int index = 0;
		String data[][] = new String[patient_list.size()][5];
		for (Patient p1 : patient_list) {
			data[index][0] = p1.getPatient_id() + "";
			data[index][1] = p1.getName();
			data[index][2] = p1.getSurname();
			data[index][3] = p1.getTelephone();
			data[index][4] = p1.getEmail();
			String add = data[index][0] + " " + data[index][1] + " " + data[index][2];
			cmb_Patient.insertItemAt(add, index);
			index++;
		}

		Query q1 = new Query(Viewpoint.Receptionist);
		q1.setFunction("getDoctors");
		q1.addArgument(model.getClinic_id() + "");
		client.send(q1);

		Integer size1 = new Gson().fromJson(client.read(), Integer.class);
		ArrayList<Doctor> doctor_list = new ArrayList<Doctor>();
		for (int i = 0; i < size1; i++)
			doctor_list.add(new Gson().fromJson(client.read(), Doctor.class));
		int index1 = 0;
		String data1[][] = new String[doctor_list.size()][3];
		for (Doctor p1 : doctor_list) {
			data1[index1][0] = p1.getName();
			data1[index1][1] = p1.getSurname();
			data1[index1][2] = p1.getId() + "";
			String add = data1[index1][2] + " " + data1[index1][0] + " " + data1[index1][1];
			cmb_Doctor.insertItemAt(add, index1);
			index1++;
		}

		if (p == -1) {
			chk_attend.setEnabled(false);
			chk_attend.setSelected(false);
			chk_drop.setEnabled(true);
			txtDate.setEnabled(true);
			txtTime.setEnabled(true);
			txtAppID.setEnabled(false);
			txt_Receptionist.setEnabled(false);
			txtClinic.setEnabled(false);
			cmb_Doctor.setEnabled(true);
			cmb_Patient.setEnabled(true);
			txt_Receptionist.setText(model.getId() + "");
			txtClinic.setText(model.getClinic_id() + "");
			Query q2 = new Query(Viewpoint.Receptionist);
			q2.setFunction("getlastID");
			client.send(q2);
			Integer last = new Gson().fromJson(client.read(), Integer.class) + 1;
			txtAppID.setText(last + "");
			cmb_Doctor.setSelectedIndex(0);
			cmb_Patient.setSelectedIndex(0);
		} else {
			chk_drop.setEnabled(false);
			txtDate.setEnabled(false);
			txtTime.setEnabled(false);
			txtAppID.setEnabled(false);
			txt_Receptionist.setEnabled(false);
			cmb_Doctor.setEnabled(false);
			cmb_Patient.setEnabled(false);
			txtClinic.setEnabled(false);

			Query q4 = new Query(Viewpoint.Receptionist);
			q4.setFunction("CheckIfAlive");
			q4.addArgument(id_patient + "");
			client.send(q4);
			boolean alive = new Gson().fromJson(client.read(), Boolean.class);
			chk_attend.setEnabled(alive);
			btnNewButton.setEnabled(alive);

			Query q3 = new Query(Viewpoint.Receptionist);
			q3.setFunction("getAppointment");
			q3.addArgument(a + "");
			client.send(q3);
			Appointment app = new Gson().fromJson(client.read(), Appointment.class);
			txtAppID.setText(app.getAppoint_id() + "");
			txt_Receptionist.setText(app.getReceptionist_id() + "");
			txtClinic.setText(model.getClinic_id() + "");
			txtDate.setText(app.getDate());
			txtTime.setText(app.getTime());
			if (app.isDropIn())
				chk_drop.setSelected(true);
			else
				chk_drop.setSelected(false);
			if (app.isAttended())
				chk_attend.setSelected(true);
			else
				chk_attend.setSelected(false);

			int indexd = 0;
			for (indexd = 0; indexd < cmb_Doctor.getItemCount(); indexd++) {
				if (Integer.parseInt(cmb_Doctor.getItemAt(indexd).toString().split(" ")[0]) == app.getDoctor_id()) {
					cmb_Doctor.setSelectedIndex(indexd);
				}
			}

			q = new Query(Viewpoint.Receptionist);
			q.setFunction("getPatients");
			client.send(q);
			size = new Gson().fromJson(client.read(), Integer.class);
			patient_list = new ArrayList<Patient>();
			for (int i = 0; i < size; i++) {
				Patient toAdd = new Gson().fromJson(client.read(), Patient.class);
				patient_list.add(toAdd);
			}
			index1 = 0;
			data = new String[patient_list.size()][5];
			for (Patient p1 : patient_list) {
				data[index1][0] = p1.getPatient_id() + "";
				data[index1][1] = p1.getName();
				data[index1][2] = p1.getSurname();
				data[index1][3] = p1.getTelephone();
				data[index1][4] = p1.getEmail();
				String add = data[index1][0] + " " + data[index1][1] + " " + data[index1][2];
				cmb_Patient.insertItemAt(add, index1);
				index1++;
			}

			for (indexd = 0; indexd < cmb_Patient.getItemCount(); indexd++)
				if (Integer.parseInt(cmb_Patient.getItemAt(indexd).toString().split(" ")[0]) == app.getPatient_id())
					cmb_Patient.setSelectedIndex(indexd);
		}

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (p == -1) {
					Query q = new Query(Viewpoint.Receptionist);
					q.setFunction("newAppointment");
					q.addArgument(cmb_Doctor.getSelectedItem().toString().split(" ")[0]);
					q.addArgument(cmb_Patient.getSelectedItem().toString().split(" ")[0]);
					q.addArgument(txtClinic.getText());
					q.addArgument(txtDate.getText());
					q.addArgument(txtTime.getText());

					Query q3 = new Query(Viewpoint.Receptionist);
					q3.setFunction("getAppointment");
					q3.addArgument(a + "");
					client.send(q3);
					Appointment app = new Gson().fromJson(client.read(), Appointment.class);
					if (chk_drop.isSelected()) {
						int option = JOptionPane.showConfirmDialog(null,
								"Last Appointment was at: " + app.getDate() + ".\nDo you want to continue?",
								"Overprescription Alert", JOptionPane.YES_NO_OPTION);
						if (option == 0)
							q.addArgument("1");
						else {
							chk_drop.setSelected(false);
							q.addArgument("0");
						}
					}
					else 
						q.addArgument("0");

					q.addArgument(txt_Receptionist.getText());
					q.addArgument("0");
					client.send(q);
					JOptionPane.showMessageDialog(crt_app.getContentPane(), "Added successfully");
				} else {
					Query q2 = new Query(Viewpoint.Receptionist);
					q2.setFunction("updateApp");
					q2.addArgument(txtAppID.getText());
					if (chk_attend.isSelected())
						q2.addArgument("1");
					else
						q2.addArgument("0");
					client.send(q2);
					JOptionPane.showMessageDialog(crt_app.getContentPane(), "Updated successfully");
				}
			}
		});
		btnNewButton.setBackground(new Color(0, 102, 255));
		btnNewButton.setBounds(142, 426, 93, 21);
		crt_app.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Back");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crt_app.dispose();
				Receptionist.openWin(client, model);
			}
		});
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBackground(new Color(0, 204, 102));
		btnNewButton_1.setBounds(10, 10, 80, 23);
		crt_app.getContentPane().add(btnNewButton_1);
	}
}
