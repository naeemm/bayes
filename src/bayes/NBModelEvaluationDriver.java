package bayes;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.lib.MultipleOutputs;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import CommonUtilities.CommonUtil;



public class NBModelEvaluationDriver extends Configured implements Tool{
	CommonUtil cu = new CommonUtil();
	
	////////////////////////////////////////////////////////////////
	@SuppressWarnings("deprecation")
	@Override
	public int run(String[] arg0) throws Exception {
		
		
		// TODO Auto-generated method stub
		JobConf conf = new JobConf(getConf(),NBModelEvaluationDriver.class);
		Job job = new Job(conf, "Multi-view NaiveBayes Training");
		DistributedCache.addCacheFile(new Path(conf.get("modelPath")+"/part-00000").toUri(), conf);
		conf.setMapperClass(NBModelEvaluationMapper.class);
		conf.setReducerClass(NBModelEvaluationReducer.class);
		
		conf.setOutputKeyClass(NullWritable.class);
		conf.setOutputValueClass(Text.class);
		conf.setNumMapTasks(conf.getInt("numMaps", 1));
		conf.setNumMapTasks(conf.getInt("numReduce", 1));
		
		Path InputPath = new Path(conf.get("input"));		
		FileInputFormat.addInputPath(conf, InputPath);
		
		Path OutputPath = new Path(conf.get("output"));		
	    FileOutputFormat.setOutputPath(conf, OutputPath);
	    
	    cu.deleteFolder(conf, OutputPath);
	    
	    JobClient.runJob(conf);
		return 0;		
	}
	
	////////////////////////////////////////////////////////////////
	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new NBModelEvaluationDriver(), args);
		System.exit(res);
	}
	////////////////////////////////////////////////////////////////
}
