#!/bin/bash
git remote rm heroku
heroku git:remote -a project-group-13-frontend
git push heroku master
