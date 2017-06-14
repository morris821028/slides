import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.util.*;

public class Average extends Configured implements Tool {

	public static class Map extends MapReduceBase
			implements Mapper<LongWritable, Text, Text, IntPair> {
		public void map(LongWritable key, Text value,
				OutputCollector<Text, IntPair> output, Reporter reporter)
				throws IOException {
			StringTokenizer stk = new StringTokenizer(value.toString(), " ");

			while (stk.hasMoreTokens()) {
				// TODO: Task I, transform a line into <Key, IntPair> as you wish
			}
		}
	}

	public static class Combine extends MapReduceBase
			implements Reducer<Text, IntPair, Text, IntPair> {
		public void reduce(Text key, Iterator<IntPair> values,
				OutputCollector<Text, IntPair> output, Reporter reporter)
				throws IOException {
			// TODO: Task II, collect the part of Iterator<IntPair>, 
			// 			and then store them into <Key, IntPair>
		}
	}

	public static class Reduce extends MapReduceBase
			implements Reducer<Text, IntPair, Text, DoubleWritable> {
		public void reduce(Text key, Iterator<IntPair> values,
				OutputCollector<Text, DoubleWritable> output, Reporter reporter)
				throws IOException {
			// TODO: Task I, transform <Key, Iterator<IntPair>> into <Key, Double>
		}
	}

	public static class Partition implements Partitioner<Text, IntPair> {
		@Override
		public void configure(JobConf conf) {
		}

		@Override
		public int getPartition(Text key, IntPair value, int numPartitions) {
			char c = key.toString().charAt(0);
			return c % numPartitions;
		}

	}

	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		JobConf job = new JobConf(conf, Average.class);

		Path in = new Path(args[0]);
		Path out = new Path(args[1]);

		FileInputFormat.setInputPaths(job, in);
		FileOutputFormat.setOutputPath(job, out);

		job.setJobName("Average");

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(DoubleWritable.class);

		job.setPartitionerClass(Partition.class);
		job.setMapperClass(Map.class);
		
		// TODO: Task II
		// job.setCombinerClass(Combine.class);
		job.setReducerClass(Reduce.class);

		job.setInputFormat(TextInputFormat.class);
		job.setOutputFormat(TextOutputFormat.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntPair.class);

		// TODO: Task III (optional)
		job.setNumMapTasks(5);
		job.setNumReduceTasks(2);

		JobClient.runJob(job);

		return 0;
	}

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println(
					"Usage: hadoop jar Average.jar WordCount <HDFS-INPUT-PATH> <HDFS-OUTPUT-PATH>");
			System.exit(1);
		}

		int res = ToolRunner.run(new Configuration(), new Average(), args);
		System.exit(res);
	}
}

