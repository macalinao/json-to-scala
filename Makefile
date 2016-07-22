dist: clean
	cp -R public/ dist/
	sbt fastOptJS
	cp ./target/scala-2.11/jsontoscala-fastopt.js ./dist/app.js

clean:
	rm -fr dist/
