Shared between both versions:
TwitterGatherer is a mostly abstract class that is subclassed for the sequential and concurrent versions. This class provides some basic helper functions 
for loading a list of users form a text file as well as generating twitter URL's given a user name and page number. 

Sequential version:
The sequential version is made up of the SequentialTwitter and SequentialGatherer. This version simple loads the file of users. Then for each user it 
loops through all the pages concatenating the results as it goes. Once all of the results have been gathered they are written to a file. 

Concurrent Version:
This version is made up of ConcurrentTwitter, ConcurrentGatherer, and TwitterPageCallable. In this version the list of users is read in 
form a text file like the sequential version. Next a thread pool is created with one thread for each user name read in. In these threads we
 call the getTweetsForUser method. This method / task then starts a new thread pool for each page results request. Then all threads are 
invoked and all requests for all 144 pages are issued simultaneously. After the results for a given users are finished the results are 
concatenated and dumped to a file. This operations also occurs in parallel as we had previously created a thread for each user. 

Results:
Sequential version: As long as 700+ seconds 
Concurrent version: ~30 seconds depending on the number of 502 errors thrown. 

