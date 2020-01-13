Implementation of Url Hit Counter in Java.

You can use any language of choice, e.g. Java, C++, Scala, Python, Ruby, etc. The exercise has no time limit. Please submit the solution and build instruction if applicable to exercise@nyansa.com via Google Drive/Dropbox/Github (preferred) or as attachment.


Problem:

You’re given an input file. Each line consists of a timestamp (unix epoch in seconds) and a url separated by ‘|’ (pipe operator). The entries are not in any chronological order. Your task is to produce a daily summarized report on url hit count, organized daily (mm/dd/yyyy GMT) with the earliest date appearing first. For each day, you should display the number of times each url is visited in the order of highest hit count to lowest count. Your program should take in one command line argument: input file name. The output should be printed to stdout. You can assume that the cardinality (i.e. number of distinct values) of hit count values and the number of days are much smaller than the number of unique URLs. You may also assume that number of unique URLs can fit in memory, but not necessarily the entire file.


input.txt

1407564301|www.nba.com
1407478021|www.facebook.com
1407478022|www.facebook.com
1407481200|news.ycombinator.com
1407478028|www.google.com
1407564301|sports.yahoo.com
1407564300|www.cnn.com
1407564300|www.nba.com
1407564300|www.nba.com
1407564301|sports.yahoo.com
1407478022|www.google.com
1407648022|www.twitter.com


Output

08/08/2014 GMT
www.facebook.com 2
www.google.com 2
news.ycombinator.com 1
08/09/2014 GMT
www.nba.com 3
sports.yahoo.com 2
www.cnn.com 1
08/10/2014 GMT
www.twitter.com 1


Correctness, efficiency (speed and memory) and code cleanliness will be evaluated. Please provide a complexity analysis in Big-O notation for your program along with your source.

**Clone**

`git@github.com:apoorvam1/UrlHitCounter.git`


Instructions to build
From the parent folder of project run below commands
*  `gradle clean build`                   :  builds the project
*  `gradle fatJar`                       :  builds the jar file in build/libs folder
*  `java -jar <jar-file> <input-file>`   :  runs the application with the given input file


Design decisions are based on the below assumptions
1. Application reads 100K lines from the file before it refreshes the internal cache.
2. Assuming average record size of 80bytes, 100K records will need 8MB of memory.
   Depending on the resources available the number mentioned in (1) can be configured through the
   configuration file. The property name is 'readlimit'


**Time Complexity**

Assuming that there are m unique dates and n unique urls, the time complexity would be
`0(mn) + O(n log n) + O(nm log m)`

* O(mn) - Maximum number of records possible
* O(m log m) - Sort all the dates
* O(m * n log n) - Sort all the hit counts for each m dates



Reference
https://stackoverflow.com/questions/18971951/multithreading-to-read-a-file-in-java


