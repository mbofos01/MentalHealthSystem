package Clients.Clinical;

import java.awt.EventQueue;

import javax.swing.JFrame;

import Objects.Patient;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PatientView {

	private JFrame frmPatientView;

	/**
	 * Launch the application.
	 */
	public static void openWindow(Patient patient) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PatientView window = new PatientView(patient);
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
	public PatientView(Patient patient) {
		initialize(patient);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Patient patient) {

		frmPatientView = new JFrame();
		if (Tools.CustomColours.isDark())
			frmPatientView.getContentPane().setBackground(Tools.CustomColours.Black());
		else
			frmPatientView.getContentPane().setBackground(Tools.CustomColours.White());
		frmPatientView.setTitle("Patient View - " + patient.getName() + " " + patient.getSurname());
		frmPatientView.setResizable(false);
		frmPatientView.setBounds(100, 100, 1129, 521);
		frmPatientView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPatientView.getContentPane().setLayout(null);

		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Tools.CustomColours.changeTheme();
				SwingUtilities.updateComponentTreeUI(frmPatientView);
				// frmPatientView.dispose();

			}
		});
		backButton.setBackground(Tools.CustomColours.Orange());
		backButton.setForeground(Tools.CustomColours.White());
		backButton.setBounds(10, 20, 85, 27);
		frmPatientView.getContentPane().add(backButton);

		JLabel patientName = new JLabel(patient.getName() + " " + patient.getSurname());
		if (Tools.CustomColours.isDark())
			patientName.setForeground(Tools.CustomColours.White());
		else
			patientName.setForeground(Tools.CustomColours.Black());
		patientName.setFont(new Font("Tahoma", Font.PLAIN, 25));
		patientName.setBounds(371, 27, 319, 37);
		frmPatientView.getContentPane().add(patientName);
	}
}
