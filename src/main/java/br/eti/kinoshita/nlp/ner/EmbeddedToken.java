package br.eti.kinoshita.nlp.ner;

public class EmbeddedToken {

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
