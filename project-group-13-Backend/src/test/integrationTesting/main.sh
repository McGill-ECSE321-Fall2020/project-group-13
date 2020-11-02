#!/bin/bash

blue=$(tput setaf 4)
normal=$(tput sgr0)
yellow=$(tput setaf 3)
red=$(tput setaf 1)
powder_blue=$(tput setaf 153)

username="ffdF"
username2="eddFd"
function curl-format() {
	if [[ ! -z "$1" ]]
	then
		if [[ ! -z "$5" ]]	#have json body
		then
			currentLine=$(curl -i -s -H "Content-Type: application/json" --data $5 $2 "$3")
		else
			currentLine=$(curl -i -s $2 "$3")
		fi
		statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
		printf "%s\t%s\n" "${powder_blue}RETURN STATUS CODE: [${yellow}$statusCode${powder_blue}]${normal}" "${blue}$1${normal}"
		#currentLine=$(echo  "$currentLine" | grep -o "{.*")
		currentLine=$(echo  "$currentLine" | awk 'c&&!--c;/Date:/{c=2}')
		printf "$currentLine\n\n\n"
		if [[ ! -z "$4" ]]	#need to save variable
		then
			tempVar=$(echo  "$currentLine" | grep -o "$4.*" | cut -f2- -d: | grep  -o "^[0-9]*")
		fi
	fi
}

#create user 1
curl-format "create user 1" "-X POST" "http://localhost:8080/newuser?username=$username&email=$username@no.com&password=passwor1dfd"

#create user 2
curl-format "create user 2" "-X POST" "http://localhost:8080/newuser?username=$username2&email=$username@not.com&password=passwor1dfd"

#create artwork and store artworkID
curl-format "create artwork and store artworkID" "-X POST" "http://localhost:8080/artwork/new/?artid=fakeTitle&artist=$username&artist=$username2&worth=100.7" "artworkID"
artworkID=$tempVar

#add artwork to cart
curl-format "add artwork to cart" "-X PUT" "http://localhost:8080/user/$username/edit+/cart/?artid=$artworkID"	"cartID"
cartID=$tempVar

#get cart by cart ID
curl-format "get cart by cart ID" "-X GET" "http://localhost:8080/user/$username/cart/$cartID"

#get cart from user
curl-format "get cart from user" "-X GET" "http://localhost:8080/user/$username/cart"

#create order and store orderId
curl-format "create order and store orderId" "-X POST" "http://localhost:8080/user/$username/new/order" "orderID"
orderID=$tempVar

#create another order and store orderId
curl-format "create another order and store orderId" "-X POST" "http://localhost:8080/user/$username/new/order" "orderID"
orderID2=$tempVar

#get orders from user
curl-format "get orders from user" "-X GET" "http://localhost:8080/user/$username/orders"

#get orders from user
curl-format "get orders from user" "-X GET" "http://localhost:8080/user/$username/orders"

#get order from orderId
curl-format "get order from orderId" "-X GET" "http://localhost:8080/user/$username/order/$orderID"

#create payment and store paymentId
curl-format "create payment and store paymentId" "-X POST" "http://localhost:8080/order/$orderID/pay" "paymentID" "@payment.json"
paymentID=$tempVar

printf "%s\n" "${blue}sleep for a second so that next order has a different time${normal}"
sleep 2

#create payment for order 2
curl-format "create payment for other order and store paymentId" "-X POST" "http://localhost:8080/order/$orderID2/pay" "paymentID" "@payment.json"
paymentID2=$tempVar

#get payment from ID
curl-format "get payment from ID" "-X GET" "http://localhost:8080/payments/$paymentID"

#get payments of artist
curl-format "get payments of artist" "-X GET" "http://localhost:8080/payments/$username/artist"

#get payments of customer
curl-format "get payments of customer" "-X GET" "http://localhost:8080/payments/$username/customer"

#calculate commission for gallery after Date
curl-format "calculate commission for gallery after Date" "-X GET" "http://localhost:8080/payments/gallery?date=2019-03-28T01:30:00.000+07:00"

#get most recent order
curl-format "get most recent order" "-X GET" "http://localhost:8080/user/$username/orders/most-recent"

#create address and store addressId
curl-format "create address and store addressId" "-X POST" "http://localhost:8080/user/$username/new/address" "addressID" "@address.json"
addressID=$tempVar

#update address
curl-format "update address" "-X PUT" "http://localhost:8080/address/$addressID/update" "@address-updated.json" "@address-updated.json"

#get all addresses of user
curl-format "get all addresses of user" "-X GET" "http://localhost:8080/user/$username/addresses"

#set order to be delivered
curl-format "set order to be delivered" "-X PUT" "http://localhost:8080/order/$orderID/delivery?delivery=true" "@shipment.json" "@shipment.json"

#create shipment and store shipmentId
curl-format "create shipment and store shipmentId" "-X POST" "http://localhost:8080/order/$orderID/shipping?address=$addressID" "shipmentID" "@shipment.json"
shipmentID=$tempVar

#get all shipments of user
curl-format "get all shipments of user" "-X GET" "http://localhost:8080/user/$username/shipments"

#get artwork by ID
curl-format "get artwork by ID" "-X GET" "http://localhost:8080/artwork/$artworkID"

#create a second artwork
curl-format "create a second artwork" "-X POST" "http://localhost:8080/artwork/new/?artid=fakeTitle&artist=$username&artist=$username2&worth=100.7" "artworkID"
artworkID2=$tempVar

#add artwork to cart
curl-format "add artwork to cart" "-X PUT" "http://localhost:8080/user/$username/edit+/cart/?artid=$artworkID2" "cartID"
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
cartID2=$tempVar

#remove artwork from cart
curl-format "remove artwork from cart" "-X PUT" "http://localhost:8080/user/$username/edit-/cart/?artid=$artworkID2"

#update artwork
curl-format "update artwork" "-X PUT" "http://localhost:8080/artwork/$artworkID/update?description=A-beautiful-UPDATED-description"

#delete cart
curl-format "delete cart" "-X DELETE" "http://localhost:8080/user/$username/delete/cart/$cartID2"

#delete artwork by id
curl-format "delete artwork by id" "-X DELETE" "http://localhost:8080/artwork/$artworkID/delete"

#edit user fields
curl-format "edit user fields" "-X PUT" "http://localhost:8080/user/$username/edit" "@user-update.json" "@user-update.json"

#delete second order (expected: false b/c we cannot delete an order that has been paid)
curl-format "delete second order" "-X DELETE" "http://localhost:8080/user/$username/delete/order/$orderID2"

#delete user by username
curl-format "delete user by username" "-X DELETE" "http://localhost:8080/user/$username2/delete"
