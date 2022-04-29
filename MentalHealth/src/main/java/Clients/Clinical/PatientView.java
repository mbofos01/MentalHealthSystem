package Clients.Clinical;

import java.awt.EventQueue;

import javax.swing.JFrame;

import Objects.Patient;
import javax.swing.JButton;
import javax.swing.JLabel;

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
		backButton.setBackground(Tools.CustomColours.Orange());
		backButton.setForeground(Tools.CustomColours.interChangableWhite());
		backButton.setBounds(10, 20, 85, 27);
		frmPatientView.getContentPane().add(backButton);

		JLabel patientName = new JLabel(patient.getName() + " " + patient.getSurname());

		patientName.setForeground(Tools.CustomColours.interChangableBlack());
		patientName.setFont(new Font("Tahoma", Font.PLAIN, 25));
		patientName.setBounds(397, 21, 319, 37);
		frmPatientView.getContentPane().add(patientName);
	}
}
