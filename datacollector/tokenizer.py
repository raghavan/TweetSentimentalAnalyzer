import csv


def writeToFile(list,filename):
 f = open(filename, 'w')
 for item in list:
   f.write("%s\n" % item)

tweets = open("tweets.csv", "r");
obama = []
romney= []
unknown = []
for tweet in tweets:   
   tweetInLowerCase = tweet.lower();
   words = tweetInLowerCase.split(' ');
   print(words);
   if 'obama' in words or 'barrack' in words:
       obama.append(tweet);
   elif 'mitt' in words or 'romney' in words:
       romney.append(tweet);
   else:
       unknown.append(tweet);

writeToFile(obama,'obama.txt');
writeToFile(romney,'romney.txt');
writeToFile(unknown,'unknown.txt');

