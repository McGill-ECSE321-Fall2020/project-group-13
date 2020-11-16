#!/bin/bash
git remote rm heroku
heroku git:remote -a project-group-13-frontend
git subtree push --prefix project-group-13-frontend/ heroku master
git remote rm heroku
