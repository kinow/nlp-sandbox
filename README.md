# NLP Sandbox

Playing with NLP in Java and Python, using the CLiPS-Pattern, NLTK, OpenNLP and Stanford CoreNLP.

## br.eti.kinoshita.nlp.ner

The idea is to compare OpenNLP, StanfordCoreNLP and other Java libraries with regards to
the NER models.

## src/main/python/compare-nltk.py

This is the Python NLTK equivalent of what was done with OpenNLP and StanfordCoreNLP

## Comparing results

The CSV input file contained columns that would be used to compare the results, but I decided not to use them and investigate how OpenNLP is measuring the F1 score, and perhaps normalize the tag labels (LOCATION, DATE, MONEY, etc) and then measure the results.
