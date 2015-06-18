#!/usr/bin/python
'''
Tests with the pattern
'''

from pattern.search import search, match
from pattern.en import Sentence, parse

def normalize(s, punctuation="!?.:;,()[] "):
    s = s.decode("utf-8")
    s = s.strip()
    s = s.strip(punctuation)
    return s

with open('sentence-en') as content_file:
    content = content_file.read()
    content = normalize(content)
    
    print content
    s = parse(content, lemmata=True, tokenize=False, chunks=True, tags=True, light=False)
    print s
    s = Sentence(s)
    m = match('harassment against {DT? consul? general? IN? brazil? IN? NNP?} americo fontenelle', s)
    print m
    
    if m != None:
        # TODO: mark name as accused of harassment
        for w in m.words:
            print w
