INPUT = /user/hadoop/wordcnt-input/*
OUTPUT = wordcnt-output

JAR = WordCount.jar
CP = $(shell hadoop classpath)

.PHONY: all test clean 

all: ${JAR}

WordCount.jar: ./src/*.java
	mkdir -p ./bin
#	hadoop com.sun.tools.javac.Main -d ./bin ./src/*.java
	javac -cp ${CP} -d ./bin ./src/*.java
	jar -cvf $@ -C ./bin .

test:
	-hdfs dfs -rm -r -f wordcnt-output
	-rm -rf local-wordcnt-output
	hadoop jar WordCount.jar WordCount $(INPUT) $(OUTPUT)
	hdfs dfs -copyToLocal wordcnt-output local-wordcnt-output

clean:
	-rm -rf ./bin
	-rm $(JAR)
	-hdfs dfs -rm -r -f $(OUTPUT)
	-rm -rf local-wordcnt-output

