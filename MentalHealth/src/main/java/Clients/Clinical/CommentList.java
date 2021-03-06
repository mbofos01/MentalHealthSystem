package Clients.Clinical;

import java.awt.EventQueue;

import javax.swing.JFrame;

import static javax.swing.JOptionPane.showMessageDialog;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;

import Clients.Client;
import Objects.Comment;
import Objects.Doctor;
import Objects.Patient;
import Tools.CustomColours;
import Tools.Query;
import Tools.Viewpoint;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This application window is used to provide a comment view for a doctor.
 * 
 * @author Michail Panagiotis Bofos
 *
 */
public class CommentList {
	/**
	 * JFrame that creates the window
	 */
	private JFrame frame;
	/**
	 * JPanel for the table to be in
	 */
	private JPanel contentPane;
	/**
	 * JTable for the table of the comments
	 */
	private JTable commentTable;
	/**
	 * JButton for the back function
	 */
	private JButton back;

	/**
	 * Launch the application.
	 * 
	 * @param client  Client object for server client communication
	 * @param doctor  Doctor object - the one how is logged in
	 * @param patient Patient object - the patient whom we want to create a
	 *                diagnosis
	 */
	public static void openWindow(Client client, Doctor doctor, Patient patient) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CommentList window = new CommentList(client, doctor, patient);
					window.frame.setVisible(true);
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
	 */
	public CommentList(Client client, Doctor doctor, Patient patient) {
		initialize(client, doctor, patient);
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @param client  Client object for server client communication
	 * @param doctor  Doctor object - the one how is logged in
	 * @param patient Patient object - the patient whom we want to create a
	 *                diagnosis
	 */
	private void initialize(Client client, Doctor doctor, Patient patient) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Comments for " + patient.getName() + " " + patient.getSurname());
		frame.setBounds(100, 100, 635, 606);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		contentPane = new JPanel();
		contentPane.setBackground(CustomColours.interChangableWhite());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		Query q = new Query(Viewpoint.Clinical);
		q.setFunction("getPatientComments");
		q.addArgument("" + patient.getPatient_id());
		client.send(q);

		Integer size = new Gson().fromJson(client.read(), Integer.class);
		ArrayList<Comment> comm_list = new ArrayList<Comment>();
		for (int i = 0; i < size; i++)
			comm_list.add(new Gson().fromJson(client.read(), Comment.class));
		// SHOW
		String col[] = { "Doctor", "Date", "Comment" };
		int index = 0;
		String data[][] = new String[comm_list.size()][col.length];
		for (Comment co : comm_list) {
			data[index][0] = co.getDoctor_name().charAt(0) + ". " + co.getDoctor_surname();
			data[index][1] = co.getDate();
			data[index][2] = co.getComment();
			index++;
		}

		DefaultTableModel model = new DefaultTableModel(data, col);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 85, 568, 456);
		contentPane.add(scrollPane);
		commentTable = new JTable();
		scrollPane.setViewportView(commentTable);
		commentTable = new JTable(model);
		scrollPane.setViewportView(commentTable);

		JLabel nameTag = new JLabel(patient.getName() + " " + patient.getSurname());
		nameTag.setHorizontalAlignment(SwingConstants.CENTER);
		nameTag.setForeground(CustomColours.interChangableBlack());
		nameTag.setFont(new Font("Tahoma", Font.PLAIN, 22));
		nameTag.setBounds(135, 11, 357, 44);
		contentPane.add(nameTag);

		back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		back.setBackground(CustomColours.Red());
		back.setForeground(CustomColours.interChangableWhite());
		back.setBounds(23, 27, 89, 23);
		contentPane.add(back);
		commentTable.setDefaultEditor(Object.class, null);
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		commentTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int p = commentTable.getSelectedRow();
				showMessageDialog(null, comm_list.get(p).getComment(), "Comment", JOptionPane.INFORMATION_MESSAGE);

			}
		});
	}
}
