package Clients.Clinical;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import com.google.gson.Gson;

import Clients.Client;
import Objects.Doctor;
import Tools.Query;
import Tools.Viewpoint;
import Tools.CustomColours;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * In-page for the Medical Records Management Viewpoint. As stated in the basic
 * structure each viewpoint must have a client object as an argument.
 * 
 * @author Michail Panagiotis Bofos
 *
 */
public class ClinicalLogin {

	private JFrame frmClinicalStaff;
	private JTextField usernameField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client client = new Client("clientConf.json");
					ClinicalLogin window = new ClinicalLogin(client);
					window.frmClinicalStaff.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the application.
	 */
	public ClinicalLogin(Client client) {
		initialize(client);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(final Client client) {
		frmClinicalStaff = new JFrame();
		frmClinicalStaff.getContentPane().setBackground(CustomColours.interChangableWhite());
		frmClinicalStaff.setTitle("Clincal Staff Login Page");
		frmClinicalStaff.setResizable(false);
		frmClinicalStaff.setBounds(100, 100, 539, 422);
		frmClinicalStaff.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmClinicalStaff.getContentPane().setLayout(null);

		usernameField = new JTextField();
		usernameField.setBounds(265, 157, 213, 28);
		frmClinicalStaff.getContentPane().add(usernameField);
		usernameField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(265, 215, 213, 28);
		frmClinicalStaff.getContentPane().add(passwordField);

		JLabel usernameLabel = new JLabel("Username:");

		usernameLabel.setForeground(CustomColours.interChangableBlack());
		usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		usernameLabel.setBounds(75, 157, 165, 28);
		frmClinicalStaff.getContentPane().add(usernameLabel);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setForeground(CustomColours.interChangableBlack());
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passwordLabel.setBounds(75, 215, 165, 28);
		frmClinicalStaff.getContentPane().add(passwordLabel);

		JButton loginbtn = new JButton("Log In");
		if (CustomColours.isDark())
			loginbtn.setForeground(CustomColours.Black());
		else
			loginbtn.setForeground(CustomColours.White());
		loginbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user1 = usernameField.getText();
				String pass1 = new String(passwordField.getPassword());

				Query query = new Query(Viewpoint.Clinical);
				query.setFunction("login");
				query.addArgument(user1);
				query.addArgument(pass1);
				client.send(query);

				Doctor doctor = new Gson().fromJson(client.read(), Doctor.class);

				if (doctor.getId() == -1) {
					JOptionPane.showMessageDialog(null, "           Wrong password!" + '\n', "Error",
							JOptionPane.ERROR_MESSAGE);
					System.exit(0);

				} else {

					MainPage.openWindow(client, doctor);
					frmClinicalStaff.dispose();
				}

				return;
			}

		});

		loginbtn.setBackground(CustomColours.Green());
		loginbtn.setFont(new Font("Tahoma", Font.PLAIN, 17));
		loginbtn.setBounds(102, 295, 138, 38);
		frmClinicalStaff.getContentPane().add(loginbtn);

		JButton cancelbtn = new JButton("Cancel");

		cancelbtn.setForeground(CustomColours.interChangableWhite());

		cancelbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});

		cancelbtn.setFont(new Font("Tahoma", Font.PLAIN, 17));
		cancelbtn.setBackground(CustomColours.Red());
		cancelbtn.setBounds(280, 295, 138, 38);
		frmClinicalStaff.getContentPane().add(cancelbtn);

		JLabel title = new JLabel("Login to Clinical Staff Viewpoint");

		title.setForeground(CustomColours.interChangableBlack());
		title.setVerticalAlignment(SwingConstants.TOP);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.PLAIN, 24));
		title.setBounds(56, 28, 422, 55);
		frmClinicalStaff.getContentPane().add(title);
	}
}
