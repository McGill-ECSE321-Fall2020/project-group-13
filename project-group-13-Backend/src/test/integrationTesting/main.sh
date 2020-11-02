#!/bin/bash

blue=$(tput setaf 4)
normal=$(tput sgr0)

username="rr3rf5redr"
username2="aff3rte5r"

#test the creation of users
printf "%s\n" "${blue}test the creation of users${normal}"
curl -s -X POST "http://localhost:8080/newuser?username=$username&email=asd@no.com&password=passwor1dfd"
curl -s -X POST "http://localhost:8080/newuser?username=$username2&email=asdfg@not.com&password=passwor1dfd"

#create cart and store cartId
printf "\n%s\n" "${blue}create cart and store cartId${normal}"
currentLine=$(curl -s -X POST "http://localhost:8080/user/$username/new/cart/empty")
printf "$currentLine\n"
cartID=$(echo  "$currentLine" | grep -o "\"cartID\".*" | cut -f2- -d: | grep  -o "^[0-9]*")

#create artwork and store artworkID
printf "%s\n" "${blue}create artwork and store artworkID${normal}"
currentLine=$(curl -s -X POST "http://localhost:8080/artwork/new/?artid=fakeTitle&artist=$username&artist=$username2&worth=100.7")
printf "$currentLine\n"
artworkID=$(echo  "$currentLine" | grep -o "\"artworkID\".*" | cut -f2- -d: | grep  -o "^[0-9]*")

#add artwork to cart
printf "%s\n" "${blue}add artwork to cart${normal}"
currentLine=$(curl -s -X PUT "http://localhost:8080/user/$username/edit+/cart/?artid=$artworkID")
printf "$currentLine\n"

#get cart by cart ID
printf "%s\n" "${blue}get cart by cart ID${normal}"
currentLine=$(curl -s -X GET "http://localhost:8080/user/$username/cart/$cartID")
printf "$currentLine\n"


#get cart from user
printf "%s\n" "${blue}get cart from user${normal}"
currentLine=$(curl -s -X GET "http://localhost:8080/user/$username/cart")
printf "\n$currentLine\n"

#create order and store orderId
printf "%s\n" "${blue}create order and store orderId${normal}"
currentLine=$(curl -s -X POST "http://localhost:8080/user/$username/new/order")
printf "$currentLine\n"
orderID=$(echo  "$currentLine" | grep -o "\"orderID\".*" | cut -f2- -d: | grep  -o "^[0-9]*")


#create another order and store orderId
printf "%s\n" "${blue}re-create order and store orderId${normal}"
currentLine=$(curl -s -X POST "http://localhost:8080/user/$username/new/order")
printf "$currentLine\n"
orderID2=$(echo  "$currentLine" | grep -o "\"orderID\".*" | cut -f2- -d: | grep  -o "^[0-9]*")

#get orders from user
printf "%s\n" "${blue}get orders from user${normal}"
currentLine=$(curl -s -X GET "http://localhost:8080/user/$username/orders")
printf "\n$currentLine\n"

#delete second order
printf "%s\n" "${blue}delete second order${normal}"
currentLine=$(curl -s -X DELETE "http://localhost:8080/user/$username/delete/order/$orderID2")
printf "$currentLine\n"

#get order from orderId
printf "%s\n" "${blue}get order from orderId${normal}"
currentLine=$(curl -s -X GET "http://localhost:8080/user/$username/order/$orderID")
printf "$currentLine\n"


#create payment and store paymentId
printf "%s\n" "${blue}create payment and store paymentId${normal}"
currentLine=$(curl -s -H "Content-Type: application/json" --data @payment.json -X POST "http://localhost:8080/order/$orderID/pay")
printf "$currentLine\n"
paymentID=$(echo  "$currentLine" | grep -o "\"paymentID\".*" | cut -f2- -d: | grep  -o "^[0-9]*")

#get payment from ID
printf "%s\n" "${blue}get payment from ID${normal}"
currentLine=$(curl -s -X GET "http://localhost:8080/payments/$paymentID")
printf "$currentLine\n"

#get payments of artist
printf "%s\n" "${blue}get payments of artist${normal}"
currentLine=$(curl -s -X GET "http://localhost:8080/payments/$username/artist")
printf "$currentLine\n"

#get payments of customer
printf "%s\n" "${blue}get payments of customer${normal}"
currentLine=$(curl -s -X GET "http://localhost:8080/payments/$username/customer")
printf "$currentLine\n"

#calculate commission for gallery after Date
printf "%s\n" "${blue}calculate commission for gallery after Date${normal}"
currentLine=$(curl -s -X GET "http://localhost:8080/payments/gallery?date=2019-03-28T01:30:00.000+07:00")
printf "$currentLine\n"

#get most recent order
printf "%s\n" "${blue}get most recent order${normal}"
currentLine=$(curl -s -X GET "http://localhost:8080/user/$username/orders/most-recent")
printf "\n$currentLine\n"

#create address and store addressId
printf "%s\n" "${blue}create address and store addressId${normal}"
currentLine=$(curl -s -H "Content-Type: application/json" --data @address.json -X POST "http://localhost:8080/user/$username/new/address")
printf "$currentLine\n"
addressID=$(echo  "$currentLine" | grep -o "\"addressID\".*" | cut -f2- -d: | grep  -o "^[0-9]*")

#update address
printf "%s\n" "${blue}update address${normal}"
currentLine=$(curl -s -H "Content-Type: application/json" --data @address-updated.json -X PUT "http://localhost:8080/address/$addressID/update")
printf "$currentLine\n"


#get all addresses of user
printf "%s\n" "${blue}get all addresses of user${normal}"
currentLine=$(curl -s -X GET "http://localhost:8080/user/$username/addresses")
printf "$currentLine\n"

#set order to be delivered
printf "%s\n" "${blue}set order to be delivered${normal}"
currentLine=$(curl -s -H "Content-Type: application/json" --data @shipment.json -X PUT "http://localhost:8080/order/$orderID/delivery?delivery=true")
printf "$currentLine\n"

#create shipment and store shipmentId
printf "%s\n" "${blue}create shipment and store shipmentId${normal}"
currentLine=$(curl -s -H "Content-Type: application/json" --data @shipment.json -X POST "http://localhost:8080/order/$orderID/shipping?address=$addressID")
printf "$currentLine\n"
shipmentID=$(echo  "$currentLine" | grep -o "\"shipmentID\".*" | cut -f2- -d: | grep  -o "^[0-9]*")

#get all shipments of user
printf "%s\n" "${blue}get all shipments of user${normal}"
currentLine=$(curl -s -X GET "http://localhost:8080/user/$username/shipments")
printf "$currentLine\n"

#create artwork and store artworkID
printf "%s\n" "${blue}create artwork and store artworkID${normal}"
currentLine=$(curl -s -X POST "http://localhost:8080/artwork/new/?artid=fakeTitle&artist=$username&worth=100.7")
printf "$currentLine\n"
artworkID=$(echo  "$currentLine" | grep -o "\"artworkID\".*" | cut -f2- -d: | grep  -o "^[0-9]*")

#get artwork by ID
printf "%s\n" "${blue}get artwork by ID${normal}"
currentLine=$(curl -s -X GET "http://localhost:8080/artwork/$artworkID")
printf "$currentLine\n"

#add artwork to cart
printf "%s\n" "${blue}add artwork to cart${normal}"
currentLine=$(curl -s -X PUT "http://localhost:8080/user/$username/edit+/cart/?artid=$artworkID")
printf "$currentLine\n"

#remove artwork from cart
printf "%s\n" "${blue}remove artwork from cart${normal}"
currentLine=$(curl -s -X PUT "http://localhost:8080/user/$username/edit-/cart/$cartID?artid=$artworkID")
printf "$currentLine\n"

#update artwork
printf "%s\n" "${blue}update artwork${normal}"
currentLine=$(curl -s -X PUT "http://localhost:8080/artwork/$artworkID/update?description=A-beautiful-UPDATED-description")
printf "$currentLine\n"

#delete cart
printf "%s\n" "${blue}delete cart${normal}"
currentLine=$(curl -s -X DELETE "http://localhost:8080/user/$username/delete/cart/$cartID")
printf "$currentLine\n"

#create cart with artwork and store cartID
printf "\n%s\n" "${blue}create cart with artwork and store cartID${normal}"
currentLine=$(curl -s -X POST "http://localhost:8080/user/$username/new/cart?artid=$artworkID")
printf "$currentLine\n"
cartID=$(echo  "$currentLine" | grep -o "\"cartID\".*" | cut -f2- -d: | grep  -o "^[0-9]*")

#delete address by id
printf "%s\n" "${blue}delete address by id${normal}"
currentLine=$(curl -s -X DELETE "http://localhost:8080/address/$addressID/delete")
printf "$currentLine\n"

#delete artwork by id
printf "%s\n" "${blue}delete artwork by id${normal}"
currentLine=$(curl -s -X DELETE "http://localhost:8080/artwork/$artworkID/delete")
printf "$currentLine\n"

#edit user fields
printf "%s\n" "${blue}edit user fields${normal}"
currentLine=$(curl -s -H "Content-Type: application/json" --data @user-update.json -X PUT "http://localhost:8080/user/$username/edit")
printf "$currentLine\n"


#delete user by username
printf "%s\n" "${blue}delete user by username${normal}"
currentLine=$(curl -s -X DELETE "http://localhost:8080/user/$username2/delete")
printf "$currentLine\n"