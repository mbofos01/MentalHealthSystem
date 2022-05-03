package Clients.HealthService;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

import Clients.Client;
import Objects.HealthServ;

import javax.swing.JComboBox;
import javax.swing.JSeparator;

public class HealthService {

	private JFrame frmHealthService;

	public static void openWindow(final Client client, final HealthServ model) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HealthService window = new HealthService(client, model);
					window.frmHealthService.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HealthService(Client client, HealthServ model) {
		initialize(client, model);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Client client, HealthServ model) {
		frmHealthService = new JFrame();
		frmHealthService.setTitle("Health Service");
		frmHealthService.getContentPane().setBackground(Color.WHITE);
		frmHealthService.getContentPane().setLayout(null);
		
		JButton btnWeekReport = new JButton("Generate Weekly Report");
		btnWeekReport.setBackground(new Color(51, 204, 102));
		btnWeekReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnWeekReport.setBounds(174, 67, 180, 21);
		frmHealthService.getContentPane().add(btnWeekReport);
		
		JLabel lblNewLabel = new JLabel("Select Condition and Treatment to Generate Report");
		lblNewLabel.setBounds(147, 128, 258, 13);
		frmHealthService.getContentPane().add(lblNewLabel);
		
		JComboBox cmbCondition = new JComboBox();
		cmbCondition.setBounds(115, 184, 119, 21);
		frmHealthService.getContentPane().add(cmbCondition);
		
		JComboBox cmbTreatment = new JComboBox();
		cmbTreatment.setBounds(288, 184, 117, 21);
		frmHealthService.getContentPane().add(cmbTreatment);
		
		JButton btnReportPatient = new JButton("Generate Report with patients");
		btnReportPatient.setBackground(new Color(51, 204, 102));
		btnReportPatient.setBounds(174, 227, 180, 21);
		frmHealthService.getContentPane().add(btnReportPatient);
		
		JLabel lblNewLabel_1 = new JLabel("Condition");
		lblNewLabel_1.setBounds(157, 161, 45, 13);
		frmHealthService.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Treatment");
		lblNewLabel_1_1.setBounds(321, 161, 71, 13);
		frmHealthService.getContentPane().add(lblNewLabel_1_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(131, 146, 276, 2);
		frmHealthService.getContentPane().add(separator);
		frmHealthService.setBackground(Color.WHITE);
		frmHealthService.setBounds(100, 100, 524, 381);
		frmHealthService.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
