# webframework

Goal: Make a web framework in under 4 hours and under 100 lines of code.

Requires `leiningen` (www.leiningen.org) on your computer.

`lein run` to run a server on port 3000 (or `lein ring server` if you have `lein-ring` installed). Some urls to go to:

`http://localhost:3000/foo/bar.html`

`http://localhost:3000/../project.clj`

`http://localhost:3000/users/meep`

`http://localhost:3000/users/ghost`

`http://localhost:3000/users/` post a user then go to that url.

All the code is in `src/webframework/core.clj`. Some tests in the `test`
directory. Not a whole lot of documentation.

# What it can do

1. Listen to http traffic on port 3000 (with the help of ring.adapter.jetty)
2. Safely serve static files in nested directories
3. Connect Clojure functions to routes (routes as data, woho!)
4. Deal with one implicit argument for urls of format "/resource/:id"
5. Deal with GET and POST requests (with the help of ring.middleware.params)

# TODO

- remove middleware for wrap-params
- use own server instead of ring-adapter-jetty
- think about routing format (separation of concerns etc)
- modularize it
