package Clients.MedicalRecordsStaff;

import java.awt.EventQueue;

import javax.swing.JFrame;

import Clients.Client;
import Objects.Drug;
import Objects.RecordsStaff;
import Objects.Request;
import Objects.Transaction;
import Tools.CustomColours;
import Tools.Query;
import Tools.Viewpoint;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
/**
 * This class represents 
 * @author Ioanna Theophilou
 *
 */
public class Transactions {
	/**
	 * the frame
	 */
	private JFrame frame;
	/**
	 * the content pane
	 */
	private JPanel contentPane;
	/**
	 * the table
	 */
	private JTable table;
	/**
	 * the label
	 */
	private JLabel lblNewLabel;

	/**
	 * to start the app
	 * @param client
	 */
	private void initialize(Client client) {
		frame = new JFrame();
		frame.setBounds(100, 100, 582, 484);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblNewLabel_4 = new JLabel("       View all Transactions");
		lblNewLabel_4.setFont(new Font("Segoe Script", Font.BOLD, 32));
		frame.getContentPane().add(lblNewLabel_4, BorderLayout.NORTH);

		contentPane = new JPanel();
		contentPane.setBackground(CustomColours.interChangableWhite());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String col[] = { "Request Type", "Request", "Accepted", "Date" };
		int index = 0;
		System.out.println(Transaction.req.size());
		String data[][] = new String[Transaction.req.size()][col.length];
		for (Request tr : Transaction.req) {
			data[index][0] = tr.getType() + "";
			data[index][1] = tr.getDescr();
			data[index][2] = "" + tr.getAccepted();
			data[index][3] = tr.getDt();
			index++;
		}

		DefaultTableModel model = new DefaultTableModel(data, col);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(42, 90, 485, 307);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table = new JTable(model);
		scrollPane.setViewportView(table);
		
		lblNewLabel = new JLabel("All Transactions");
		lblNewLabel.setFont(new Font("Segoe Script", Font.PLAIN, 25));
		lblNewLabel.setBounds(153, 10, 255, 60);
		contentPane.add(lblNewLabel);
		table.setDefaultEditor(Object.class, null);

	}
	/**
	 * The constructor 
	 * @param client
	 */
	public Transactions(Client client) {
		initialize(client);
	}
	
	/**
	 * The function to open this window. 
	 * @param client
	 * @param req
	 */
	public static void openWindow(Client client) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Transactions window = new Transactions(client);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
