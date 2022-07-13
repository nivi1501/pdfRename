pdf-rename
rename research papers in the format year_conference_author_title

Generally when we download a research paper in pdf, the name of the pdf file gives no indication as to what the research paper
is about. This application solves this problem by renaming the pdf in the format year_conference_author_topic. The application 
does this by extracting information from web hence an internet connection is a pre-requisite.


Installation

Linux:
Download the repository and extract it

tar -xzvf rename.tar.gz

Go in the rename directory. Give the install.sh shell script all the needed permissions. And run this script.
Uses the bash shell.

chmod +x install.sh

bash install3.sh

Now go in the build directory and run ant

ant

**Close the existing terminal and open new terminal to run the pdfrename command**

Windows:

Download the repository and extract it

Now go in the build directory and run ant

run the jar file

java -jar rename.jar [options] ... [parameters]...

*Note that -a and --all option will not work in windows and a one page unwanted pdf file will be generated which has to be removed
manually. To resolve these problems a separate batch file will be released shortly.

Usage example
1) to get help

pdfrename -h  or  pdfrename --help

2) to print the database

pdfrename -p  or pdfrename --print

3) to rename a single pdf file

pdfrename filename.pdf

4) to force rename a pdf whose name appears to be in reqired format

pdfrename -f filename.pdf  or pdfrename --force filename.pdf

5) to rename all pdf files in a directory

pdfrename -a directory  or pdfrename --all directory

6) to delete a particular rule in database

pdfrename -d conference(full name)  or pdfrename --delete conference(full name)

7) to add a new rule or update it

pdfrename -u conference(full name) acronym  or pdfrename --update conference(full name) acronym 

8) to get the full title instead of important words

pdfrename -c filename.pdf  or pdfrename --complete filename.pdf

9) to know the version 

pdfrename -v or pdfrename --version

More about rules and database
The database contains two columns namely full-name and acronym
whenever conference name have same words as one of the record of database(full-name) it is abbrevited accordingly. 

Meta
Mukul Kaushik  1kaushikmukul@gmail.com  
Distributed under the Apache 2 license.  
See ``LICENSE`` for more information.  
This project was made under the guidance of Dr.Smruti Ranjan Sarangi, Associate Professor, CSE, IIT Delhi
