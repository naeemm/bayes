package rfmr;

/**
 * Abstract class for attributes.
 * 
 * @author M. Naeem
 * 
 */
public abstract class BootsrapS implements Comparable<BootsrapS>, Copyable<BootsrapS> {

	public enum Type {
		/**
		 * Nominal type.
		 */
		NOMINAL,
		/**
		 * Numeric type.
		 */
		NUMERIC,
		/**
		 * Binned type.
		 */
		BINNED;
	}

	protected Type type;

	protected int index;

	protected String name;

	/**
	 * Returns the type of this attribute.
	 * 
	 * @return the type of this attribute.
	 */
	public final Type getType() {
		return type;
	}

	/**
	 * Returns the index of this attribute.
	 * 
	 * @return the index of this attribute.
	 */
	public final int getIndex() {
		return index;
	}

	/**
	 * Sets the index of this attribute.
	 * 
	 * @param index the new index.
	 */
	public final void setIndex(int index) {
		this.index = index;
	}

	/**
	 * Returns the name of this attribute.
	 * 
	 * @return the name of this attribute.
	 */
	public final String getName() {
		return name;
	}

	@Override
	public int compareTo(BootsrapS bt) {
		return (this.index - bt.index);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BootsrapS other = (BootsrapS) obj;
		if (index != other.index)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name;
	}

}
