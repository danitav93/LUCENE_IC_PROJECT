package frame;


import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.lucene.document.Document;

import utility.Constant;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class ResultsFrame extends JFrame {


	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public ResultsFrame(ArrayList<String> results) {
		
		
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		DefaultListModel<String> data = new DefaultListModel<>();
		for (int i=0;i<results.size();i++) {
			data.addElement( results.get(i));
		}
		
		JLabel labelNumerOfResult = new JLabel("");
		labelNumerOfResult.setForeground(Color.RED);
		labelNumerOfResult.setBounds(186, 11, 96, 14);
		labelNumerOfResult.setText(results.size()+" ICs found.");
		contentPane.add(labelNumerOfResult);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(428, 51, -427, 200);
		contentPane.add(scrollPane);
		
		JList<String> list = new JList<String>(data);
		contentPane.add(list);
		list.setBounds(0, 34, 434, 228);
	}
}
