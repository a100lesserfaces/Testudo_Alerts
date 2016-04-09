import requests
import sys
 
from lxml import html
 
#link = "'" + sys.argv[1] + "'"
#print link
 
#page = requests.get(link)
page = requests.get('https://alexandriabendebba.wordpress.com')
tree = html.fromstring(page.content)
data = tree.xpath('//@src')
 
print len(data)
print data