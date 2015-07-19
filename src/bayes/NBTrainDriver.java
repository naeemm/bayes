package bayes;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import CommonUtilities.CommonUtil;


public class NBTrainDriver extends Configured implements Tool{

	CommonUtil cmn = new CommonUtil();
	
	@Override
	public int run(String[] arg0) throws Exception {
		// TODO Auto-generated method stub
		JobConf conf = new JobConf(getConf(),NBTrainDriver.class);
		conf.setJobName("Training");
		conf.setMapOutputKeyClass(Text.class);
		conf.setMapOutputValueClass(DoubleWritable.class);
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);
		conf.setNumMapTasks(conf.getInt("numMaps", 4));
		conf.setNumReduceTasks(conf.getInt("numReduce", 1));
		conf.setMapperClass(NBTrainMapper.class);
		conf.setReducerClass(NBTrainReducer.class);		
		
		Path InputPath = new Path(conf.get("input"));
		FileInputFormat.addInputPath(conf, InputPath);		
		Path OutputPath = new Path(conf.get("output"));	
		FileOutputFormat.setOutputPath(conf, OutputPath);
		
		cmn.deleteFolder(conf, OutputPath);		
		JobClient.runJob(conf);
		return 0;
	}
	
	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new NBTrainDriver(), args);
		System.exit(res);
	}
	

	
	
}
