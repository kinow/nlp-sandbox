from nltk import *
from nltk.corpus import floresta as floresta
from nltk import UnigramTagger as ut
from nltk import BigramTagger as bt

def normalize(s, punctuation="!?.:;,()[] "):
    s = s.decode("iso-8859-1")
    s = s.strip()
    s = s.strip(punctuation)
    return s

with open('sentence-pt') as content_file:
    content = content_file.read()
    content = normalize(content)
    
    tokens = word_tokenize(content)
    
    sents = floresta.tagged_sents()
    
    uni_tag = ut(sents)
    print uni_tag.tag(tokens)
    
    # Split corpus into training and testing set.
    train = int(len(sents)*90/100) # 90%
    
    # Train a bigram tagger with only training data.
    bi_tag = bt(sents[:train])
    
    # Evaluates on testing data remaining 10%
    bi_tag.evaluate(sents[train+1:])
    
    # Using the tagger.
    print bi_tag.tag(tokens)
    
#    tokens = word_tokenize(content)
#    print tokens
#    
#    tagged = pos_tag(tokens)
#    
#    entities = chunk.ne_chunk(tagged)
#    print entities


#    s = parse(content, lemmata=True, tokenize=False, chunks=True, tags=True, light=False)
#    print s
#    s = Sentence(s)
#    m = match('harassment against {DT? consul? general? IN? brazil? IN? NNP?} americo fontenelle', s)
#    print m
#    
#    if m != None:
#        for w in m.words:
#            print w
