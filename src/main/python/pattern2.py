#!/usr/bin/python
'''
Tests with the pattern
'''

from pattern.en import parse, pprint
s = 'The mobile web is more important than mobile apps.'
with open('text-en') as content_file:
        content = content_file.read()
        s = parse(content, relations=True, lemmata=True)
        pprint(s)
