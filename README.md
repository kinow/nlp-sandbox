# NLP Sandbox

Playing with NLP in Java and Python, using the CLiPS-Pattern, NLTK, OpenNLP and Stanford CoreNLP.

The Java and Python code are under src/main. Following are the description of the packages.

TODO:

* Normalise output labels (NAME, DATE, ORDINAL, etc)
* Compare F1 score for each implementation
* Write comparison about algorithm, models and data used in each library
* Get more (volume, diversity) data
* Include performance stats, such as time to train and/or time to parse

## Java

### br.eti.kinoshita.nlp.ner

The idea here is to compare OpenNLP, StanfordCoreNLP and other Java libraries
regarding NER. I used the models shipped with OpenNLP and CoreNLP (IOW, no training
or new corpora)

## Python

### src/main/python/compare-nltk.py

This is the Python NLTK equivalent of what was done with OpenNLP and StanfordCoreNLP

# Data used

So far I have used only five news from NZ Herald. You can find them in src/main/resources/ner_test.csv

# Comparing results

The CSV input file contained columns that would be used to compare the results, but I decided not to use them and investigate how OpenNLP is measuring the F1 score, and perhaps normalize the tag labels (LOCATION, DATE, MONEY, etc) and then measure the results.

## Java

### OpenNLP

Text: [At just 22-years-old, Brandon Lipman bought his first property in Hamilton in April.]
'April' is a DATE
'Hamilton' is a LOCATION

Text: ["Most students are wondering how they'll afford next week's rent or ever pay back their mounting loan. Mr Lipman's story is the exception, not the reality," said association president Rory McCourt.]
'next week' is a DATE

Text: [The University of Auckland commerce and science student saved for a $45,000 deposit for the $300,000 investment property by working from 9pm to 5am at Countdown for a year, and by living at his parent's house rent-free for two years.]
'$ 45,000' is a MONEY
'$' is a MONEY
'University of Auckland' is a ORG

Text: [The New Zealand Students' Association said Mr Lipman's story did not represent the financial hardships faced by many students.]
'New Zealand Students ' Association' is a ORG

Text: [Martin Duncan is the owner of Freestyle Experiences, a Sunshine Coast food ambassador and a supporter of local food producers.]
'Martin Duncan' is a NAME

### CoreNLP

Text: [At just 22-years-old, Brandon Lipman bought his first property in Hamilton in April.]
'22-years-old' is a DURATION
'Brandon Lipman' is a PERSON
'first' is a ORDINAL
'Hamilton' is a LOCATION
'April' is a DATE

Text: [The University of Auckland commerce and science student saved for a $45,000 deposit for the $300,000 investment property by working from 9pm to 5am at Countdown for a year, and by living at his parent's house rent-free for two years.]
'University of Auckland' is a ORGANIZATION
'$ 45,000' is a MONEY
'$ 300,000' is a MONEY
'9pm to 5am' is a TIME
'a year' is a DURATION
'two years' is a DURATION

Text: [The New Zealand Students' Association said Mr Lipman's story did not represent the financial hardships faced by many students.]
'New Zealand Students ' Association' is a ORGANIZATION
'Lipman' is a PERSON

Text: ["Most students are wondering how they'll afford next week's rent or ever pay back their mounting loan. Mr Lipman's story is the exception, not the reality," said association president Rory McCourt.]
'next week' is a DATE
'Lipman' is a PERSON
'Rory McCourt' is a PERSON

Text: [Martin Duncan is the owner of Freestyle Experiences, a Sunshine Coast food ambassador and a supporter of local food producers.]
'Martin Duncan' is a PERSON
'Sunshine Coast' is a LOCATION

## Python

### NLTK

Text: [At just 22-years-old, Brandon Lipman bought his first property in Hamilton in April.]
'Brandon Lipman' is a PERSON
'Hamilton' is a GPE

Text: [The University of Auckland commerce and science student saved for a $45,000 deposit for the $300,000 investment property by working from 9pm to 5am at Countdown for a year, and by living at his parent's house rent-free for two years.]
'University' is a ORGANIZATION
'Auckland' is a GPE
'Countdown' is a ORGANIZATION

Text: [The New Zealand Students' Association said Mr Lipman's story did not represent the financial hardships faced by many students.]
'New Zealand Students' is a ORGANIZATION
'Association' is a PERSON
'Lipman' is a PERSON

Text: ["Most students are wondering how they'll afford next week's rent or ever pay back their mounting loan. Mr Lipman's story is the exception, not the reality," said association president Rory McCourt.]
'Lipman' is a PERSON
'Rory McCourt' is a PERSON

Text: [Martin Duncan is the owner of Freestyle Experiences, a Sunshine Coast food ambassador and a supporter of local food producers.]
'Martin' is a PERSON
'Duncan' is a ORGANIZATION
'Freestyle Experiences' is a ORGANIZATION
'Sunshine Coast' is a ORGANIZATION

### Pattern

Text: [At just 22-years-old, Brandon Lipman bought his first property in Hamilton in April.]

Text: [The University of Auckland commerce and science student saved for a $45,000 deposit for the $300,000 investment property by working from 9pm to 5am at Countdown for a year, and by living at his parent's house rent-free for two years.]

Text: [The New Zealand Students' Association said Mr Lipman's story did not represent the financial hardships faced by many students.]

Text: ["Most students are wondering how they'll afford next week's rent or ever pay back their mounting loan. Mr Lipman's story is the exception, not the reality," said association president Rory McCourt.]

Text: [Martin Duncan is the owner of Freestyle Experiences, a Sunshine Coast food ambassador and a supporter of local food producers.]