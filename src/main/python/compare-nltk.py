#!/usr/bin/env python

import nltk
import csv
import os

def extract_entity_names(t):
    entity_names = []
    if type(t) is nltk.tree.Tree:
        entity_names.append((t.label(), t.leaves()))
                
    return entity_names

def print_entity_names(names):
	for name in names:
		text = ''
		for token in name[1]:
			text += token[0] + ' '
		print '\'{}\' is a {}'.format(text.strip(), name[0])

def main():
	script_dir = os.path.dirname(os.path.realpath(__file__))

	csv_file = open(script_dir + '/../resources/ner_test.csv', 'rb')
	csv_reader = csv.reader(csv_file, delimiter=';', quotechar='\\')

	for row in csv_reader:
		sentence = row[1]

		tokens = nltk.word_tokenize(sentence)
		pos_tags = nltk.pos_tag(tokens)
		ne_chunked = nltk.ne_chunk(pos_tags)

		entity_names = []
		print 'Text: [{}]'.format(sentence)
		for ne_chunk in ne_chunked:
			entity_names.extend(extract_entity_names(ne_chunk))
			
		print_entity_names(entity_names)
		print ''

	csv_file.close()

if __name__ == '__main__':
	main()

