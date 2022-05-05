package Clients.MedicalRecordsStaff;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;

import Clients.Client;
import Objects.Request;
import Objects.Transaction;
import Tools.CustomColours;
import Tools.Query;
import Tools.Viewpoint;

public class Requests {
//record, treatment, 
	private JFrame frmAllRequests;
	private JPanel contentPane;
	public static JTable table;
	private JLabel lblNewLabel;
	private Client client;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the application.
	 */

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Client client) {

		frmAllRequests = new JFrame();
		frmAllRequests.setTitle("All Requests");
		frmAllRequests.setBounds(100, 100, 910, 504);
		frmAllRequests.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblNewLabel_4 = new JLabel("       View all Requests");
		lblNewLabel_4.setFont(new Font("Segoe Script", Font.BOLD, 32));
		frmAllRequests.getContentPane().add(lblNewLabel_4, BorderLayout.NORTH);

		contentPane = new JPanel();
		contentPane.setBackground(CustomColours.interChangableWhite());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frmAllRequests.setContentPane(contentPane);
		contentPane.setLayout(null);

		Query q = new Query(Viewpoint.MedicalRecords);
		q.setFunction("getRequests");
		client.send(q);

		Integer size = new Gson().fromJson(client.read(), Integer.class);
		// System.out.println(size);.
		ArrayList<Request> req = new ArrayList<Request>();
		for (int i = 0; i < size; i++)
			req.add(new Gson().fromJson(client.read(), Request.class));
		// SHOW
		String col[] = { "Decription", "Date" };
		int index = 0;
		String data[][] = new String[req.size()][col.length];
		for (Request tr : req) {
			data[index][0] = tr.getDescr();
			data[index][1] = tr.getDt();
			index++;
		}

		DefaultTableModel model = new DefaultTableModel(data, col);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(42, 90, 814, 334);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table = new JTable(model);
		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int p = table.getSelectedRow();
				System.out.println("i selected: " + req.get(p).getDescr());
				
				HandleRequests.openWindow(client, req.get(p));
				
				frmAllRequests.dispose();
			}
		});

		lblNewLabel = new JLabel("All Requests");
		lblNewLabel.setFont(new Font("Segoe Script", Font.PLAIN, 25));
		lblNewLabel.setBounds(351, 20, 255, 60);
		contentPane.add(lblNewLabel);
		table.setDefaultEditor(Object.class, null);

	}
	public static JTable getTable() {
		return table;
	}
	public void setTable(JTable table) {
		this.table = table;
	}
	public static void openWindow(Client client) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Requests window = new Requests(client);
					window.frmAllRequests.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public Requests(Client client) {
		initialize(client);
	}

}
