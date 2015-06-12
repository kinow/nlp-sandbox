package br.eti.kinoshita.nlp.ner;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Result {

	private Map<String, List<EmbeddedToken>> entries = new LinkedHashMap<String, List<EmbeddedToken>>();
	
	public void addEntry(String text, EmbeddedToken token) {
		if (entries.get(text) != null) {
			entries.get(text).add(token);
		} else {
			entries.put(text, new ArrayList<EmbeddedToken>(Arrays.asList(token)));
		}
	}
	
	public Map<String, List<EmbeddedToken>> getEntries() {
		return entries;
	}

	public void printTo(PrintStream out) {
		for(Entry<String, List<EmbeddedToken>> entry : entries.entrySet()) {
			out.println(String.format("Text: [%s]", entry.getKey()));
			List<EmbeddedToken> tokens = entry.getValue();
			for (EmbeddedToken token : tokens) {
				out.println(String.format("'%s' is a %s", token.getName(), token.getValue()));
			}
			out.println();
		}
	}
	
}
