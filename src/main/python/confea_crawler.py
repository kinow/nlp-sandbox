#!/usr/bin/python

from HTMLParser import HTMLParser
import requests

class MyParser(HTMLParser):
    
    def __init__(self):
        HTMLParser.__init__(self)
        self.results = []
    
    def handle_starttag(self, tag, attrs):
        if(tag == 'a'):
            for _, value in attrs:
                if(value.find('visualiza.asp') >= 0 and value.find('idTiposEmentas') > 0):
                    self.results.append(value)

proxies = {
  "http": "http://localhost:3128",
  "https": "http://localhost:3128",
}

with open('confea_names', 'r') as f:
    names = f.readlines()
    
    for name in names:
        parser = MyParser()
        url = 'http://normativos.confea.org.br/ementas/lista_ementas.asp'
        payload = {'idTiposEmentas': '4, 2, 3, 5, 1, 6, 9', 'PalavraChave': ''+ name +'', 'buscarem': 'conteudo'}
        r = requests.post(url, payload, proxies=proxies)
        parser.feed(r.content)
        
        if len(parser.results) > 0:
            for result in parser.results: 
                result_url = 'http://normativos.confea.org.br/ementas/' + result
                print result_url
