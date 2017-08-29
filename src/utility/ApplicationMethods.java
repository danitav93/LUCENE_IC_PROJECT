package utility;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JLabel;

import entity.IdCardEntity;

public class ApplicationMethods {
	
	

	/**
	 * rules for adding the id card
	 * @param idCardEntity: the entity of the id card to be inserted
	 * @param hashMapErrorsLabel: hash map of error label messages
	 * @return true if rules are satisfied
	 */
	public static boolean checkIcInsertRules(IdCardEntity idCardEntity, HashMap<Integer,JLabel> hashMapErrorMessage) {

		//Number rule
		if ( !(idCardEntity.getIcNumber().length()==9)) {
			hashMapErrorMessage.get(Constant.IC_NUMBER).setText("<html>Must contain<br>9 characters");
			return false;
		}

		//Name rule
		if (idCardEntity.getName().equals("")) {
			hashMapErrorMessage.get(Constant.NAME).setText("<html>Name must be<br>non empty");
			return false;
		}

		//Surname rule
		if (idCardEntity.getSurname().equals("")) {
			hashMapErrorMessage.get(Constant.SURNAME).setText("<html>Surname must be<br>non empty");
			return false;
		}

		//Date rule
		if (!Methods.isValidDate(idCardEntity.getBirthDate())) {
			hashMapErrorMessage.get(Constant.BIRTH_DATE).setText("<html>Date format<br>aaaa/mm/dd");
			return false;
		}

		//State rule
		if (idCardEntity.getState().equals("")) {
			hashMapErrorMessage.get(Constant.STATE).setText("<html>State must be<br>non empty");
			return false;
		}

		//City rule
		if (idCardEntity.getCity().equals("")) {
			hashMapErrorMessage.get(Constant.CITY).setText("<html>City must be<br>non empty");
			return false;
		}

		//Height rule
		if (idCardEntity.getHeight().equals("")) {
			hashMapErrorMessage.get(Constant.HEIGHT).setText("<html>Height must be<br>non empty");
			return false;
		}
		return true;
	}

	/**
	 * rules for searching ic cards
	 * @param idCardEntity 
	 * @param hashMapErrorMessage
	 * @return
	 */
	public static boolean checkIcInsertRulesForSearching(IdCardEntity idCardEntity,HashMap<Integer, JLabel> hashMapErrorMessage) {
		
		boolean[] arrayOk =new boolean[8];

		//Number rule
		if (!idCardEntity.getIcNumber().equals("")) {
			return true;
		}

		//Name rule
		if (!idCardEntity.getName().equals("")) {
			return true;
		}

		//Surname rule
		if (!idCardEntity.getSurname().equals("")) {
			return true;
		}

		//Date rule
		if (Methods.isValidDate(idCardEntity.getBirthDate())) {
			return true;
		}else if (!idCardEntity.getBirthDate().equals("")){
			arrayOk[Constant.BIRTH_DATE]=true;
		}

		//State rule
		if (!idCardEntity.getState().equals("")) {
			return true;
		}

		//City rule
		if (!idCardEntity.getCity().equals("")) {
			return true;
		}

		//Height rule
		if (!idCardEntity.getHeight().equals("")) {
			return true;
		}
		
		if (arrayOk[Constant.BIRTH_DATE]) {
			hashMapErrorMessage.get(Constant.BIRTH_DATE).setText("<html>Date format<br>aaaa/mm/dd");
		}
		
		return false;
	}

	/**
	 * ritorna un valore random a seconda del field
	 * @param field
	 * @return
	 */
	public static String getAutoGenerated(Integer field) {
		
	
		try {
		Random randomGenerator = new Random();
		
		FileReader fr ; 
		BufferedReader br ;
		
		
		if (field.equals(Constant.IC_NUMBER)) {
			return new Integer(randomGenerator.nextInt(999999999-100000000)+100000000).toString();
		}
		
		if (field.equals(Constant.NAME)) {
			fr = new FileReader("C:\\Users\\Daniele Tavernelli\\eclipse-workspace\\LUCENE_IC_PROJECT\\files\\names.txt");
			br = new BufferedReader(fr);
			Integer selectedNameIndex= randomGenerator.nextInt(Constant.NumberAutogeneratedNames);
			for (int i=0;i<selectedNameIndex-1;i++) {
				br.readLine();
			}
			String stringToReturn= br.readLine();
			fr.close();
			br.close();
			return stringToReturn;
		}
		
		if (field.equals(Constant.SURNAME)) {
			fr = new FileReader("C:\\Users\\Daniele Tavernelli\\eclipse-workspace\\LUCENE_IC_PROJECT\\files\\surname.txt");
			br = new BufferedReader(fr);
			Integer selectedNameIndex= randomGenerator.nextInt(Constant.NumberAutogeneratedSurnames);
			for (int i=0;i<selectedNameIndex-1;i++) {
				br.readLine();
			}
			String stringToReturn= br.readLine();
			fr.close();
			br.close();
			return stringToReturn;
		}
		
		if (field.equals(Constant.BIRTH_DATE)) {		
			Integer year= randomGenerator.nextInt(Constant.MaxYearBirthDate-Constant.MinYearBirthDate)+Constant.MinYearBirthDate;
			Integer month= randomGenerator.nextInt(12);
			Integer day= randomGenerator.nextInt(28);
			return year+"/"+month+"/"+day;	
		}
		
		if (field.equals(Constant.STATE)) {
			return "Italy";
		}

		if (field.equals(Constant.CITY)) {
			fr = new FileReader("C:\\Users\\Daniele Tavernelli\\eclipse-workspace\\LUCENE_IC_PROJECT\\files\\comuni.txt");
			br = new BufferedReader(fr);
			Integer selectedNameIndex= randomGenerator.nextInt(Constant.NumberAutogeneratedSurnames);
			for (int i=0;i<selectedNameIndex-1;i++) {
				br.readLine();
			}
			String stringToReturn= br.readLine();
			fr.close();
			br.close();
			return stringToReturn;
		}
		
		if (field.equals(Constant.HEIGHT)) {		
			Integer cm= randomGenerator.nextInt(90-40)+40;
			return "1."+cm;	
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	


}
