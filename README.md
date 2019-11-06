Implementation of Url Hit Counter in Java.

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


