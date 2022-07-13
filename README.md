#PDFRENAME

##Rename research papers in the format year_conference_author_title

Generally when we download a research paper in pdf, the name of the pdf file gives no indication as to what the research paper is about. This application solves this problem by renaming the pdf in the format YEAR_CONFERENCE_AUTHOR_TOPIC. 
This application uses internet for extracting conference, year, author name and topic. A reliable internet connnection is necessary. 

Installation

Linux:
Download the repository

Go in the pdfrename_linux directory. 


Grant the executable permission for install3.sh:
'''
chmod +x install3.sh
'''

Now execute the install3.sh file with bash shell:
'''
bash install3.sh
'''

Now you can run the pdfrename command.

Usage example

To get help
'''
pdfrename -h  or  pdfrename --help
'''

To print the database
'''
pdfrename -p  or pdfrename --print
'''

To rename a single pdf file
'''
pdfrename filename.pdf
'''

To force rename a pdf whose name appears to be in reqired format
'''
pdfrename -f filename.pdf  or pdfrename --force filename.pdf
'''

To rename all pdf files in a directory
'''
pdfrename *.pdf
'''

To delete a particular rule in database
'''
pdfrename -d conference(full name)  or pdfrename --delete conference(full name)
'''

To add a new rule or update it
'''
pdfrename -u conference(full name) acronym  or pdfrename --update conference(full name) acronym 
'''

To get the full title instead of important words
'''
pdfrename -c filename.pdf  or pdfrename --complete filename.pdf
'''

To know the version 
'''
pdfrename -v or pdfrename --version
'''

More about rules and database
The database contains two columns namely full-name and acronym
whenever conference name have same words as one of the record of database(full-name) it is abbrevited accordingly.

Windows:

Download the repository and extract it.

Double click on the pdfrename_windows_without_jre.mysetup in the pdfrename_windows_without_jre to install it.

Features on windows:
 Markup : * Desktop icon.
          * Run with or without a proxy at your convenience. 
          * Save the proxy URLs for later use.
          * Pdfrename action in the right click context menu of files.
          * You can select multiple files and rename them from 'Send to' in right click context menu.
          * Dedicated GUI to walkthrough each step of renaming the pdf file with clear messages.

Steps for Windows:

Step 1:  To initiate the rename proces,  either double click on the desktop icon or just select the pdfrename option from right click menu of a file. In the case of desktop icon, choose the files using the open button, then click on convert.
Step 2: Now select whether to proceed with a proxy or not.
Step 3: When proxy option is selected, you are prompted with an option to choose saved proxies or to use a new proxy address and port.
Step 4: After selecting the proxy, wait for the program to finish renaming the file. You will be prompted with a message at the end of program execution.

