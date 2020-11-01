#!/bin/bash

username="c"

#test the creation of users
curl -s -X POST "http://localhost:8080/newuser?username=$username&email=asd@no.com&password=passwor1dfd"

#create order and store orderId
currentLine=$(curl -s -X POST "http://localhost:8080/user/$username/new/order")
echo "$currentLine"
orderID=$(echo  "$currentLine" | grep -o "\"orderID\".*" | cut -f2- -d: | grep  -o "^[0-9]*")

#create payment and store paymentId
currentLine=$(curl -s -H "Content-Type: application/json" --data @payment.json -X POST "http://localhost:8080/order/$orderID/pay")
echo "$currentLine"
paymentID=$(echo  "$currentLine" | grep -o "\"paymentID\".*" | cut -f2- -d: | grep  -o "^[0-9]*")

#create address and store addressId
currentLine=$(curl -s -H "Content-Type: application/json" --data @address.json -X POST "http://localhost:8080/user/$username/new/address")
echo "$currentLine"
addressID=$(echo  "$currentLine" | grep -o "\"addressID\".*" | cut -f2- -d: | grep  -o "^[0-9]*")


#set order to be delivered
currentLine=$(curl -s -H "Content-Type: application/json" --data @shipment.json -X PUT "http://localhost:8080/order/$orderID/delivery?delivery=true")
echo "$currentLine"

#create shipment and store shipmentId
currentLine=$(curl -s -H "Content-Type: application/json" --data @shipment.json -X POST "http://localhost:8080/order/$orderID/shipping?address=$addressID")
echo "$currentLine"
shipmentID=$(echo  "$currentLine" | grep -o "\"shipmentID\".*" | cut -f2- -d: | grep  -o "^[0-9]*")

