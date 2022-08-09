# PDFRENAME

## Rename research papers in the format year_conference_author_title

When we download a research paper in pdf format, the file name provides no clue of the article's topic. This application addresses the issue by renaming the PDF using the format 'YEAR_CONFERENCE_AUTHOR_TITLE'. This application extracts conference, year, author name, and title information from the Internet.

## Prerequisite
JDK-11 or higher

## Installation

### Linux:

Download the repository

Go in the pdfrename_linux directory. 

Grant the executable permission for install3.sh:
```
chmod +x install.sh
```

Now execute the install.sh file with bash shell:
```
bash install.sh
```
Now you can run the pdfrename command.


Usage example

To get help
```
pdfrename -h  or  pdfrename --help
```

To rename a single pdf file
```
pdfrename filename.pdf
```

To rename all pdf files in a directory
```
pdfrename *.pdf
```

### Windows:

Download the repository and extract it.

Double click on the 'pdfrename.mysetup' in the 'pdfrename_windows' to install it.

Features on windows:
* Run with or without a proxy at your convenience. 
* Pdfrename action in the right click context menu of files.
* You can select multiple files and rename them from 'Send to -> pdfrename' in right click context menu.
* Dedicated error messages will be printed at the end of the execution.

Steps for Windows:

1. To initiate the rename proces, select the pdfrename option from right click menu of a file.
2. When proxy is detected, it will use the proxy ip address and port number for getting the information from the web.
3. After selecting the pdfrename, proper response will be shown at the end of the execution.

