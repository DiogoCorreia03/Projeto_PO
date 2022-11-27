# target: dependencies
# 	action

output:
	javac -cp po-uilib.jar:. `find prr -name "*.java"`

run:
	java -cp po-uilib.jar:. prr.app.App

clean:
	find prr -name \*.class -type f -delete
