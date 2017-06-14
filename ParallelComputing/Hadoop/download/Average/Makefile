INPUT = /user/hadoop/avg-input/avg-input.small
OUTPUT = avg-output

JAR = Average.jar
CP = $(shell hadoop classpath)

.PHONY: all test clean 

all: ${JAR}

Average.jar: ./src/*.java
	mkdir -p ./bin
#	hadoop com.sun.tools.javac.Main -d ./bin ./src/*.java
	javac -cp ${CP} -d ./bin ./src/*.java
	jar -cvf $@ -C ./bin .

test:
	-hdfs dfs -rm -r -f $(OUTPUT)
	-rm -rf local-$(OUTPUT)
	hadoop jar Average.jar Average $(INPUT) $(OUTPUT)
	hdfs dfs -copyToLocal $(OUTPUT) local-$(OUTPUT)

clean:
	-rm -rf ./bin
	-rm $(JAR)
	-hdfs dfs -rm -r -f $(OUTPUT)
	-rm -rf local-$(OUTPUT)

