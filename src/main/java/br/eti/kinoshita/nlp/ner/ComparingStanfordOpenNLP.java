package br.eti.kinoshita.nlp.ner;

import java.io.File;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.QuoteMode;
import org.apache.commons.lang.StringUtils;


public class ComparingStanfordOpenNLP {

	private static void testOpennlpNER(CSVParser csv) throws Exception {
		URI tokenModelInput = ComparingStanfordOpenNLP.class.
				getResource("/models/opennlp/en-us/tokenizer/en-token.bin").toURI();
		TokenizerModel tokenModel = new TokenizerModel(tokenModelInput.toURL());
		TokenizerME tokenizer = new TokenizerME(tokenModel);
		
		URI nerModelInput = ComparingStanfordOpenNLP.class.
				getResource("/models/opennlp/en-us/ner/en-ner-person.bin").toURI();
		TokenNameFinderModel nerModel = new TokenNameFinderModel(nerModelInput.toURL());
		NameFinderME ner = new NameFinderME(nerModel);

		csv.forEach(record -> {
			String text = record.get(1);
			text = StringUtils.replace(text, "\\\"", "\"");
			System.out.println(text);
			String[] tokens = tokenizer.tokenize(text);
			String toText = Arrays.toString(tokens);
			System.out.println(toText);
			Span[] spans = ner.find(tokens);
			
			System.out.println("Names:");
			String[] names = Span.spansToStrings(spans, tokens);
			for (String name: names) {
				System.out.println(name);
			}
			System.out.println();
			
			ner.clearAdaptiveData();
		});
	}
	
	private static void testStanfordNER(CSVParser csv) {
		
	}
	
	public static void main(String[] args) throws Exception {
		URI csvInput = ComparingStanfordOpenNLP.class.
				getResource("/ner_test.csv").toURI();
		File csvFile = new File(csvInput);
		CSVParser csv = CSVParser.parse(csvFile, Charset.defaultCharset(), 
				CSVFormat
					.DEFAULT
					.withIgnoreSurroundingSpaces()
					.withDelimiter(';')
					.withQuote('"')
					.withQuoteMode(QuoteMode.MINIMAL)
		);
		testOpennlpNER(csv);
	}
	
}
