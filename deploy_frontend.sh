#!/bin/bash
git remote rm heroku
heroku git:remote -a project-group-13-frontend
<<<<<<< HEAD
git push heroku master
=======
git subtree push --prefix project-group-13-frontend/ heroku master
>>>>>>> 6e128fff7d5846fc1dfc6f80de65149c782255af
