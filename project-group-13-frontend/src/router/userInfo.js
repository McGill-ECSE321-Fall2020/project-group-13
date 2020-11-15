
import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})
function userDto(username, email, bio,profilePictureIRL){
    this.username = username
    this.email=email
    this.bio=bio
    this.profilePictureIRL = profilePictureIRL
    this.artworks = []

}
function artworkDto(name){
    this.name = name
}
/*
export default {
    name: 'eventregistration',
    data () {
      return {
        persons: [],
        newPerson: '',
        errorPerson: '',
        response: []
      }
    },
    created: function () {
        ​// Initializing persons from backend
        ​AXIOS.get('/user')
        ​.then(response => {
          ​// JSON responses are automatically parsed.
          ​this.persons = response.data
        ​})
        ​.catch(e => {
          ​this.errorPerson = e
        ​})
        ​// Initializing events
        ​AXIOS.get('/events')
        ​.then(response => {
          ​this.events = response.data
        ​})
        ​.catch(e => {
          ​this.errorEvent = e
          ​// this.errors.push(e)
        ​})
        }
  }
  */