package frame;


import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



import javax.swing.Box;
import javax.swing.JLabel;
import java.awt.Color;

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
		
		Box hitsBox = Box.createVerticalBox();
		hitsBox.setBounds(10, 33, 414, 217);
		for(int i=0;i<results.size();++i) {
           hitsBox.add(new JLabel(results.get(i)));
        }
		contentPane.add(hitsBox);
		
		JLabel labelNumerOfResult = new JLabel("");
		labelNumerOfResult.setForeground(Color.RED);
		labelNumerOfResult.setBounds(186, 11, 96, 14);
		labelNumerOfResult.setText(results.size()+" ICs found.");
		contentPane.add(labelNumerOfResult);
	}
}
