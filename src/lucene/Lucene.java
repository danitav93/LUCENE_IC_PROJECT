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
	//dfadsfasdfawfqawfq
	
	private static String getQueryForParser(IdCardEntity idCardEntity) {
		return Constant.IC_NUMBER+":"+idCardEntity.getIcNumber()+"^7"+
				" OR "+Constant.NAME+":"+idCardEntity.getName()+"^5"+
				" OR "+Constant.SURNAME+":"+idCardEntity.getSurname()+"^6"+
				" OR "+Constant.BIRTH_DATE+":"+idCardEntity.getBirthDate()+"^4"+
				" OR "+Constant.STATE+":"+idCardEntity.getState()+"^3"+
				" OR "+Constant.CITY+":"+idCardEntity.getCity()+"^2"+
				" OR "+Constant.HEIGHT+":"+idCardEntity.getHeight();
	}
}
