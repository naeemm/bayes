package bayes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.fs.Path;

import CommonUtilities.CommonUtil;

public class NBModelEvaluationMapper extends MapReduceBase implements Mapper<LongWritable, Text, NullWritable,Text>{
	String delimiter,continousVariables,discreteVariables,targetClasses;	
	int targetVariable,numColums;
	HashSet<Integer> continousVariablesIndex;
	HashSet<Integer> discreteVariablesIndex;
	HashMap<String,String> hm;
	HashSet<String> classesTargetVariables;
	private final static IntWritable one = new IntWritable(1);
	CommonUtil cmn = new CommonUtil();
	
	@Override
	 public void configure(JobConf conf){
		delimiter = conf.get("delimiter");
		numColums = conf.getInt("numColumns", 0);
		continousVariables = conf.get("continousVariables");
		discreteVariables = conf.get("discreteVariables");
		targetClasses = conf.get("targetClasses");
	    targetVariable = conf.getInt("targetVariable",0);
	    discreteVariablesIndex = new HashSet<Integer>();
	    continousVariablesIndex = new HashSet<Integer>();
	    
	    if(continousVariables!=null)
	    continousVariablesIndex = cmn.splitvariables(continousVariables);
	    if(discreteVariables!=null)
	    discreteVariablesIndex = cmn.splitvariables(discreteVariables);
	    classesTargetVariables = cmn.splitstringvariables(targetClasses);
	    
	    hm = new HashMap();
	    try {
			@SuppressWarnings("deprecation")
			// distributed mode only
			// Uri[] filesIncache = DistributedCache.getCacheFiles(conf);
			
			// local mode vs
			Path[] filesIncache = DistributedCache.getLocalCacheFiles(conf);
			for(int i=0;i<filesIncache.length;i++){
				// distributed mode only
				// BufferedReader fis = new BufferedReader(new FileReader(filesIncache[i].getPath().toString()));
				
				BufferedReader fis = new BufferedReader(new FileReader(filesIncache[i].getName().toString()));
				
				String record; 
				 while ((record = fis.readLine()) != null) {
					 String key,value;
					 StringTokenizer tokRecord = new StringTokenizer(record);
					 key = tokRecord.nextToken();
					 value = tokRecord.nextToken();
					 hm.put(key, value); 
				 }			
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void map(LongWritable key, Text value,
			OutputCollector<NullWritable, Text> output, Reporter arg3) throws IOException {
		// TODO Auto-generated method stub 
		String record = value.toString();
	    int featureID = 1;
	    Double probablity=0.0;
	    String features[] = record.split(delimiter);
	    String classified = "";
	    double whichOne = Double.MIN_VALUE;
	    
	    for (String labels : classesTargetVariables){
	    	probablity=1.0;
	    	featureID = 1;
	    	
	      for(int i=0; i<numColums; i++){
	    	if(discreteVariablesIndex.contains(featureID)){
	    	//   output.collect(NullWritable.get(),new Text(featureID + ":" + features[i] + ":" + labels));
	    	   probablity *= cmn.calculateProbablity(featureID,features[i],labels, hm, targetVariable);
	    	}
	    	if(continousVariablesIndex.contains(featureID)){
	    //		output.collect(NullWritable.get(),new Text(featureID + ":" + features[i] + ":" + labels));
	    		probablity *= cmn.calculateGaussian(featureID,features[i],labels, hm);	    	  
	    	}
	    	++featureID;	   	
	     }

	     // decision making phase
	     if ((whichOne < probablity) == true) {
	    	whichOne = probablity;
	    	classified = labels;
	      }
	    }
	    
	    String[] Arr = record.split(",");	    
		record = Arr[Arr.length-1] + "_" + classified;
		output.collect(NullWritable.get(),new Text(record));
	}
}
