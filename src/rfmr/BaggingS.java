package rfmr;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BaggingS {

	
	 BaggingS(long instanceCount, long bagSize) {
	    	Map<Long, Long> m = new HashMap<Long, Long>();
			for (int i=0; i<1; i++) {
				m = BootStrapSamples (500,100);
		    	System.out.print( "\t" + m.size());	
			}    	   	
	     	       	    	
	    }
	
	 /////////////////////////////////////////////////////////
    private long nextSample(long n) {
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
    private Map<Long,Long> BootStrapSamples(long instanceCount, long bagSize) {
    	Map<Long, Long> m = new HashMap<Long, Long>();
        long wt = 1;
    	long ky = 1;
    	while (bagSize > 0) {
        	ky = nextSample(instanceCount);
        	if (m.containsKey(ky) == false) {
        		m.put(ky, wt);
        		bagSize = bagSize -1;
        	}
    	}
    	return m; 
    	
    }

	
	
}
