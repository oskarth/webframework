# webframework

Goal: Make a web framework in under 4 hours and under 100 lines of code.

`lein ring server` to run.

# What it can do

1. Listen to http traffic on port 3000 (with the help of ring.adapter.jetty)
2. Safely serve static files in nested directories
3. Connect Clojure functions to routes (routes as data, woho!)
4. Deal with one implicit argument for urls of format "/resource/:id"
5. Deal with GET and POST requests (with the help of ring.middleware.params)

# TODO

- remove middleware for wrap-params
- use own server instead of ring-adapter-jetty
- think about routing format
