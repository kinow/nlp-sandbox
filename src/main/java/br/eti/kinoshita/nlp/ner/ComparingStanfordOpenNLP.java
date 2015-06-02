package br.eti.kinoshita.nlp.ner;

import java.io.File;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.apache.commons.lang.StringUtils;

import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class ComparingStanfordOpenNLP {

	private static void testOpennlpNER(List<CSVRecord> rows) throws Exception {
		URI tokenModelInput = ComparingStanfordOpenNLP.class.getResource(
				"/models/opennlp/en-us/tokenizer/en-token.bin").toURI();
		TokenizerModel tokenModel = new TokenizerModel(tokenModelInput.toURL());
		TokenizerME tokenizer = new TokenizerME(tokenModel);

		URI nerModelInput = ComparingStanfordOpenNLP.class.getResource(
				"/models/opennlp/en-us/ner/en-ner-person.bin").toURI();
		TokenNameFinderModel nerModel = new TokenNameFinderModel(
				nerModelInput.toURL());
		NameFinderME ner = new NameFinderME(nerModel);

		rows.forEach(record -> {
			String text = record.get(1);
			text = StringUtils.replace(text, "\\\"", "\"");
			System.out.println(text);
			String[] tokens = tokenizer.tokenize(text);
			String toText = Arrays.toString(tokens);
			System.out.println(toText);
			Span[] spans = ner.find(tokens);

			System.out.println("Names:");
			String[] names = Span.spansToStrings(spans, tokens);
			for (String name : names) {
				System.out.println(name);
			}
			System.out.println();

			ner.clearAdaptiveData();
		});
	}
	
	//http://www.informit.com/articles/article.aspx?p=2265404
	private static void testStanfordNER(List<CSVRecord> rows) {
		Properties props = new Properties();
		props.setProperty("annotators",
				"tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		// props.setProperty("pos.model",
		// "pos-tagger/english-bidirectional/english-bidirectional-distsim.tagger");
		// props.setProperty("ner.model",
		// "ner/english.all.3class.distsim.crf.ser.gz");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		rows.forEach(record -> {
			String text = record.get(1);
			text = StringUtils.replace(text, "\\\"", "\"");
			System.out.println(text);
			Annotation annotation = new Annotation(text);
			pipeline.annotate(annotation);
			List<CoreMap> sentences = annotation.get(SentencesAnnotation.class);
			StringBuilder sb = new StringBuilder();
			List tokens = new ArrayList<>();
			sentences.forEach(sentence -> {
				String prevNeToken = "O";
				String currNeToken = "O";
				boolean newToken = true;
				for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
					currNeToken = token.get(NamedEntityTagAnnotation.class);
					String word = token.get(TextAnnotation.class);
					// Strip out "O"s completely, makes code below easier to
					// understand
					if (currNeToken.equals("O")) {
						// LOG.debug("Skipping '{}' classified as {}", word,
						// currNeToken);
						if (!prevNeToken.equals("O") && (sb.length() > 0)) {
							handleEntity(prevNeToken, sb, tokens);
							newToken = true;
						}
						continue;
					}

					if (newToken) {
						prevNeToken = currNeToken;
						newToken = false;
						sb.append(word);
						continue;
					}

					if (currNeToken.equals(prevNeToken)) {
						sb.append(" " + word);
					} else {
						// We're done with the current entity - print it out and
						// reset
						// TODO save this token into an appropriate ADT to
						// return for useful processing..
						handleEntity(prevNeToken, sb, tokens);
						newToken = true;
					}
					prevNeToken = currNeToken;
				}
			});
		});
	}

	private static void handleEntity(String inKey, StringBuilder inSb,
			List inTokens) {
		System.out.println(String.format("'%s' is a %s", inSb, inKey));
		inTokens.add(new EmbeddedToken(inKey, inSb.toString()));
		inSb.setLength(0);
	}

	public static void main(String[] args) throws Exception {
		URI csvInput = ComparingStanfordOpenNLP.class.getResource(
				"/ner_test.csv").toURI();
		File csvFile = new File(csvInput);
		CSVParser csv = CSVParser.parse(
				csvFile,
				Charset.defaultCharset(),
				CSVFormat.DEFAULT.withIgnoreSurroundingSpaces()
						.withDelimiter(';').withQuote('"')
						.withQuoteMode(QuoteMode.MINIMAL));
		List<CSVRecord> rows = Collections.unmodifiableList(csv.getRecords());
		testOpennlpNER(rows);
		System.out.println("-------");
		testStanfordNER(rows);
	}

	static class EmbeddedToken {

		private String name;
		private String value;

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		public EmbeddedToken(String name, String value) {
			super();
			this.name = name;
			this.value = value;
		}
	}

}
