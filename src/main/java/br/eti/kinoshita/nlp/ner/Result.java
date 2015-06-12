package br.eti.kinoshita.nlp.ner;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Result {

	private Map<String, List<EmbeddedToken>> entries = new LinkedHashMap<String, List<EmbeddedToken>>();
	
	public void addEntry(String text, EmbeddedToken token) {
		if (entries.get(text) != null) {
			entries.get(text).add(token);
		} else {
			entries.put(text, Arrays.asList(token));
		}
	}
	
	public Map<String, List<EmbeddedToken>> getEntries() {
		return entries;
	}
	
}
