package lucene;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import utility.ApplicationMethods;
import utility.Constant;
import entity.IdCardEntity;

/**
 * 
 * @author Daniele
 * This class implements the indexing of the id card using lucene
 */
public class Lucene {

	private static StandardAnalyzer analyzer;
	private static Directory index;

	public static boolean indexIc(IdCardEntity idCardEntity) {
		try {
			if (analyzer==null) {
				analyzer = new StandardAnalyzer();
			}
			if (index==null) {
				index = new RAMDirectory();
			}
			IndexWriterConfig config = new IndexWriterConfig(analyzer);

			IndexWriter w = new IndexWriter(index, config);
			addDoc(w,idCardEntity);
			w.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static void addDoc(IndexWriter w, IdCardEntity idCardEntity) throws IOException {
		Document doc = new Document();

		doc.add(new StringField(Constant.IC_NUMBER.toString(), idCardEntity.getIcNumber(), Field.Store.YES));
		doc.add(new TextField(Constant.NAME.toString(), idCardEntity.getName(), Field.Store.YES));
		doc.add(new TextField(Constant.SURNAME.toString(), idCardEntity.getSurname(), Field.Store.YES));
		doc.add(new TextField(Constant.BIRTH_DATE.toString(), idCardEntity.getBirthDate(), Field.Store.YES));
		doc.add(new TextField(Constant.STATE.toString(), idCardEntity.getState(), Field.Store.YES));
		doc.add(new TextField(Constant.CITY.toString(), idCardEntity.getCity(), Field.Store.YES));
		doc.add(new StringField(Constant.HEIGHT.toString(), idCardEntity.getHeight(), Field.Store.YES));

		w.addDocument(doc);
	}

	public static ArrayList<String> searchIc(IdCardEntity idCardEntity) {
		try {
			ArrayList<String> listToReturn = new ArrayList<>();

			Query q = new QueryParser(Constant.IC_NUMBER.toString(),analyzer).parse(getQueryForParser(idCardEntity));
			int hitsPerPage = 10;
			IndexReader reader = DirectoryReader.open(index);
			IndexSearcher searcher = new IndexSearcher(reader);
			TopDocs docs = searcher.search(q, hitsPerPage);
			ScoreDoc[] hits = docs.scoreDocs;
			for(int i=0;i<hits.length;++i) {
				int docId = hits[i].doc;
				Document d = searcher.doc(docId);
				listToReturn.add((i + 1) + ". " + d.get(Constant.IC_NUMBER.toString()) 
				+ "     " + d.get(Constant.NAME.toString())
				+ "     " + d.get(Constant.SURNAME.toString()));
			}
			reader.close();
			return listToReturn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}


	private static String getQueryForParser(IdCardEntity idCardEntity) {
		
		String stringToReturn = new String("");
		
		
		boolean firstWritten=false;
		
		if (!idCardEntity.getIcNumber().equals("") ) {
			stringToReturn = stringToReturn + Constant.IC_NUMBER+":"+idCardEntity.getIcNumber()+"~"+Constant.zero+"^7";
			firstWritten = true;
		} 
		
		
		if (!idCardEntity.getName().equals("")) {
			String appo;
			if (firstWritten) {
				appo = " OR ";
			} else {
				appo = "";
				firstWritten=true;
			}
			stringToReturn = stringToReturn + appo+Constant.NAME+":"+idCardEntity.getName()+"~"+Constant.zero+"^5";

		}
		
		
		if (!idCardEntity.getSurname().equals("")) {
			String appo;
			if (firstWritten) {
				appo = " OR ";
			} else {
				appo = "";
				firstWritten=true;
			}
			stringToReturn = stringToReturn +appo+Constant.SURNAME+":"+idCardEntity.getSurname()+"~"+Constant.zero+"^6";
		}
		
		if (!idCardEntity.getBirthDate().equals("")) {
			String appo;
			if (firstWritten) {
				appo = " OR ";
			} else {
				appo = "";
				firstWritten=true;
			}
			stringToReturn = stringToReturn + appo+Constant.BIRTH_DATE+":"+idCardEntity.getBirthDate()+"~"+Constant.zero+"^4";
		}
		
		if (!idCardEntity.getState().equals("")) {
			String appo;
			if (firstWritten) {
				appo = " OR ";
			} else {
				appo = "";
				firstWritten=true;
			}
			stringToReturn = stringToReturn + appo+Constant.STATE+":"+idCardEntity.getState()+"~"+Constant.zero+"^3";
		}
		
		
		if (!idCardEntity.getCity().equals("")) {
			String appo;
			if (firstWritten) {
				appo = " OR ";
			} else {
				appo = "";
				firstWritten=true;
			}
			stringToReturn = stringToReturn + appo+Constant.CITY+":"+idCardEntity.getCity()+"~"+Constant.zero+"^2";
		}
		
		
		if (!idCardEntity.getHeight().equals("")) {
			String appo;
			if (firstWritten) {
				appo = " OR ";
			} else {
				appo = "";
				firstWritten=true;
			}
			stringToReturn = stringToReturn + appo+Constant.HEIGHT+":"+idCardEntity.getHeight()+"~"+Constant.zero;
		}

	return stringToReturn;
	}

	/**
	 * this method insert many ics in the index
	 */
	public static boolean insertAutogeneratedICs() {
		try {
			if (analyzer==null) {
				analyzer = new StandardAnalyzer();
			}
			if (index==null) {
				index = new RAMDirectory();
			}
			IndexWriterConfig config = new IndexWriterConfig(analyzer);

			IndexWriter w = new IndexWriter(index, config);
			
			for (int i=0;i<Constant.NumberAutogeneratedICs;i++) {
				if(!addAutogeneratedDoc(w)) {
					return false;	
				}
			}
			w.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private static boolean addAutogeneratedDoc(IndexWriter w) throws IOException {
		Document doc = new Document();
		
		String ic_number = ApplicationMethods.getAutoGenerated(Constant.IC_NUMBER);
		if (ic_number!=null) {
			doc.add(new StringField(Constant.IC_NUMBER.toString(),ic_number , Field.Store.YES));
		} else {
			return false;
		}
		
		String name = ApplicationMethods.getAutoGenerated(Constant.NAME);
		if (name!=null) {
			doc.add(new StringField(Constant.NAME.toString(),name, Field.Store.YES));
		} else {
			return false;
		}
		
		String surname = ApplicationMethods.getAutoGenerated(Constant.SURNAME);
		if (surname!=null) {
			doc.add(new StringField(Constant.SURNAME.toString(),surname , Field.Store.YES));
		} else {
			return false;
		}
		String birthDate = ApplicationMethods.getAutoGenerated(Constant.BIRTH_DATE);
		if (birthDate!=null) {
			doc.add(new StringField(Constant.BIRTH_DATE.toString(),birthDate , Field.Store.YES));
		} else {
			return false;
		}
		String state = ApplicationMethods.getAutoGenerated(Constant.STATE);
		if (state!=null) {
			doc.add(new StringField(Constant.STATE.toString(),state , Field.Store.YES));
		} else {
			return false;
		}
		String city = ApplicationMethods.getAutoGenerated(Constant.CITY);
		if (city!=null) {
			doc.add(new StringField(Constant.CITY.toString(),city , Field.Store.YES));
		} else {
			return false;
		}
		String height = ApplicationMethods.getAutoGenerated(Constant.HEIGHT);
		if (height!=null) {
			doc.add(new StringField(Constant.HEIGHT.toString(),height , Field.Store.YES));
		} else {
			return false;
		}
		
		w.addDocument(doc);
		return true;
	}

	public static Directory getIndex() {
		return index;
	}
	
	
}
