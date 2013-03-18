import re


def writeToFile(list,filename):
 f = open(filename, 'w')
 for item in list:
   f.write("%s\n" % item)

tweets = open("tweets.csv", "r");
obama = []
romney= []
unknown = []
for tweet in tweets: 
   cleanedTweet = re.sub(r"<(.*?)>", " ",tweet);

   for ch in ['&','#','?',':','@','\'','!','-',',','/',';','(',')','\\','"','.','=']: 
      if ch in cleanedTweet:
         cleanedTweet=cleanedTweet.replace(ch," ")

   tweetInLowerCase = cleanedTweet.lower() 
   words = tweetInLowerCase.split(' ');

   #print(words);
   if 'obama' in words or 'barrack' in words or 'barackobama' in words:
       obama.append(cleanedTweet);
   elif 'mitt' in words or 'romney' in words or 'mittromney' in words :
       romney.append(cleanedTweet);
   else:
       unknown.append(cleanedTweet);

writeToFile(obama,'obama.txt');
writeToFile(romney,'romney.txt');
writeToFile(unknown,'unknown.txt');

