import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.util.*;

public class WordCount extends Configured implements Tool {

	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
		public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter)
				throws IOException {
			StringTokenizer stk = new StringTokenizer(value.toString());
			IntWritable one = new IntWritable(1);
			while (stk.hasMoreTokens()) {
				output.collect(new Text(stk.nextToken()), one);
			}
		}
	}

	public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {
		public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output,
				Reporter reporter) throws IOException {
			int sum = 0;
			while (values.hasNext())
				sum += values.next().get();
			output.collect(key, new IntWritable(sum));
		}
	}

	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		JobConf job = new JobConf(conf, WordCount.class);

		Path in = new Path(args[0]);
		Path out = new Path(args[1]);

		FileInputFormat.setInputPaths(job, in);
		FileOutputFormat.setOutputPath(job, out);

		job.setJobName("My First Job - WordCount");

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		job.setMapperClass(Map.class);
		job.setCombinerClass(Reduce.class);
		job.setReducerClass(Reduce.class);

		job.setInputFormat(TextInputFormat.class);
		job.setOutputFormat(TextOutputFormat.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		JobClient.runJob(job);

		return 0;
	}	

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("Usage: hadoop jar WordCount.jar WordCount <HDFS-INPUT-PATH> <HDFS-OUTPUT-PATH>");
			System.exit(1);
		}

		int res = ToolRunner.run(new Configuration(), new WordCount(), args);
		System.exit(res);
	}
}

