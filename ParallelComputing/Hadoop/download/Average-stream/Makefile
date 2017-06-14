HADOOP_PATH=/usr/local/hadoop-2.7

INPUT = /user/hadoop/avg-input/avg-input.small
OUTPUT = avg-output

TOY_INPUT = avg.in

.PHONY: all toy-test test clean

all: 
	@echo "make"
	@echo "\ttoy-test\t Run the small by standard IO"
	@echo "\ttest\t\t Submit your code to Hadoop cluster"

toy-test:
	./mapper.py <$(TOY_INPUT) | sort -k1,1 | ./combiner.py | ./reducer.py

test:
	-rm -rf local-$(OUTPUT)
	-hdfs dfs -rm -r -f avg-output 
	hadoop jar $(HADOOP_PATH)/share/hadoop/tools/lib/hadoop-streaming-2.7.2.jar \
			-files mapper.py,reducer.py,combiner.py \
			-mapper mapper.py \
			-reducer reducer.py \
			-combiner combiner.py \
			-input $(INPUT) \
			-output $(OUTPUT) \
			-numReduceTasks 2
	hdfs dfs -copyToLocal $(OUTPUT) local-$(OUTPUT)

clean:
	-rm -rf local-$(OUTPUT)
	-hdfs dfs -rm -r -f $(OUTPUT)
