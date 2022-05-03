package Clients.Receptionist;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.google.gson.Gson;

import Clients.Client;
import Objects.ReceptionistObj;
import Tools.CustomColours;
import Tools.Query;
import Tools.Viewpoint;

public class ReceptionistLogin {

	private JFrame frmReceptionistLogin;
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
					ReceptionistLogin window = new ReceptionistLogin(client);
					window.frmReceptionistLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ReceptionistLogin(Client Client) {
		initialize(Client);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(final Client client) {
		frmReceptionistLogin = new JFrame();
		frmReceptionistLogin.getContentPane().setForeground(CustomColours.Gray());
		frmReceptionistLogin.setTitle("Receptionist Login Page");
		frmReceptionistLogin.setResizable(false);
		frmReceptionistLogin.setBounds(100, 100, 539, 422);
		frmReceptionistLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmReceptionistLogin.getContentPane().setLayout(null);

		usernameField = new JTextField();
		usernameField.setBounds(265, 157, 213, 28);
		frmReceptionistLogin.getContentPane().add(usernameField);
		usernameField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(265, 215, 213, 28);
		frmReceptionistLogin.getContentPane().add(passwordField);

		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		usernameLabel.setBounds(75, 157, 165, 28);
		frmReceptionistLogin.getContentPane().add(usernameLabel);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		passwordLabel.setBounds(75, 215, 165, 28);
		frmReceptionistLogin.getContentPane().add(passwordLabel);

		JButton loginbtn = new JButton("Log In");
		loginbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user1 = usernameField.getText();
				String pass1 = new String(passwordField.getPassword());

				Query query = new Query(Viewpoint.Receptionist);
				query.setFunction("login");
				query.addArgument(user1);
				query.addArgument(pass1);
				client.send(query);

				ReceptionistObj record = new Gson().fromJson(client.read(), ReceptionistObj.class);
				if (record.getId() == -1) {
					JOptionPane.showMessageDialog(null, "           Wrong password!" + '\n', "Error",
							JOptionPane.ERROR_MESSAGE);
					System.exit(0);

				} else {
					System.out.println(record.toString());
					frmReceptionistLogin.dispose();
					Receptionist.openWin(client, record);
				}

				return;
			}

		});

		loginbtn.setBackground(CustomColours.Green());
		loginbtn.setForeground(CustomColours.White());
		loginbtn.setFont(new Font("Tahoma", Font.PLAIN, 17));
		loginbtn.setBounds(102, 295, 138, 38);
		frmReceptionistLogin.getContentPane().add(loginbtn);

		JButton cancelbtn = new JButton("Cancel");
		cancelbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		cancelbtn.setForeground(CustomColours.White());
		cancelbtn.setFont(new Font("Tahoma", Font.PLAIN, 17));
		cancelbtn.setBackground(CustomColours.Red());
		cancelbtn.setBounds(280, 295, 138, 38);
		frmReceptionistLogin.getContentPane().add(cancelbtn);

		JLabel title = new JLabel("Login to Reception");
		title.setForeground(CustomColours.Black());
		title.setVerticalAlignment(SwingConstants.TOP);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.PLAIN, 24));
		title.setBounds(56, 28, 422, 55);
		frmReceptionistLogin.getContentPane().add(title);

		JLabel title2 = new JLabel("Management System");
		title2.setForeground(CustomColours.Black());
		title2.setFont(new Font("Tahoma", Font.PLAIN, 24));
		title2.setBounds(154, 69, 239, 38);
		frmReceptionistLogin.getContentPane().add(title2);
	}

}
