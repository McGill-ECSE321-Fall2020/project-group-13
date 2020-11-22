#!/bin/bash

blue=$(tput setaf 4)
normal=$(tput sgr0)
yellow=$(tput setaf 3)
red=$(tput setaf 1)
powder_blue=$(tput setaf 153)

#usernames used to create users during test
username="zzz"
username2="wwww"
url="http://localhost:8080"


function curl-format() {
	if [[ ! -z "$1" ]]	#Only run if parameters are given to the function
	then
		if [[ ! -z "$5" ]]	#Check for json body
		then
			currentLine=$(curl -i -s -H "Content-Type: application/json" --data $5 $2 "$3") # if json body
		else
			currentLine=$(curl -i -s $2 "$3") # if no json body
		fi
		statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")  #get HTML return status code
		printf "%s\t%s\n" "${powder_blue}RETURN STATUS CODE: [${yellow}$statusCode${powder_blue}]${normal}" "${blue}$1${normal}" #print return status code and description of request
		
		currentLine=$(echo  "$currentLine" | awk 'c&&!--c;/Date:/{c=2}') #parse and print HTML response
		printf "$currentLine\n\n\n"
		if [[ ! -z "$4" ]]	#Check whether we need to parse for any Integer data in the response (i.e: object IDs)
		then
			tempVar=$(echo  "$currentLine" | grep -o "$4.*" | cut -f2- -d: | grep  -o "^[0-9]*") #store data in temporary global variable 
		fi
	fi
}


#create user 1
curl-format "create user 1" "-X POST" "$url/newuser?username=$username&email=$username@no.com&password=passwor1dfd"

#create user 2
curl-format "create user 2" "-X POST" "$url/newuser?username=$username2&email=$username2@no.com&password=passwor1dfd"


for i in {1..5}
do
	#create artwork and store artworkID
	curl-format "create artwork and store artworkID" "-X POST" "$url/artwork/new/?title=fakeTitle&artist=$username&artist=$username2&worth=100.7&imageURL=https%3A%2F%2Fimg.xcitefun.net%2Fusers%2F2013%2F09%2F333375%2Cxcitefun-beautiful-paintings-with-digital-sky-2.jpg" "artworkID"
	artworkID=$tempVar
	#update artwork
	curl-format "update artwork" "-X PUT" "$url/artwork/$artworkID/update?description=A-beautiful-UPDATED-description&title=$artworkID&OnPremise=true"

	#add artwork to cart of user 1
	curl-format "add artwork to cart" "-X PUT" "$url/user/$username/edit+/cart/?artid=$artworkID"	"cartID"
done




