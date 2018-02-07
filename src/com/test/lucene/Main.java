package com.test.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class Main {

	String indexDir = "C:\\TEMP\\IDX";
	String dataDir = "C:\\TEMP\\DOCS";
	
	Indexer indexer;
	Searcher searcher;
	
	public static void main(String[] args) throws IOException, ParseException {
		Main main = new Main();
		main.clearIdxDir(main.indexDir);
		main.createIdx();
		main.search("lero");
	}

	private void createIdx() throws IOException{
		indexer = new Indexer(indexDir);
		int indexed = indexer.createIndex(dataDir, new TextFileFilter());
		indexer.close();
		System.out.println("Indexed files: " + indexed);
	}
	
	private void search(String searchQuery) throws IOException, ParseException{
		searcher = new Searcher(indexDir);
		TopDocs hits = searcher.search(searchQuery);
		
		System.out.println("Found: " + hits.totalHits + " documents with search string " + searchQuery);
		
		for(ScoreDoc scoreDoc : hits.scoreDocs){
			Document doc = searcher.getDocument(scoreDoc);
			System.out.println("Document found: " + doc.get(LuceneConstants.FILE_PATH));
		}
	}
	
	private void clearIdxDir(String indexDir) {
		final File[] files = new File(indexDir).listFiles();
		if (files != null) {
			for (File f : files) {
				f.delete();
			}
		}
	}
}
