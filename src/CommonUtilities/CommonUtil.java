package CommonUtilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class CommonUtil {
	
	void Misc() {
		// This is for functions to be used all of the other classes 
	}
	
	 /**
	 * Delete a folder on the HDFS. This is an example of how to interact
	 * with the HDFS using the Java API. You can also interact with it
	 * on the command line, using: hdfs dfs -rm -r /path/to/delete
	 * 
	 * @param conf a Hadoop Configuration object
	 * @param folderPath folder to delete
	 * @throws IOException
	 */
		
	public void deleteFolder(Configuration conf, Path path) throws IOException {
		FileSystem fs = FileSystem.get(conf);
		//Path path = new Path(folderPath); // if folderPath is string argument 
		if(fs.exists(path)) {
			fs.delete(path,true);
		}
	}
	
	
	/**
	 * Split string and extract the numeric value 
	 * Populate the Hashset 
	 * @param String varString 
	 * @return Hashset	 
	 */
		
	public HashSet<Integer> splitvariables(String varString){
		HashSet<Integer> hs = new HashSet<Integer>();
	    StringTokenizer tok = new StringTokenizer(varString,",");
	    while(tok.hasMoreElements())
	    	hs.add(Integer.parseInt(tok.nextToken()));
		return hs;
	}
	////////////////////////////////////////////////////////////////////
	
	public HashSet<String> splitstringvariables(String varString){
		HashSet<String> hs = new HashSet<String>();
	    StringTokenizer tok = new StringTokenizer(varString,",");
	    while(tok.hasMoreElements())
	    	hs.add(tok.nextToken());
		return hs;
	}
	
	////////////////////////////////////////////////////////////////////

	public double calculateProbablity(int featureID,String value,String label, HashMap<String,String> hm, int targetVariable){
	   String classCount,valueCount,totalCount;
	   classCount = hm.get(targetVariable+"_"+label.trim());	   	   
	   double ret = 1.0;	   
	   
	   if(classCount==null) {
		//   System.out.print("\t" + ret);
		   return ret;		  
	   }
	   valueCount = hm.get(featureID+"_"+value+"_"+label);	   
	   //System.out.print("-" + hm.size());
	   
	   if(valueCount==null) {
		  // System.out.print("-" + ret);
		   return ret;
	   }
	   totalCount = hm.get(targetVariable +"");
	   ret = 1.0 * (Double.parseDouble(classCount) / Double.parseDouble(classCount));
		// weights to increase under represented technique
 	   //if (label.contentEquals("1") == true){   ret *= 1.08;   // System.out.print("\t" + ret);  }
	   return ret;
	}
	
	/////////////////////////////////////////////////////	
	public double calculateGaussian(int featureID,String value,String label, HashMap<String,String> hm){
		Double mean,variance,val, ret=1.0;
		if (NumberUtils.isNumber(value) == false)
			return ret;		
		val = Double.parseDouble(value);
		String values = hm.get(featureID+"_"+label);		
		if(values!=null){
	      StringTokenizer tokMeanVariance = new StringTokenizer(values,",");
	      mean = Double.parseDouble(tokMeanVariance.nextToken());
	      variance = Double.parseDouble(tokMeanVariance.nextToken());
	      if(variance==0.0)   	  return 1.0;
	      double sd = Math.sqrt(variance);	 
	      NormalDistribution n =  new NormalDistribution(mean, sd);
	      ret = n.density(val);
	      if(ret ==0.0)	    	  ret = 1.0;
	 //   System.out.print(val + "," + DeviseConfusionMatric.Formate(mean.doubleValue()) + "," +  DeviseConfusionMatric.Formate(variance.doubleValue()) +  " -> "+ ret + "\t");
		}		
		// weights to increase under represented technique
		//if (label.contentEquals("1") == true){   ret *= 1.08;   // System.out.print("\t" + ret);  }
		
		return ret;	
	}	
	
	//////////////////////////////////////////////////////////////////////////////:
    // check wheter it is date or not..
   // covers a lot of date format  
   public static boolean isDate(CharSequence date) {
     //05.10.1981 05-10-1981  07-09-2006 23:00:33  2006-09-07 23:01:24 2003-08-30
     // 2003-30-30    some text   // false
     // some regular expression
     String time = "(\\s(([01]?\\d)|(2[0123]))[:](([012345]\\d)|(60))"
         + "[:](([012345]\\d)|(60)))?"; // with a space before, zero or one time

     // no check for leap years 
     // and 31.02.2006 will also be correct
     String day = "(([12]\\d)|(3[01])|(0?[1-9]))"; // 01 up to 31
     String month = "((1[012])|(0\\d))"; // 01 up to 12
     String year = "\\d{4}";

     // define here all date format
     ArrayList<Pattern> patterns = new ArrayList<Pattern>();
     patterns.add(Pattern.compile(day + "[-.]" + month + "[-.]" + year + time));
     patterns.add(Pattern.compile(year + "-" + month + "-" + day + time));
     // here you can add more date formats if you want

     // check dates
     for (Pattern p : patterns)
       if (p.matcher(date).matches())
         return true;

     return false;

   }
   
   //////////////////////////////////////////////////////////////////////////////:
   // detects the hr:min:sec.millisecond
   public static boolean isTime(CharSequence date) {
     //14:47:43.8216833
     // some regular expression
     String time = "(0?[1-9]|[1][0-9]|2[0123]):(0?[1-9]|[0-5][0-9]|[6][0]):"
             + "(0?[1-9]|[0-5][0-9]|[6][0]).([0-9]+)";

     // define here all date format
     ArrayList<Pattern> patterns = new ArrayList<Pattern>();
     patterns.add(Pattern.compile(time));
     // here you can add more date formats if you want

     // check dates
     for (Pattern p : patterns)
       if (p.matcher(date).matches())
         return true;

     return false;

   }
   
   //////////////////////////////////////////////////////////////////////////////
   // detect double and integer both
   public static boolean isNumeric(String str) {
     return str.matches("^-?[0-9]+(\\.[0-9]+)?$");
     // return s.matches("[-+]?\\d*\\.?\\d+");  not test 
     }

   // detects 3E-9, 0E9 as float
   public static boolean isFloat(String str) {
     try {
      Float.parseFloat(str);
      return true;
     }
     catch (NumberFormatException ex) {
      return false;
     }  
   }

  

   

//////////////////////////////////////////////////////////////////////////////:
   // move it to one class... it also occurs in schemabuilder
   public static void WriteFiles(String Data, String pth) {	
		try {
			File file = new File(pth);
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(Data);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	//////////////////////////////////////////////////////////////////////////////:
   public static void Formulate_Command(String fName, int classIndx) {
	   String line = "";
	   int i=0, j=0, numColumns =0;
	   Long  count;
	   String[] Arr;
	   String continousVariables = "";
	   String discreteVariables = "";
	   HashMap<String, Long> h = new HashMap<String, Long>();
	   // output folder for bash files (test.sh, train.sh etc)
	   String outputFolder = "/home/hduser/Downloads/";
	   
	// take only file name with extension ignoring local path
	   Arr = fName.split("/"); 
	   String fn = Arr[Arr.length-1];
	   
	   String ret= "";
	   
	   String train = "hadoop jar nb.jar bayes.NBTrainDriver -D num_mappers=\"3\" -D num_reducers=\"1\" -D delimiter=\",\"";
	   train += " -D input=\"" + fn + "\" -D output=\"model\" ";
	   
	   String evaluation = "hadoop jar nb.jar bayes.NBModelEvaluationDriver -D num_mappers=\"3\" -D num_reducers=\"1\" -D delimiter=\",\"";
	   evaluation += " -D input=\"" + fn + "\" -D output=\"evalresult\" ";
	   
	   
	   ///////////////////////////////////////
	   try {
			File file = new File(fName);
			BufferedReader br = new BufferedReader(new FileReader(file));		
		    line = br.readLine();
		    Arr = line.split(",");
		    numColumns = Arr.length;
		    // class index is 0 based. if las then provide -1
		    if (classIndx == -1)
		    	classIndx = (Arr.length-1);
		
		    // collecting unique class states (for evaluation and test of the model)
		    count = h.get(Arr[classIndx]);
		    h.put(Arr[classIndx], (count == null) ? 1 : count + 1);
		    
		    // building up the continuous or discrete variables index list
		    for (i=0; i<Arr.length; i++) {
		    	if (i != classIndx) {
			    	if ((isNumeric(Arr[i]) == true) || (isFloat(Arr[i]) == true)) {
			    		j = i+1;
			    		continousVariables +=  "," + j;
			    	}
			    	else {
			    		j = i+1;
			    		discreteVariables += "," + j;
			    	}
		    	}
		    }
		    
		    // remove first unwanted comma 
		    if (discreteVariables.length() > 0) 
		    	discreteVariables = discreteVariables.substring(1, discreteVariables.length());
		    if (continousVariables.length() > 0)
		    	continousVariables = continousVariables.substring(1, continousVariables.length());		    			
	        ////////////////////////////////////////////
	   
		 // extracting class states (for evaluation and test of the model) 
	       while ((line = br.readLine()) != null) {
	    	    Arr = line.split(",");
			    count = h.get(Arr[classIndx]);
			    //System.out.println(h.size());			    
			    h.put(Arr[classIndx], (count == null) ? 1 : count + 1);
	       } // end of the while 		    			    	
			
		} catch (IOException e) {
			e.printStackTrace();
		}
   	
	   // these are common data for all three bash files
	   ret += "-D continousVariables=\"" + continousVariables + "\" ";
	   ret += "-D discreteVariables=\"" + discreteVariables + "\" ";
	   ret += "-D targetVariable=\"" + (classIndx +1) + "\" ";
	   ret += " -D numColumns=\"" + numColumns + "\" ";   
	   
	   // train.sh (run on command line. (bash ./train.sh)
	   train += ret;	   
	   WriteFiles(train, outputFolder + "train.sh");
	   
	   // evaluation.sh (run on command line. (bash ./evaluation.sh)
	   line = "";
	   for (Map.Entry<String, Long> entry : h.entrySet()) {
           line += entry.getKey().toString() + ",";           
       }  
	   line = line.substring(0, line.length()-1);	
	   evaluation +=ret;
	   evaluation += "-D modelPath=\"model\" -D targetClasses=\"" + line + "\"";
	   WriteFiles(evaluation, outputFolder + "evaluation.sh");
	   
	   // test.sh (run on command line. (bash ./test.sh)
	   String test =  evaluation.replace("NBModelEvaluationDriver", "NBTestDriver");
	   test = test.replace("evalresult", "testresult");	   
	   WriteFiles(test, outputFolder + "test.sh");
	   
	   ret = "rm -rfv result\n"; 
	   ret += "hadoop fs -get model result\n";
	   ret += "hadoop fs -get evalresult result\n";
	   ret += "hadoop fs -get testresult result\n";
	   WriteFiles(ret, outputFolder + "getresult.sh");
	   
	   line = train + "\n\n" + evaluation + "\n\n" + test + "\n\n" + ret;
	   
	   WriteFiles(line, outputFolder + "all.sh");
	   
	   // System.out.println(train);	   System.out.println(evaluation); 	   System.out.println(test);
   }
   
	//////////////////////////////////////////////////////////////////////////////:

	//////////////////////////////////////////////////////////////////////////////:

   //////////////////////////////////////////////////////////////////////////////:	
}
