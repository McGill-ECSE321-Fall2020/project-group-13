#!/bin/bash

blue=$(tput setaf 4)
normal=$(tput sgr0)
yellow=$(tput setaf 3)
red=$(tput setaf 1)
powder_blue=$(tput setaf 153)

#usernames used to create users during test
username="aa"
username2="bb"
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

#get user 1 from database using correct username and password
curl-format "get user 1 from database using correct username and password" "-X GET" "$url/user/$username/login?password=passwor1dfd"

#get user 1 from database using incorrect username and password
curl-format "get user 1 from database using incorrect username and password" "-X GET" "$url/user/$username/login?password=passwfh"

#create artwork and store artworkID
curl-format "create artwork and store artworkID" "-X POST" "$url/artwork/new/?title=fakeTitle&artist=$username&artist=$username2&worth=100.7&imageURL=urlBABY" "artworkID"
artworkID=$tempVar

#get all artwork on premise
curl-format "get all artwork on premise" "-X GET" "$url/artwork/onPremise"

#add artwork to cart
curl-format "add artwork to cart" "-X PUT" "$url/user/$username/edit+/cart/?artid=$artworkID"	"cartID"
cartID=$tempVar

#get cart by cart ID
curl-format "get cart by cart ID" "-X GET" "$url/user/$username/cart/$cartID"

#get cart from user
curl-format "get cart from user" "-X GET" "$url/user/$username/cart"

#create order and store orderId
curl-format "create order and store orderId" "-X POST" "$url/user/$username/new/order" "orderID"
orderID=$tempVar

#create another order and store orderId
curl-format "create another order and store orderId" "-X POST" "$url/user/$username/new/order" "orderID"
orderID2=$tempVar

#get orders from user
curl-format "get orders from user" "-X GET" "$url/user/$username/orders"

#get order from orderId
curl-format "get order from orderId" "-X GET" "$url/user/$username/order/$orderID"

#create payment and store paymentId
curl-format "create payment and store paymentId" "-X POST" "$url/order/$orderID/pay" "paymentID" "@payment.json"
paymentID=$tempVar

printf "%s\n" "${blue}sleep for a second so that the next order's payment has a different time${normal}"
sleep 2
printf "\n"

#create payment for order 2
curl-format "create payment for other order and store paymentId" "-X POST" "$url/order/$orderID2/pay" "paymentID" "@payment.json"
paymentID2=$tempVar

#get payment from ID
curl-format "get payment from ID" "-X GET" "$url/payments/$paymentID"

#get payments of artist
curl-format "get payments of artist" "-X GET" "$url/payments/$username/artist"

#get payments of customer
curl-format "get payments of customer" "-X GET" "$url/payments/$username/customer"

#calculate commission for gallery after Date
curl-format "calculate commission for gallery after Date" "-X GET" "$url/payments/gallery?date=2019-03-28T01:30:00.000+07:00"

#get most recent order
curl-format "get most recent order" "-X GET" "$url/user/$username/orders/most-recent"

#create address and store addressId
curl-format "create address and store addressId" "-X POST" "$url/user/$username/new/address" "addressID" "@address.json"
addressID=$tempVar

#update address
curl-format "update address" "-X PUT" "$url/address/$addressID/update" "@address-updated.json" "@address-updated.json"

#get all addresses of user
curl-format "get all addresses of user" "-X GET" "$url/user/$username/addresses"

#set order to be delivered
curl-format "set order to be delivered" "-X PUT" "$url/order/$orderID/delivery?delivery=true" "@shipment.json" "@shipment.json"

#create shipment and store shipmentId
curl-format "create shipment and store shipmentId" "-X POST" "$url/order/$orderID/shipping?address=$addressID" "shipmentID" "@shipment.json"
shipmentID=$tempVar

#get all shipments of user
curl-format "get all shipments of user" "-X GET" "$url/user/$username/shipments"

#get shipment of order 1
curl-format "get shipment of order 1" "-X GET" "$url/order/$orderID/shipment"

#get artwork by ID
curl-format "get artwork by ID" "-X GET" "$url/artwork/$artworkID"

#create a second artwork
curl-format "create a second artwork" "-X POST" "$url/artwork/new/?title=fakeTitle&artist=$username&artist=$username2&worth=100.7&imageURL=urlBABY" "artworkID"
artworkID2=$tempVar

#add artwork to cart
curl-format "add artwork to cart" "-X PUT" "$url/user/$username/edit+/cart/?artid=$artworkID2" "cartID"
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
cartID2=$tempVar

#remove artwork from cart
curl-format "remove artwork from cart" "-X PUT" "$url/user/$username/edit-/cart/?artid=$artworkID2"

#update artwork
curl-format "update artwork" "-X PUT" "$url/artwork/$artworkID/update?description=A-beautiful-UPDATED-description"

#delete cart
curl-format "delete cart" "-X DELETE" "$url/user/$username/delete/cart/$cartID2"

#delete artwork by id
curl-format "delete artwork by id" "-X DELETE" "$url/artwork/$artworkID/delete"

#edit user fields
curl-format "edit user fields" "-X PUT" "$url/user/$username/edit" "@user-update.json" "@user-update.json"

#delete second order (expected: false b/c we cannot delete an order that has been paid)
curl-format "delete second order" "-X DELETE" "$url/user/$username/delete/order/$orderID2"

#delete user by username
curl-format "delete user by username" "-X DELETE" "$url/user/$username2/delete"
