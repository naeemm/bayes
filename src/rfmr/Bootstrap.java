package rfmr;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Class for instances.
 * 
 * @author M. Naeem
 * 
 */
public class Bootstrap implements Copyable<Bootstrap> {
	protected Map<Long,Long> B;
	/**
	 * Returns the values.
	 * 
	 * @return the values.
	 */
	public final Map<Long,Long> getValues() {
		return B;
	}
	
	/**
	 * Sets the value at specified attribute.
	 * 
	 * @param attIndex the attribute index.
	 * @param value the new value to set.
	 */
	public final void setValue(Map<Long,Long> m) {
		B = m;
	}
	
	@Override
	public Bootstrap copy() {
		return new Bootstrap(50L, 30L);
	}
	
	
	 /////////////////////////////////////////////////////////
    private long nextSample(long n) {
    	  Random rng = new Random();    	
    	   long bits, val;
    	   do {
    	      bits = (rng.nextLong() << 1) >>> 1;
    	      val = bits % n;
    	   } while (bits-val+(n-1) < 0L);
    	   return val;
    	}   
        

    ////////////////////////////////////////////////////////
    public  Bootstrap(long instanceCount, long bagSize) {
    	B = new HashMap<Long, Long>();
        long wt = 1;
    	long ky = 1;
    	while (bagSize > 0) {
        	ky = nextSample(instanceCount);
        	if (B.containsKey(ky) == false) {
        		B.put(ky, wt);
        		bagSize = bagSize -1;
        	}
    	} 	
    	
    }



}
