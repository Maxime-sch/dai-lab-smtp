# DAI - Lab 4 - SMTP: Design #

## Project components ##
### TcpClient (main) ###
The main method gets 2 csv files and 1 int as parameters, which are:\
-Victims list\
-Messages list\
-Number of groups\
The program then form n groups by selecting 2-5 e-mail addresses from the file for each group.\ 
The first address of the group is the sender, the others are the receivers (victims).\
For each group, the program selects one of the e-mail messages.\
The respective messages are then sent to the different groups using the SMTP protocol.\

### CSVReader ###
#### VictimsReader ####
Provides a method to parse a csv file and return an List of email addresses and to handle invalid inputs.
#### MessagesListReader ####
Provides a method to parse a csv file and return an List of subjects and an List of bodies for the prank e-mails to
send as well as handle invalid inputs.

### GroupMaker ###
Provides a method to group an array of e-mail addresses into n groups of 2-5 people, and to handle situations where 
n is too big or too small.

### SmtpClient ###
Provides a method to send a message which appears to be sent by a specific email address, to defined email addresses
using SMTP.