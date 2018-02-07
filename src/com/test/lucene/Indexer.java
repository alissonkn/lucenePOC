package com.test.lucene;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {

	private IndexWriter writer;
	
	public Indexer(String indexDirectoryPath) throws IOException {
		
		//Indexes directory
		Directory indexDirectory = FSDirectory.open(new File(indexDirectoryPath).toPath());
		
		//Create the Indexer
		writer = new IndexWriter(indexDirectory, new IndexWriterConfig(new StandardAnalyzer()));
	}
	
	public void close() throws IOException{
		writer.close();
	}
	
	private Document getDocument(File file) throws IOException{
		
		Document doc = new Document();
		
		TextField contentField = new TextField(LuceneConstants.CONTENTS, new FileReader(file));
		TextField fileNameField = new TextField(LuceneConstants.FILE_NAME, file.getName(), Field.Store.YES);
		TextField filePathField = new TextField(LuceneConstants.FILE_PATH, file.getCanonicalPath(), Field.Store.YES);
		
		doc.add(contentField);
		doc.add(fileNameField);
		doc.add(filePathField);
		
		return doc;
		
	}
	
	public int createIndex(String dataDirPath, FileFilter filter) throws IOException{
		
		File[] files = new File(dataDirPath).listFiles();
		
		for(File file : files){
			if(!file.isDirectory() && !file.isHidden() && file.exists() && file.canRead() && filter != null){
				System.out.println("Indexing file: " + file.getCanonicalPath());
				Document doc = getDocument(file);
				writer.addDocument(doc);
			}
		}
		
		return writer.numDocs();
	}
	
	
	
	
	
}
