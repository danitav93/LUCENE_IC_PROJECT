package frame;


import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;

import utility.Constant;

import javax.swing.JList;
import javax.swing.JScrollPane;


public class IndexList extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public IndexList(IndexReader reader) throws IOException {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		

		DefaultListModel<String> data = new DefaultListModel<>();
		for (int i=0;i<reader.maxDoc();i++) {
			Document doc = reader.document(i);
			data.addElement( doc.get(Constant.IC_NUMBER.toString()) 
			+ "     " + doc.get(Constant.NAME.toString())
			+ "     " + doc.get(Constant.SURNAME.toString())
			+ "     " + doc.get(Constant.BIRTH_DATE.toString())
			+ "     " + doc.get(Constant.STATE.toString())
			+ "     " + doc.get(Constant.CITY.toString())
			+ "     " + doc.get(Constant.HEIGHT.toString()));


			
		}
		
		
	
		
		JList<String> list = new JList<String>(data);
		list.setBounds(10, 11, 414, 240);
		
		
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(10, 11, 414, 240);
		contentPane.add(scrollPane);
	}
}
