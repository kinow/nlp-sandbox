#!/usr/bin/python
'''
Tests with the pattern
'''

from pattern.en import parse, pprint
s = 'The mobile web is more important than mobile apps.'
s = parse(s, relations=True, lemmata=True)
pprint(s)