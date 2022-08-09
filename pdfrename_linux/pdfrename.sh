#!/bin/bash

title1="********************************************************"
title2="************************pdfrename***********************"
title3="********************************************************"

end_title="##################-------------------###################"

COLUMNS=$(tput cols)
echo "$title1"
echo "$title2"
echo "$title3"


jarfileaddress="/home/teja/Documents/iitd/minorproject/final_pdfrename/pdfrename/build/jar"
deletefileone=""
deletefiletwo=""

if ping -q -c 1 -W 1 8.8.8.8 >/dev/null; then
  echo "Internet is up"
  var=$http_proxy
    if [ "$var" = "" ]
    then
        echo "no proxy"
        is_proxy=false
        a=0
        b=0
        port=0
        portf=0
    else
        is_proxy=true
        a="${var%:*}"
        b="${a#*//}"
        port="${var##*:}"
        portf="${port%/*}"
        echo $b
        echo $portf
    fi
else
  echo "No internet conneccted.."
  echo "$end_title"
  exit
fi

count=0
for i in "$@"
do
if  [[ -f "$i" ]]
then  
    count=$(( $count + 1))
fi
done

if [ $# -eq 0 ]
then
    echo "please specify the file you want to rename"
    echo "$end_title"
    exit
elif [ $# -eq 1 ]
then
    if [ "$1" = "--print" ] || [ "$1" = "-p" ] || [ "$1" = "--help" ] || [ "$1" = "-h" ]
    then
        cd $jarfileaddress
        java -jar rename.jar $is_proxy "$b" "$portf" "$1"
    else
        intfilename="$( cd "$( dirname "$1" )" && pwd )/$1"
        dirctaddress="$( cd "$( dirname "$1" )" && pwd )"
        finalfilename="/"${intfilename#*//}""
        cd $jarfileaddress
        java -jar rename.jar $is_proxy "$b" "$portf" "$finalfilename"
    fi
    echo "$end_title"
elif [ $count -eq $# ] 
then
    echo "passed *.pdf"
    current="$(pwd)"
    echo "'$(pwd)'"
    goback="$(pwd)"
    find "$(pwd)" -maxdepth 1 -type f -exec echo "{}" >> temp.txt \;
    while IFS='' read -r line || [ -n "$line" ]; do
        if [ "${line:(-4)}" == ".pdf" ]
        then
            echo " Working on $line"
            cd $jarfileaddress
            java -jar rename.jar $is_proxy "$b" "$portf" "$line"
            echo "$end_title"
        fi
        done < temp.txt
    cd "$goback"
    rm temp.txt
elif [ $# -eq 2 ]
then
    if [ "$1" = "--delete" ] || [ "$1" = "-d" ]
    then
        cd $jarfileaddress
        java -jar rename.jar $is_proxy "$b" "$portf" "$1" "$2"
        echo "$end_title"
    elif [ "$1" = "--force" ] || [ "$1" = "-f" ] || [ "$1" = "-c" ] || [ "$1" = "--complete" ]
    then
        intfilename="$( cd "$( dirname "$2" )" && pwd )/$2"
        dirctaddress="$( cd "$( dirname "$2" )" && pwd )"
        finalfilename="/"${intfilename#*//}""
        cd $jarfileaddress
        java -jar rename.jar $is_proxy "$b" "$portf" "$1" "$finalfilename"
        echo "$end_title"
    elif [ "$1" = "--all" ] || [ "$1" = "-a" ]
    then
        current="$(pwd)"
        cd "$2" #change to the directory provided (relative to the script)
        echo "'$2'" '->' "'$(pwd)'" #show new path for demo purposes
        goback="$(pwd)"
        find "$(pwd)" -maxdepth 1 -type f -exec echo "{}" >> temp.txt \;
        while IFS='' read -r line || [ -n "$line" ]; do
            cd $jarfileaddress
            java -jar rename.jar $is_proxy "$b" "$portf" "$line"
            echo "$end_title"
            done < temp.txt
        cd "$goback"
        echo "$(pwd)"
        rm temp.txt
    else
        echo "improper options or parameters"
    fi
elif [ $# -eq 3 ]
then
    if [ "$1" = "--update" ] || [ "$1" = "-u" ]
    then
        cd $jarfileaddress
        java -jar rename.jar $is_proxy "$b" "$portf" "$1" "$2" "$3"
        echo "$end_title"
    elif [ \( "$1" = "--all" -o "$1" = "-a" -o "$1" = "-c" -o "$1" = "--complete" \) -a \( "$2" = "--all" -o "$2" = "-a" -o "$2" = "-c" -o "$2" = "--complete" \) ]
    then
        current="$(pwd)"
        cd "$3" #change to the directory provided (relative to the script)
        echo "'$3'" '->' "'$(pwd)'" #show new path for demo purposes
        goback="$(pwd)"
        find "$(pwd)" -maxdepth 1 -type f -exec echo "{}" >> temp.txt \;
        while IFS='' read -r line || [ -n "$line" ]; do
            cd $jarfileaddress
            java -jar rename.jar $is_proxy "$b" "$portf" "$1" "$line"
            echo "$end_title"
            done < temp.txt
        cd "$goback"
        echo "$(pwd)"
        rm temp.txt
    elif [ \( "$1" = "--force" -o "$1" = "-f" -o "$1" = "-c" -o "$1" = "--complete" \) -a \( "$2" = "--force" -o "$2" = "-f" -o "$2" = "-c" -o "$2" = "--complete" \) ]
    then
        intfilename="$( cd "$( dirname "$2" )" && pwd )/$2"
        dirctaddress="$( cd "$( dirname "$2" )" && pwd )"
        finalfilename="/"${intfilename#*//}""
        cd $jarfileaddress
        java -jar rename.jar false 0 0 "$1" "$2" "$finalfilename"
        echo "$end_title"
    else
    echo "Please enter proper options and parameters"
    echo "$end_title"
    fi
else
    echo "Please enter proper options and parameters"
fi
