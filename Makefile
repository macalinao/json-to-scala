dist: clean
	cp -R public/ dist/
	sbt fastOptJS
	cp ./target/scala-2.11/jsontoscala-fastopt.js ./dist/app.js

clean:
	rm -fr dist/*

deploy:
	cd dist/ && git add -A . && git commit -m "Deploy" && git push origin master:gh-pages
