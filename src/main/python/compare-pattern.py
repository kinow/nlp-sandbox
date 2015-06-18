#!/usr/bin/env python

from pattern.en import parse
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

		tokens = parse(sentence)

		entity_names = []
		print 'Text: [{}]'.format(sentence)
		# for chunck in chuncked:
		# 	entity_names.extend(extract_entity_names(chunck))
			
		# print_entity_names(entity_names)
		print ''

	csv_file.close()

if __name__ == '__main__':
	main()

