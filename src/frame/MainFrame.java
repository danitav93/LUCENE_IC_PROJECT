package frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dialog.WaitingDialog;
import utility.Constant;
import utility.ApplicationMethods;
import entity.IdCardEntity;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;

import lucene.Lucene;

import javax.swing.SwingConstants;




public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Layout fields
	 */
	private JTextField textFieldIcNumber;
	private JTextField textFieldName;
	private JTextField textFieldBirthDate;
	private JTextField textFieldSurname;
	private JTextField textFieldState;
	private JTextField textFieldCity;
	private JTextField textFieldHeight;



	private HashMap<Integer,JLabel > hashMapErrorMessage = new HashMap<>();



	/**
	 * context
	 */
	private JFrame jFrame= (JFrame) this;


	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setBounds(100, 100, 596, 376);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/**
		 * Add Ic button
		 */
		JButton btnAddIc = new JButton("");
		java.awt.Image addImg = new ImageIcon(this.getClass().getResource("/add.png")).getImage();
		btnAddIc.setFocusPainted( false );
		btnAddIc.setIcon(new ImageIcon(addImg));
		btnAddIc.setBounds(88, 239, 86, 31);
		btnAddIc.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				WaitingDialog waitingDialog = new WaitingDialog( jFrame);
				waitingDialog.setVisible(true);
				deleteErrorMessages();
				boolean indexingSucess=false;
				boolean icRulesVioleted=false;
				IdCardEntity idCardEntity = getIdCardEntity();
				if (ApplicationMethods.checkIcInsertRules(idCardEntity,hashMapErrorMessage)) {
					if (Lucene.indexIc(idCardEntity)) {
						indexingSucess=true;
					};
				} else {
					icRulesVioleted=true;
				}

				setEnabled(true);
				waitingDialog.dispose();
				if (indexingSucess) {
					JOptionPane.showMessageDialog(null, "Insert succeded!");
				} else if (!icRulesVioleted){
					JOptionPane.showMessageDialog(null, "Insert failed!");
				}
			}

		});
		getContentPane().setLayout(null);
		getContentPane().add(btnAddIc);



		JButton btnRetriveIc = new JButton("");
		java.awt.Image searchImg = new ImageIcon(this.getClass().getResource("/search.png")).getImage();
		btnRetriveIc.setFocusPainted( false );
		btnRetriveIc.setIcon(new ImageIcon(searchImg));
		btnRetriveIc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WaitingDialog waitingDialog = new WaitingDialog( jFrame);
				waitingDialog.setVisible(true);
				deleteErrorMessages();
				boolean searchingSucess=false;
				IdCardEntity idCardEntity = getIdCardEntity();
				ArrayList<String> results=null;
				if (ApplicationMethods.checkIcInsertRulesForSearching(idCardEntity,hashMapErrorMessage)) {
					results = Lucene.searchIc(idCardEntity);
					if (results!=null && results.size()!=0) {
						searchingSucess=true;
					}
					
				} 

				setEnabled(true);
				waitingDialog.dispose();
				if (searchingSucess) {
					new ResultsFrame(results).setVisible(true);	
				} else {
					JOptionPane.showMessageDialog(null, "No identity card found!");
				} 


			}
		});
		btnRetriveIc.setBounds(195, 239, 86, 31);
		getContentPane().add(btnRetriveIc);

		JButton btnQuery = new JButton("Make a query");
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnQuery.setBounds(195, 303, 143, 23);
		getContentPane().add(btnQuery);

		JLabel label_1 = new JLabel("Ic number");
		label_1.setBounds(87, 25, 87, 14);
		getContentPane().add(label_1);

		textFieldIcNumber = new JTextField();
		textFieldIcNumber.setColumns(10);
		textFieldIcNumber.setBounds(184, 22, 86, 20);


		getContentPane().add(textFieldIcNumber);

		JLabel label_2 = new JLabel("Name");
		label_2.setBounds(87, 51, 87, 14);
		getContentPane().add(label_2);

		textFieldName = new JTextField();
		textFieldName.setColumns(10);
		textFieldName.setBounds(184, 48, 86, 20);
		getContentPane().add(textFieldName);

		JLabel label_3 = new JLabel("Surname");
		label_3.setBounds(87, 79, 87, 14);
		getContentPane().add(label_3);

		JLabel label_4 = new JLabel("Birth Date");
		label_4.setBounds(87, 108, 87, 14);
		getContentPane().add(label_4);

		textFieldBirthDate = new JTextField();
		textFieldBirthDate.setColumns(10);
		textFieldBirthDate.setBounds(184, 104, 86, 20);
		getContentPane().add(textFieldBirthDate);

		textFieldSurname = new JTextField();
		textFieldSurname.setColumns(10);
		textFieldSurname.setBounds(184, 76, 86, 20);
		getContentPane().add(textFieldSurname);

		JLabel label_5 = new JLabel("State");
		label_5.setBounds(87, 133, 87, 14);
		getContentPane().add(label_5);

		textFieldState = new JTextField();
		textFieldState.setColumns(10);
		textFieldState.setBounds(184, 130, 86, 20);
		getContentPane().add(textFieldState);

		JLabel label_6 = new JLabel("City");
		label_6.setBounds(87, 161, 87, 14);
		getContentPane().add(label_6);

		textFieldCity = new JTextField();
		textFieldCity.setColumns(10);
		textFieldCity.setBounds(184, 158, 86, 20);
		getContentPane().add(textFieldCity);

		JLabel label_7 = new JLabel("Height (mt.)");
		label_7.setBounds(87, 191, 87, 14);
		getContentPane().add(label_7);

		textFieldHeight = new JTextField();
		textFieldHeight.setColumns(10);
		textFieldHeight.setBounds(184, 188, 86, 20);
		getContentPane().add(textFieldHeight);

		JButton btnImportIcs = new JButton("Import ICs");
		btnImportIcs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ImportIcFrame addIdCardFrame = new ImportIcFrame();
				addIdCardFrame.setVisible(true);
			}
		});
		btnImportIcs.setBounds(31, 303, 143, 23);
		getContentPane().add(btnImportIcs);

		JLabel icNumberError = new JLabel("");
		icNumberError.setHorizontalAlignment(SwingConstants.LEFT);
		icNumberError.setForeground(Color.RED);
		icNumberError.setBounds(273, 11, 83, 42);
		icNumberError.setFont(new Font("Serif", Font.PLAIN, 11));
		getContentPane().add(icNumberError);
		hashMapErrorMessage.put(Constant.IC_NUMBER,icNumberError );

		JLabel nameError = new JLabel("");
		nameError.setForeground(Color.RED);
		nameError.setBounds(273, 35, 83, 41);
		getContentPane().add(nameError);
		hashMapErrorMessage.put(Constant.NAME,nameError );


		JLabel surnameError = new JLabel("");
		surnameError.setForeground(Color.RED);
		surnameError.setBounds(273, 64, 83, 46);
		getContentPane().add(surnameError);
		hashMapErrorMessage.put(Constant.SURNAME,surnameError );


		JLabel birthDateError = new JLabel("");
		birthDateError.setForeground(Color.RED);
		birthDateError.setBounds(273, 87, 83, 46);
		getContentPane().add(birthDateError);
		hashMapErrorMessage.put(Constant.BIRTH_DATE,birthDateError );


		JLabel stateError = new JLabel("");
		stateError.setForeground(Color.RED);
		stateError.setBounds(273, 124, 83, 41);
		getContentPane().add(stateError);
		hashMapErrorMessage.put(Constant.STATE,stateError );


		JLabel cityError = new JLabel("");
		cityError.setForeground(Color.RED);
		cityError.setBounds(273, 144, 83, 42);
		getContentPane().add(cityError);
		hashMapErrorMessage.put(Constant.CITY,cityError );


		JLabel heightError = new JLabel("");
		heightError.setForeground(Color.RED);
		heightError.setBounds(273, 176, 83, 42);
		getContentPane().add(heightError);
		hashMapErrorMessage.put(Constant.HEIGHT,heightError );

		JLabel appImage = new JLabel("");
		java.awt.Image appImg = new ImageIcon(this.getClass().getResource("/carta_identita.jpg")).getImage();
		appImage.setIcon(new ImageIcon(appImg));
		appImage.setBounds(366, 25, 188, 225);
		getContentPane().add(appImage);

	}

	private IdCardEntity getIdCardEntity() {
		IdCardEntity idCardEntity = new IdCardEntity();
		idCardEntity.setIcNumber(textFieldIcNumber.getText());
		idCardEntity.setBirthDate(textFieldBirthDate.getText());
		idCardEntity.setCity(textFieldCity.getText());
		idCardEntity.setHeight(textFieldHeight.getText());
		idCardEntity.setSurname(textFieldSurname.getText());
		idCardEntity.setState(textFieldState.getText());
		idCardEntity.setName(textFieldName.getText());

		return idCardEntity;
	}

	private void deleteErrorMessages() {
		for (JLabel jLabel: hashMapErrorMessage.values()) {
			jLabel.setText("");
		}
	}
}
