package bayes;

import java.util.*;

import org.apache.commons.math3.distribution.NormalDistribution;

import CommonUtilities.CommonUtil;

// import org.apache.commons.math3.distribution.NormalDistribution;

public class Test
{	
	
	//////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
    //	Map<String, Long> h =  DeviceHashMapStringKey();
    //	String out = DeviseConfusionMatric.ConfusionMatrix(h);
    //	DeviseConfusionMatric.Print_Screen(out);
    // 	System.out.println(DistAnalysis (41.9, 0.0, 0.01));
		String folder = "/home/hduser/Downloads/data/";
    	
 // 	CommonUtil.Formulate_Command(folder + "iris.data",-1); // 96%    	
 //   	CommonUtil.Formulate_Command(folder +  "bank.data",-1); 
 //   	CommonUtil.Formulate_Command(folder +  "kddcup.data",-1);
//    	CommonUtil.Formulate_Command(folder +  "kddcup99.data",-1);// 87.26% accuracy
 //   	CommonUtil.Formulate_Command(folder +  "input.data",-1); // 100% accuracy
 //     CommonUtil.Formulate_Command(folder + "arrhythmia.data",-1); // 67.26%    	 
 // 	CommonUtil.Formulate_Command(folder + "wallmart.data",-1); // 0% accuracy (only one record correctly out 0.46million records
 //     CommonUtil.Formulate_Command(folder + "robots.data",-1);  // 94.88% accuaracy (skewed classes)
 //	 	CommonUtil.Formulate_Command(folder + "westnile.data",-1);  // 75.76% accuaracy (skewed classes)
  	CommonUtil.Formulate_Command(folder + "ionosphere.data",-1);  // 82.62% accuaracy
    	 
	     	   	
     	       	    	
    }
        
    private static double DistAnalysis (double val, double mean, double sd ) {
    	NormalDistribution n =  new NormalDistribution(mean, sd);
	    double prob = n.density(val);
	    return prob;    	
    }
  
 
    
    
    
   /////////////////////////////////////////////////////////////////// 
   private static Map<String, Long> DeviceHashMapStringKey() {
	   Map<String, Long> m = new HashMap<String, Long>();
	   
	   String[] s = populate();
        for (String a : s) {
            Long freq = m.get(a);
            m.put(a, (freq == null) ? 1 : freq + 1);
        }
 //       System.out.println(m.size() + " distinct words:");
 //       System.out.println(m);
       return m;
   }
    
   private static String[] populate () {
	   String[] s = new String[28];
	   s[0] = "A_B";
       s[1] = "B_C";
       s[2] = "A_B";
       s[3] = "A_B";
       s[4] = "B_C";
       s[5] = "A_C";
       s[6] = "C_B";
       s[7] = "C_C";
       s[8] = "A_A";
       s[9] = "A_C";
       s[10] = "C_C";
       s[11] = "C_A";
       s[12] = "A_B";
       s[13] = "B_C";
       s[14] = "B_B";
       s[15] = "C_B";
       s[16] = "A_C";
       s[17] = "C_A";
       s[18] = "A_B";
       s[19] = "B_C";
       s[20] = "A_A"; 
       s[21] = "C_C"; 
       s[22] = "B_B"; 
       s[23] = "C_C";
       s[24] = "A_A";
       s[25] = "A_A"; 
       s[26] = "A_A";
       s[27] = "A_A"; 
       return s;
   }   
///////////////////////////////////////////////////////////////////

   public void produceAlphabetAndNumbersForColumnNames() {
	      char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	      int i,j,k;	      
	      int count = 1;	      
	      String S = "";
	      int sz = alphabet.length;	      
	      for (i=0;  i<sz; i++) {
	          for (j=0; j<sz-1; j++) {              
	              //S += alphabet[i] + "" + alphabet[j] + "," ;
	                  S += count +"," ;	                  
	                 count++;
	                  if (count == 278 ) {                      
	                     sz = 26;
	                  }      
	      }
	      }
	    System.out.print(S);
	    }
   
   
 
}
