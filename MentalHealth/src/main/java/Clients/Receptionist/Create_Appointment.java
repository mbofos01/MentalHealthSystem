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
import Objects.Patient;
import Objects.ReceptionistObj;
import Tools.Query;
import Tools.Viewpoint;

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class Create_Appointment {

	private JFrame crt_app;
	private JTextField txtAppID;
	private JTextField txt_Receptionist;
	private JTextField txtDate;
	private JTextField txtTime;
	private JTextField txtClinic;

	/**
	 * Launch the application.
	 */
	public static void main(Client client, ReceptionistObj model, int p, int a) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Create_Appointment window = new Create_Appointment(client, model, p, a);
					window.crt_app.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Create_Appointment(Client client, ReceptionistObj model, int p, int a) {
		initialize(client, model, p, a);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Client client, ReceptionistObj model, int p, int a) {
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
		cmb_Patient.setEditable(true);
		cmb_Patient.setBounds(198, 90, 134, 21);
		crt_app.getContentPane().add(cmb_Patient);

		JComboBox<String> cmb_Doctor = new JComboBox<String>();
		cmb_Doctor.setEditable(true);
		cmb_Doctor.setBounds(198, 136, 134, 21);
		crt_app.getContentPane().add(cmb_Doctor);

		JLabel lblClinicId = new JLabel("Clinic ID");
		lblClinicId.setBounds(76, 305, 93, 13);
		crt_app.getContentPane().add(lblClinicId);

		txtClinic = new JTextField();
		txtClinic.setEnabled(false);
		txtClinic.setEditable(false);
		txtClinic.setColumns(10);
		txtClinic.setBounds(198, 302, 134, 19);
		crt_app.getContentPane().add(txtClinic);
		
		if (p==-1) {
			chk_attend.setEnabled(false);
			chk_attend.setSelected(false);
			chk_drop.setEnabled(true);
			txtDate.setEnabled(true);
			txtTime.setEnabled(true);
			txtAppID.setEnabled(false);
			txt_Receptionist.setEnabled(false);
			cmb_Patient.setEnabled(true);
			cmb_Doctor.setEnabled(true);
			txtClinic.setEnabled(false);
			
			txt_Receptionist.setText(model.getId()+"");
			txtClinic.setText(model.getClinic_id()+"");
			txtAppID.setText(model.getClinic_id()+"");
		}
		else {
			chk_attend.setEnabled(true);
			chk_drop.setEnabled(false);
			txtDate.setEnabled(false);
			txtTime.setEnabled(false);
			txtAppID.setEnabled(false);
			txt_Receptionist.setEnabled(false);
			cmb_Patient.setEnabled(false);
			cmb_Doctor.setEnabled(false);
			txtClinic.setEnabled(false);
			
			//chk_drop.setSelected(false);
			//chk_attend.setSelected(false);
		}
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (p==-1) {
					Query q = new Query(Viewpoint.Receptionist);
					q.setFunction("newAppointment");
					// insertAppointment(int doctor_id, int patient_id, int clinic_id, String date, String time,
					//int drop_in, int receptionist_id)
					q.addArgument(txtClinic.getText());
					q.addArgument(txtDate.getText());
					q.addArgument(txtTime.getText());
					//q.addArgument(chk_drop.se)
					q.addArgument(txt_Receptionist.getText());
				
					client.send(q);
					JOptionPane.showMessageDialog(crt_app.getContentPane(), "Added successfully");
				}
				else {
					
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
