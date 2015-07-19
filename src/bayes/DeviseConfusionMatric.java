package bayes;

import java.text.DecimalFormat;
import java.util.*;


public class DeviseConfusionMatric
{	
	private final static long zero = 0;
	
   ///////////////////////////////////////////////////////////////////
   public static String ConfusionMatrix(Map<String, Long> h) {
	  // iterate through all of the actual and identified classes
	   int i,j, sz;
	   String Ret = "";
	   String ky, line = "";
	   Long count;
	   Double Denominator = 1.0; // to ensure non zero denominaotr
	   Double Den = 1.0;
	   
       for (Map.Entry<String, Long> entry : h.entrySet()) {
           ky = entry.getKey().toString();
           line += ky.trim() + ",";
           //count = entry.getValue();           
       }  
       line = line.replace("_", ",");       
       line = line.replace(" ", "");
       
       String[] words = line.split(",");
       Set<String> uniqueWords = new HashSet<String>();
       for (String word : words) {
           uniqueWords.add(word);
       }       
       //System.out.println(line);
       //System.out.println(uniqueWords);       
       // System.out.println(uniqueWords.size());
       
       line = uniqueWords.toString();
       line = line.replace("[", "");
       line = line.replace("]", "");
       line = line.replace(" ", "");
       
       String[] Arr = line.split(",");       
       sz = Arr.length;
       
       for (i=0; i<sz; i++) {
    	   Ret += "\t " + Arr[i];
       }
       Ret += "\n";            
       
       Long TOTAL = zero;
       Long[] TP = new Long[sz];
       Long[] TN = new Long[sz];
       Long[] FP = new Long[sz];
       Long[] FN = new Long[sz];
       Long ACC = zero;
       Long ERR = zero;
        
       
       
       TP = Initialize (TP, sz);
       TN = Initialize (TN, sz);
       FP = Initialize (FP, sz);
       FN = Initialize (FN, sz);
              
       for (i=0; i<sz; i++) {
    	   Ret += Arr[i] + "\t";
    	   
    	   for (j=0; j<sz; j++) {
        	   ky = Arr[i] + "_" + Arr[j];
        	   count = h.get(ky);
        	   if (count == null) {
        		   count = zero;
        	   }        		   
        	   Ret += count.toString() + "\t";
        	           	   
        	   if (i==j) {
        		TP[i] = count;
        		ACC += count;
        	   }
        	   TOTAL += count;
           }   
    	   Ret += "\n";
       }  
              
       Ret += "\nTP\t";
       for (i=0; i<TP.length; i++) {
    	   Ret += TP[i] + "\t";
       }
       
       TN = Find_TN (TN, sz, h, Arr);
       FN = Find_FN (FN, sz, h, Arr);
       FP = Find_FP (FP, sz, h, Arr);
       
       Ret += "\nTN\t";
       for (i=0; i<sz; i++) {
       	   Ret += TN[i] + "\t";
       }
       Ret += "\nFN\t";
       for (i=0; i<sz; i++) {
    	   Ret += FN[i] + "\t";
       }
       Ret += "\nFP\t";
       for (i=0; i<sz; i++) {
    	   Ret += FP[i] + "\t";
       }
       
       double acc = 100.0 * ACC / TOTAL;
       double err = 100.0 - acc;
       ERR = TOTAL - ACC; 
       Ret += "\nTotal Instances: " + TOTAL.toString();
       Ret += "\nCorrectly classified instances: " + ACC.toString() + "\t" + Formate (acc);
       Ret += "\nIncorrectly classified instances: " + ERR.toString() + "\t" + Formate (err);

//   	sensitivity = TPR = TP / (TP + FN)
       Double[] TPR = new Double[sz];       
       Ret += "\nSensitivity (TPR):";
       for (i=0; i<sz; i++) {
    	   Den = 1.0 * (TP[i] + FN[i]);
    	   Denominator = (Den == 0.0) ? 1.0 : Den ;
    	   TPR[i] = 100.0 * TP[i] / Denominator  ;
    	   Ret += "\t" + Formate (TPR[i]);
       }     	
       
//     Specificity (SPC) = TNR = TN / (FP + TN)
       Double[] TNR = new Double[sz];       
       Ret += "\nSpecificity (SPC):";
       for (i=0; i<sz; i++) {
    	   Den = 1.0 * (FP[i] + TN[i]);
    	   Denominator = (Den == 0.0) ? 1.0 : Den ;    	   
    	   TNR[i] = 100.0 * TN[i] / Denominator ;
    	   Ret += "\t" + Formate (TNR[i]);
       }     	

//     Precision = Positive Predictive Value (PPV) = TP / TP + FP
       Double[] PPV = new Double[sz];       
       Ret += "\nPrecision = Positive Predictive Value (PPV):";
       for (i=0; i<sz; i++) {
    	   Den = 1.0 * (TP[i] + FP[i]);  
    	   Denominator = (Den == 0.0) ? 1.0 : Den;
    	   PPV[i] = 100.0 * TP[i] / Denominator;
    	   Ret += "\t" + Formate (PPV[i]);
       }     	
	    
//     Negative Predictive Value (NPV) = TN / TN + FN
       Double[] NPV = new Double[sz];       
       Ret += "\nNegative Predictive Value (NPV):";
       for (i=0; i<sz; i++) {
    	   Den = 1.0 * (TN[i] + FN[i]); 
    	   Denominator = (Den == 0.0) ? 1.0 : Den;    	   
    	   NPV[i] = 100.0 * TN[i] / Denominator;
    	   Ret += "\t" + Formate (NPV[i]);
       }     	
	    
       
//     Fall-Out or False Positive Rate (FPR) =  FP / N = FP / FP + TN
       Double[] FPR = new Double[sz];       
       Ret += "\nFall-Out / False Positive Rate (FPR):";
       for (i=0; i<sz; i++) {
    	   Den = 1.0 * (FP[i] + TN[i]);
    	   Denominator = (Den == 0.0) ? 1.0 : Den;
    	   FPR[i] = 100.0 * FP[i] / Denominator  ;
    	   Ret += "\t" + Formate (FPR[i]);
       }     	
	    
//     False Discovery Rate (FDR) = FDR = FP / FP + TP = 1 - PPV
       Double[] FDR = new Double[sz];       
       Ret += "\nFalse Discovery Rate (FDR):";
       for (i=0; i<sz; i++) {
    	   Den = 1.0 * (FP[i] + TP[i]);
    	   Denominator = (Den == 0.0) ? 1.0 : Den;
    	   FDR[i] = 100.0 * FP[i] / Denominator  ;
    	   Ret += "\t" + Formate (FDR[i]);
       }     	
 
//	   False Negative Rate (FNR) = FNR = FN / FN + TP = 1 - TPR
       Double[] FNR = new Double[sz];       
       Ret += "\nFalse Negative Rate (FNR):";
       for (i=0; i<sz; i++) {
    	   Den = 1.0 * (FN[i] + TP[i]);
    	   Denominator = (Den == 0.0) ? 1.0 : Den;
    	   FNR[i] = 100.0 * FN[i] / Denominator  ;    
    	   Ret += "\t" + Formate (FNR[i]);
       }     	

       
//	   F1 = 2TP  / (2TP + FP + FN)
       Double[] F1 = new Double[sz];       
       Ret += "\nF1 Score:";
       for (i=0; i<sz; i++) {
    	   Den = 1.0 *  (2.0 * TP[i] + FP[i] + FN[i]);
    	   Denominator = (Den == 0.0) ? 1.0 : Den;    	   
    	   F1[i] = 100.0 * 2.0 * TP[i] / Denominator;
    	   Ret += "\t" + Formate (F1[i]);
       }     	
	    
       // Matthews correlation coefficient (MCC) = 
       // ((TP*TN)-(FP*FN)) / (sqrt((TP+FP)*(TP+FN)*(TN+FP)*(TN+FN)))  
       Double[] MCC = new Double[sz];       
       Ret += "\nMatthews correlation coefficient (MCC)";
       for (i=0; i<sz; i++) {    	       	   
    	   Double Numerator = 1.0 * TP[i]  * TN[i]; 
    			  Numerator -= (FP[i] * FN[i]);
    	   Den = 1.0 * (TP[i]+FP[i]) * ( TP[i] + FN[i] ) * ( TN[i] + FP[i] ) * ( TN[i] + FN[i]);
    	   Denominator = (Den == 0.0) ? 1.0 : Den ;    	   
    	    	   Denominator = Math.sqrt(Denominator);
    	   MCC[i] = 100.0 * Numerator / Denominator;
    	   Ret += "\t" + Formate (MCC[i]);
       }  
       
       return Ret;
   }
  
   
   ///////////////////////////////////////////////////////////////////
   private static Long[] Find_TN (Long[] v, int sz, Map<String, Long> h, String[] Arr) {
	   int i=0, j=0, marker =0;
	   String ky = ""; Long count;
	   
	   for (marker=0; marker<sz; marker++) {	  
	   			
		   for (i=0; i<sz; i++) {
			   for (j=0; j<sz; j++) {
		      	   ky = Arr[i].trim() + "_" + Arr[j].trim();
	        	   count = h.get(ky);
	        	   if (count == null) {
	        		   count = zero;
	        	   }      
	        	   if ((i != marker ) && (j != marker )) {
	        		v[marker] += count;
	        	   }	        	   
			   }				   
		   }
	   }
	   return v;
   }

   ///////////////////////////////////////////////////////////////////
   private static Long[] Find_FN (Long[] v, int sz, Map<String, Long> h, String[] Arr) {
	   int i=0, j=0;
	   String ky = ""; Long count;
	   for (i=0; i<sz; i++) {
		   for (j=0; j<sz; j++) {
	      	   ky = Arr[i].trim() + "_" + Arr[j].trim();
        	   count = h.get(ky);
        	   if (count == null) {
        		   count = zero;
        	   }      
        	   if (i != j)  {
        		v[i] += count;
        	   }
		   }				   
	   }	   
	   return v;
   }   

   ///////////////////////////////////////////////////////////////////
   private static Long[] Find_FP (Long[] v, int sz, Map<String, Long> h, String[] Arr) {
	   int i=0, j=0;
	   String ky = ""; Long count;
	   for (i=0; i<sz; i++) {
		   for (j=0; j<sz; j++) {
	      	   ky = Arr[i].trim() + "_" + Arr[j].trim();
        	   count = h.get(ky);
        	   if (count == null) {
        		   count = zero;
        	   }      
        	   if (i != j)  {
        		v[j] += count;
        	   }
		   }				   
	   }	   
	   return v;
   }   

   
   
///////////////////////////////////////////////////////////////////
	private static Long[] Initialize (Long[] v, int sz) {
		for (int i=0; i<sz; i++) {
			v[i] = zero;
			}
		return v;
	}
///////////////////////////////////////////////////////////////////
	public static void Print_Screen (String ConsoleData) {
		System.out.println("\n========================================");
		System.out.print(ConsoleData);
		System.out.println("\n========================================");
	}
///////////////////////////////////////////////////////////////////
	public static String Formate (double d) {
		String pattern = "##.##";
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		String ret = decimalFormat.format(d);
		return ret + "%";
	}
	


		
}
