import java.util.Map.Entry;

/**
 * MapEntry is a map entry for the MyHashMap class
 * It stores a key value pair.
 * 
 * @author Utkarsh Priyam
 * @version v1
 * 
 * @param <K>   the type of key
 * @param <V>   the type of value
 */
public class MapEntry<K, V> implements Entry<K, V>
{
    private K key;
    private V value;

    /**
     * Creates a new map entry
     * @param k     the initial key for the entry
     * @param val   the initial value for the entry
     */
    public MapEntry( K k, V val )
    {
        key = k;
        value = val;
    }

    /**
     * Returns the entry's key
     * @return  the entry's key
     */
    public K getKey()
    {
        return key;
    }

    /**
     * Sets the key to k
     * @param k the new key
     */
    public void setKey( K k )
    {
        key = k;
    }

    /**
     * Returns the entry's value
     * @return  the entry's value
     */
    public V getValue()
    {
        return value;
    }

    /**
     * Sets the entry's value to val and returns the old value
     * @param val   the new value
     * @return      the old value
     */
    public V setValue( V val )
    {
        V old = value;
        value = val;
        return old;
    }

    /**
     * Returns whether or not the entries are equal (same key)
     * @param entry the entry to check
     * @return      true if the map entries are equal; otherwise,
     *              false
     */
    public boolean equals( MapEntry entry )
    {
        return key.equals( entry.getKey() );
    }
    
    /**
     * Returns the string form of the entry (key=value)
     * @return  the string form of the entry
     */
    public String toString()
    {
        return key + "=" + value;
    }
}
