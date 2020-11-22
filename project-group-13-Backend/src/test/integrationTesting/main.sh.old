#!/bin/bash

blue=$(tput setaf 4)
normal=$(tput sgr0)
yellow=$(tput setaf 3)
red=$(tput setaf 1)
powder_blue=$(tput setaf 153)

username="eecfdddddfdd"
username2="eedcfdddddw"

#create user 1
./curl-format "create user 1" POST "http://localhost:8080/newuser?username=$username&email=$username@no.com&password=passwor1dfd"

#create user 2
currentLine=$(curl -i -s -X POST "http://localhost:8080/newuser?username=$username2&email=$username@not.com&password=passwor1dfd")
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}create user 2${normal} ${powder_blue}RETURN STATUS CODE: [${yellow}$statusCode${powder_blue}]${normal}"
currentLine=$(echo  "$currentLine" | grep -o "{.*")
printf "$currentLine\n"

#create artwork and store artworkID
currentLine=$(curl -s -X POST "http://localhost:8080/artwork/new/?artid=fakeTitle&artist=$username&artist=$username2&worth=100.7")
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "\n%s\n" "${blue}create artwork and store artworkID${normal}"
printf "$currentLine\n"
artworkID=$(echo  "$currentLine" | grep -o "\"artworkID\".*" | cut -f2- -d: | grep  -o "^[0-9]*")

#add artwork to cart
currentLine=$(curl -s -X PUT "http://localhost:8080/user/$username/edit+/cart/?artid=$artworkID")
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}add artwork to cart${normal}"
printf "$currentLine\n"
cartID=$(echo  "$currentLine" | grep -o "\"cartID\".*" | cut -f2- -d: | grep  -o "^[0-9]*")


#get cart by cart ID
currentLine=$(curl -s -X GET "http://localhost:8080/user/$username/cart/$cartID")
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}get cart by cart ID${normal}"
printf "$currentLine\n"


#get cart from user
currentLine=$(curl -s -X GET "http://localhost:8080/user/$username/cart")
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}get cart from user${normal}"
printf "\n$currentLine\n"

#create order and store orderId
currentLine=$(curl -s -X POST "http://localhost:8080/user/$username/new/order")
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}create order and store orderId${normal}"
printf "$currentLine\n"
orderID=$(echo  "$currentLine" | grep -o "\"orderID\".*" | cut -f2- -d: | grep  -o "^[0-9]*")

#create another order and store orderId
currentLine=$(curl -s -X POST "http://localhost:8080/user/$username/new/order")
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}create another order and store orderId${normal}"
printf "$currentLine\n"
orderID2=$(echo  "$currentLine" | grep -o "\"orderID\".*" | cut -f2- -d: | grep  -o "^[0-9]*")

#get orders from user
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}get orders from user${normal}"
currentLine=$(curl -s -X GET "http://localhost:8080/user/$username/orders")
printf "\n$currentLine\n"

#get orders from user
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}get orders from user${normal}"
currentLine=$(curl -s -X GET "http://localhost:8080/user/$username/orders")
printf "\n$currentLine\n"


#get order from orderId
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}get order from orderId${normal}"
currentLine=$(curl -s -X GET "http://localhost:8080/user/$username/order/$orderID")
printf "$currentLine\n"



#create payment and store paymentId
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}create payment and store paymentId${normal}"
currentLine=$(curl -s -H "Content-Type: application/json" --data @payment.json -X POST "http://localhost:8080/order/$orderID/pay")
printf "$currentLine\n"
paymentID=$(echo  "$currentLine" | grep -o "\"paymentID\".*" | cut -f2- -d: | grep  -o "^[0-9]*")

printf "%s\n" "${blue}sleep for a second so that next order has a different time${normal}"
sleep 2

printf "%s\n" "${blue}create payment for other order and store paymentId${normal}"
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
currentLine=$(curl -s -H "Content-Type: application/json" --data @payment.json -X POST "http://localhost:8080/order/$orderID2/pay")
printf "$currentLine\n"
paymentID2=$(echo  "$currentLine" | grep -o "\"paymentID\".*" | cut -f2- -d: | grep  -o "^[0-9]*")

#get payment from ID
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}get payment from ID${normal}"
currentLine=$(curl -s -X GET "http://localhost:8080/payments/$paymentID")
printf "$currentLine\n"

#get payments of artist
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}get payments of artist${normal}"
currentLine=$(curl -s -X GET "http://localhost:8080/payments/$username/artist")
printf "$currentLine\n"

#get payments of customer
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}get payments of customer${normal}"
currentLine=$(curl -s -X GET "http://localhost:8080/payments/$username/customer")
printf "$currentLine\n"

#calculate commission for gallery after Date
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}calculate commission for gallery after Date${normal}"
currentLine=$(curl -s -X GET "http://localhost:8080/payments/gallery?date=2019-03-28T01:30:00.000+07:00")
printf "$currentLine\n"

#get most recent order
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}get most recent order${normal}"
currentLine=$(curl -s -X GET "http://localhost:8080/user/$username/orders/most-recent")
printf "\n$currentLine\n"



#create address and store addressId
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}create address and store addressId${normal}"
currentLine=$(curl -s -H "Content-Type: application/json" --data @address.json -X POST "http://localhost:8080/user/$username/new/address")
printf "$currentLine\n"
addressID=$(echo  "$currentLine" | grep -o "\"addressID\".*" | cut -f2- -d: | grep  -o "^[0-9]*")



#update address
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}update address${normal}"
currentLine=$(curl -s -H "Content-Type: application/json" --data @address-updated.json -X PUT "http://localhost:8080/address/$addressID/update")
printf "$currentLine\n"


#get all addresses of user
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}get all addresses of user${normal}"
currentLine=$(curl -s -X GET "http://localhost:8080/user/$username/addresses")
printf "$currentLine\n"


#set order to be delivered
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}set order to be delivered${normal}"
currentLine=$(curl -s -H "Content-Type: application/json" --data @shipment.json -X PUT "http://localhost:8080/order/$orderID/delivery?delivery=true")
printf "$currentLine\n"

#create shipment and store shipmentId
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}create shipment and store shipmentId${normal}"
currentLine=$(curl -s -H "Content-Type: application/json" --data @shipment.json -X POST "http://localhost:8080/order/$orderID/shipping?address=$addressID")
printf "$currentLine\n"
shipmentID=$(echo  "$currentLine" | grep -o "\"shipmentID\".*" | cut -f2- -d: | grep  -o "^[0-9]*")

#get all shipments of user
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}get all shipments of user${normal}"
currentLine=$(curl -s -X GET "http://localhost:8080/user/$username/shipments")
printf "$currentLine\n"

#get artwork by ID
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}get artwork by ID${normal}"
currentLine=$(curl -s -X GET "http://localhost:8080/artwork/$artworkID")
printf "$currentLine\n"

#create a second artwork
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
currentLine=$(curl -s -X POST "http://localhost:8080/artwork/new/?artid=fakeTitle&artist=$username&artist=$username2&worth=100.7")
printf "$currentLine\n"
artworkID2=$(echo  "$currentLine" | grep -o "\"artworkID\".*" | cut -f2- -d: | grep  -o "^[0-9]*")

#add artwork to cart
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}add artwork to cart${normal}"
currentLine=$(curl -s -X PUT "http://localhost:8080/user/$username/edit+/cart/?artid=$artworkID2")
printf "$currentLine\n"
cartID2=$(echo  "$currentLine" | grep -o "\"cartID\".*" | cut -f2- -d: | grep  -o "^[0-9]*")

#remove artwork from cart
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}remove artwork from cart${normal}"
currentLine=$(curl -s -X PUT "http://localhost:8080/user/$username/edit-/cart/?artid=$artworkID2")
printf "$currentLine\n"


#update artwork
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}update artwork${normal}"
currentLine=$(curl -s -X PUT "http://localhost:8080/artwork/$artworkID/update?description=A-beautiful-UPDATED-description")
printf "$currentLine\n"

#delete cart
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}delete cart${normal}"
currentLine=$(curl -s -X DELETE "http://localhost:8080/user/$username/delete/cart/$cartID2")
printf "$currentLine\n"


#delete address by id
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}delete address by id${normal}"
currentLine=$(curl -s -X DELETE "http://localhost:8080/address/$addressID/delete")
printf "$currentLine\n"

#delete artwork by id
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}delete artwork by id${normal}"
currentLine=$(curl -s -X DELETE "http://localhost:8080/artwork/$artworkID/delete")
printf "$currentLine\n"

#edit user fields
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}edit user fields${normal}"
currentLine=$(curl -s -H "Content-Type: application/json" --data @user-update.json -X PUT "http://localhost:8080/user/$username/edit")
printf "$currentLine\n"

#delete second order (expected: false b/c we cannot delete an order that has been paid)
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}delete second order (expected: [${normal}false${blue}] b/c we cannot delete an order that has been paid)${normal}"
currentLine=$(curl -s -X DELETE "http://localhost:8080/user/$username/delete/order/$orderID2")
printf "$currentLine\n"

#delete user by username
statusCode=$(echo  "$currentLine" | grep -o "HTTP/.*" | cut -f2- -d: | xargs | grep  -o "[0-9]*$")
printf "%s\n" "${blue}delete user by username (expected: [${normal}true${blue}])${normal}"
currentLine=$(curl -s -X DELETE "http://localhost:8080/user/$username2/delete")
printf "$currentLine\n"

