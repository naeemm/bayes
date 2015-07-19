package rfmr;

import java.util.*;
import java.util.Map.Entry;

public class Test
{	
	
	//////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
    	Bootstrap m = new Bootstrap(300,7);
    	System.out.print( "\t" + m.getValues().size());	
    	    		
    	Iterator<Entry<Long, Long>> it = m.getValues().entrySet().iterator();
        while (it.hasNext()) {
        	Map.Entry<Long, Long> pair = (Entry<Long, Long>)it.next();
            System.out.println("\t" + pair.getKey() + " = " + pair.getValue());
    			
		} 
		   	   	
     	       	    	
    }
    ////////////////////////////////////////////////////////
    
    /////////////////////////////////////////////////////////
    public static long nextLongRand(long n) {
    	   // error checking and 2^x checking removed for simplicity.
    	  Random rng = new Random();    	
    	   long bits, val;
    	   do {
    	      bits = (rng.nextLong() << 1) >>> 1;
    	      val = bits % n;
    	   } while (bits-val+(n-1) < 0L);
    	   return val;
    	}
    
        

    ////////////////////////////////////////////////////////
    public static Map<Long,Long> bagIndexes(long instanceCount, long bagSize) {
    	Map<Long, Long> m = new HashMap<Long, Long>();
        long wt = 1;
    	long ky = 1;
    	while (bagSize > 0) {
        	ky = nextLongRand(instanceCount);
        	if (m.containsKey(ky) == false) {
        		m.put(ky, wt);
        		bagSize = bagSize -1;
        	}
    	}
    	return m; 
    	
    }

///////////////////////////////////////////////////////////////////
 
}
