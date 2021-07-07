package pbo;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Vector;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class Covid {

	private JFrame frame;
	private JTable table;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Covid window = new Covid();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Covid() {
		initialize();
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.BLACK);
		frame.getContentPane().setForeground(Color.BLACK);
		frame.setBounds(100, 100, 689, 422);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		textField = new JTextField();
		textField.setToolTipText("masukan kode negara");
		textField.setBounds(125, 34, 313, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		JLabel lblNewLabel = new JLabel("Aplikasi Aplikasi Pantau Covid-19 ");
		lblNewLabel.setBounds(10, 3, 653, 20);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBackground(Color.BLACK);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 15));
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Cari Negara");
		btnNewButton.setBounds(448, 31, 139, 23);
		frame.getContentPane().add(btnNewButton);
		
		
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Request a = new Request();
				a.request(textField.getText());
				Data data = new Data();
				table = new JTable(data.read());
				
				
				
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		        JScrollPane pane = new JScrollPane(table);
		        pane.setBounds(37, 76, 500, 278);
				frame.getContentPane().add(pane, BorderLayout.CENTER);


			}
		});
		
		
		
		
		
	}
}
