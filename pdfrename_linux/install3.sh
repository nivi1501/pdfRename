#!/bin/bash
REQUIRED_PKG="ant"
echo Checking for $REQUIRED_PKG: $PKG_OK
PKG_OK=$(dpkg-query -W --showformat='${Status}\n' $REQUIRED_PKG|grep "install ok installed")
if [ "" = "$PKG_OK" ]; then
  echo "Package $REQUIRED_PKG not installed, install it and run again."
  echo "To install run: sudo apt install $REQUIRED_PKG"
  exit
fi

if [ -f pdfrename.sh ] 
then
    echo "pdfrename already installed. Overwriting the old version.."
fi

cat > ""$(pwd)"/pdfrename.sh" << EOT
#!/bin/bash

jarfileaddress="$(pwd)/build/jar"
deletefileone=""
deletefiletwo=""

if ping -q -c 1 -W 1 8.8.8.8 >/dev/null; then
  echo "Internet is up"
    var=\$http_proxy
    if [ "\$var" = "" ]
    then
        echo "no proxy"
        echo "Do you want to connect to a proxy? yes|no"
        read proxy_flag
        if [ "\$proxy_flag" = "no" ]
        then 
            is_proxy=false
            a=0
            b=0
            port=0
            portf=0
        elif [ "\$proxy_flag" = "yes" ]
        then
            echo "Using the saved proxy: yes|no"
            echo "Saved Proxies: \$(cat ~/.pdfrename.conf)"
            read prev_proxy
            if [ "\$prev_proxy" = "yes" ]
            then
                export http_proxy=\$(cat ~/.pdfrename.conf)
            else
                echo "Enter proxy URL"
                read proxy_url
                export http_proxy=\$proxy_url
                echo \$proxy_url > ~/.pdfrename.conf
            fi
            var=\$http_proxy
            echo \$var
            is_proxy=true
            a="\${var%:*}"
            b="\${a#*//}"
            port="\${var##*:}"
            portf="\${port%/*}"
            echo \$b
            echo \$portf
        fi
    else
        is_proxy=true
        a="\${var%:*}"
        b="\${a#*//}"
        port="\${var##*:}"
        portf="\${port%/*}"
        echo \$b
        echo \$portf
    fi
else
  echo "No internet conneccted.."
  exit
fi

count=0
for i in "\$@"
do
if  [[ -f "\$i" ]]
then  
    count=\$(( \$count + 1))
fi
done

if [ \$# -eq 0 ]
then
    echo "please specify the file you want to rename"
    exit
elif [ \$# -eq 1 ]
then
    if [ "\$1" = "--print" ] || [ "\$1" = "-p" ] || [ "\$1" = "--help" ] || [ "\$1" = "-h" ]
    then
        cd \$jarfileaddress
        java -jar rename.jar \$is_proxy "\$b" "\$portf" "\$1"
    else
        intfilename="\$( cd "\$( dirname "\$1" )" && pwd )/\$1"
        dirctaddress="\$( cd "\$( dirname "\$1" )" && pwd )"
        finalfilename="/"\${intfilename#*//}""
        cd \$jarfileaddress
        java -jar rename.jar \$is_proxy "\$b" "\$portf" "\$finalfilename"
        deletefileone="\${finalfilename%.*}-001.pdf"
        deletefiletwo="\${finalfilename%.*}-002.pdf"
        rm "\$deletefiletwo"
        rm "\$deletefileone"
    fi
elif [ \$count -eq \$# ] 
then
    echo "passed *.pdf"
    current="\$(pwd)"
    echo "'\$(pwd)'"
    goback="\$(pwd)"
    find "\$(pwd)" -maxdepth 1 -type f -exec echo "{}" >> temp.txt \;
    while IFS='' read -r line || [ -n "\$line" ]; do
        if [ "\${line:(-4)}" == ".pdf" ]
        then
            echo " Working on \$line"
            cd \$jarfileaddress
            java -jar rename.jar \$is_proxy "\$b" "\$portf" "\$line"
            deletefileone="\${line%.*}-001.pdf"
            deletefiletwo="\${line%.*}-002.pdf"
            rm "\$deletefiletwo"
            rm "\$deletefileone"
        fi
        done < temp.txt
    cd "\$goback"
    rm temp.txt
elif [ \$# -eq 2 ]
then
    if [ "\$1" = "--delete" ] || [ "\$1" = "-d" ]
    then
        cd \$jarfileaddress
        java -jar rename.jar \$is_proxy "\$b" "\$portf" "\$1" "\$2"
    elif [ "\$1" = "--force" ] || [ "\$1" = "-f" ] || [ "\$1" = "-c" ] || [ "\$1" = "--complete" ]
    then
        intfilename="\$( cd "\$( dirname "\$2" )" && pwd )/\$2"
        dirctaddress="\$( cd "\$( dirname "\$2" )" && pwd )"
        finalfilename="/"\${intfilename#*//}""
        cd \$jarfileaddress
        java -jar rename.jar \$is_proxy "\$b" "\$portf" "\$1" "\$finalfilename"
        deletefileone="\${finalfilename%.*}-001.pdf"
        deletefiletwo="\${finalfilename%.*}-002.pdf"
        rm "\$deletefiletwo"
        rm "\$deletefileone"
    elif [ "\$1" = "--all" ] || [ "\$1" = "-a" ]
    then
        current="\$(pwd)"
        cd "\$2" #change to the directory provided (relative to the script)
        echo "'\$2'" '->' "'\$(pwd)'" #show new path for demo purposes
        goback="\$(pwd)"
        find "\$(pwd)" -maxdepth 1 -type f -exec echo "{}" >> temp.txt \;
        while IFS='' read -r line || [ -n "\$line" ]; do
            cd \$jarfileaddress
            java -jar rename.jar \$is_proxy "\$b" "\$portf" "\$line"
            deletefileone="\${line%.*}-001.pdf"
            deletefiletwo="\${line%.*}-002.pdf"
            rm "\$deletefiletwo"
            rm "\$deletefileone"
            done < temp.txt
        cd "\$goback"
        echo "\$(pwd)"
        rm temp.txt
    else
        echo "improper options or parameters"
    fi
elif [ \$# -eq 3 ]
then
    if [ "\$1" = "--update" ] || [ "\$1" = "-u" ]
    then
        cd \$jarfileaddress
        java -jar rename.jar \$is_proxy "\$b" "\$portf" "\$1" "\$2" "\$3"
    elif [ \( "\$1" = "--all" -o "\$1" = "-a" -o "\$1" = "-c" -o "\$1" = "--complete" \) -a \( "\$2" = "--all" -o "\$2" = "-a" -o "\$2" = "-c" -o "\$2" = "--complete" \) ]
    then
        current="\$(pwd)"
        cd "\$3" #change to the directory provided (relative to the script)
        echo "'\$3'" '->' "'\$(pwd)'" #show new path for demo purposes
        goback="\$(pwd)"
        find "\$(pwd)" -maxdepth 1 -type f -exec echo "{}" >> temp.txt \;
        while IFS='' read -r line || [ -n "\$line" ]; do
            cd \$jarfileaddress
            java -jar rename.jar \$is_proxy "\$b" "\$portf" "\$1" "\$line"
            deletefileone="\${line%.*}-001.pdf"
            deletefiletwo="\${line%.*}-002.pdf"
            rm "\$deletefiletwo"
            rm "\$deletefileone"
            done < temp.txt
        cd "\$goback"
        echo "\$(pwd)"
        rm temp.txt
    elif [ \( "\$1" = "--force" -o "\$1" = "-f" -o "\$1" = "-c" -o "\$1" = "--complete" \) -a \( "\$2" = "--force" -o "\$2" = "-f" -o "\$2" = "-c" -o "\$2" = "--complete" \) ]
    then
        intfilename="\$( cd "\$( dirname "\$2" )" && pwd )/\$2"
        dirctaddress="\$( cd "\$( dirname "\$2" )" && pwd )"
        finalfilename="/"\${intfilename#*//}""
        cd \$jarfileaddress
        java -jar rename.jar false 0 0 "\$1" "\$2" "\$finalfilename"
        deletefileone="\${finalfilename%.*}-001.pdf"
        deletefiletwo="\${finalfilename%.*}-002.pdf"
        rm "\$deletefiletwo"
        rm "\$deletefileone"
    else
    echo "Please enter proper options and parameters"
    fi
else
    echo "Please enter proper options and parameters"
fi
EOT
chmod +x pdfrename.sh
cp pdfrename.sh ~/.local/bin/pdfrename.sh
echo "alias pdfrename=\"bash ~/.local/bin/pdfrename.sh\"" >> ~/.bashrc
touch ~/.pdfrename.conf
. ~/.bashrc

# Build the file
cd build
ant
