import java.util.*;
/**
 * MyHashMap acts like a map.
 * It stores map entries, which hold key value pairs.
 * All keys must be distinct, but the stored values can be the same.
 * 
 * @author Utkarsh Priyam
 * @version v1
 * 
 * @param <K>   the type of key
 * @param <V>   the type of value
 */
public class MyHashMap<K, V> implements Map<K, V>
{
    private static final int NUM_BUCKETS = 5;
    private ListNode<MapEntry<K, V>>[] buckets;
    private int size;
    
    /**
     * A constructor for the MyHashMap class.
     * @postcondition   creates a new, empty hash map
     */
    public MyHashMap()
    {
        buckets = new ListNode[ NUM_BUCKETS ];
        size = 0;
    }
    
    /**
     * Returns the index of the bucket where obj will be found
     * @param obj       the object to find the index of
     * @precondition    obj implements hashCode()
     * @return          the bucket index of obj
     */
    private int toBucketIndex(Object obj)
    {
        return Math.abs(obj.hashCode()) % NUM_BUCKETS;
    }
    
    /**
     * Returns the size of the hash map
     * @return  the number of entries in the hash map
     */
    public int size()
    {
        return size;
    }
    
    /**
     * Returns whether or not the map is empty
     * @return  true if the number of entries in the map is 0; otherwise,
     *          false
     */
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    /**
     * Returns whether or not the hash map contains an entry with the specified key
     * @param key   the key to be searched for
     * @return      true if the hash map contains an entry with the key; otherwise,
     *              false
     */
    @Override
    public boolean containsKey(Object key)
    {
        ListNode<MapEntry<K, V>> node = buckets[ toBucketIndex(key) ];
        while ( node != null )
        {
            if ( node.getValue().getKey().equals( (K) key ) )
                return true;
            node = node.getNext();
        }
        return false;
    }
    
    /**
     * Returns whether or not the hash set contains value
     * @param value the value to be searched for
     * @return      true if the hash set contains valuej; otherwise,
     *              false
     */
    public boolean containsValue(Object value)
    {
        for ( ListNode<MapEntry<K, V>> node: buckets )
            while ( node != null )
            {
                if ( node.getValue().getValue().equals( (V) value ) )
                    return true;
                node = node.getNext();
            }
        return false;
    }
    
    /**
     * Gets the value attached to the key if the key is present in the map.
     * Otherwise, returns null.
     * @param key   the key to be searched for
     * @return      the value attachedto the key if the key is present in the map; otherwise,
     *              false
     */
    public V get(Object key)
    {
        ListNode<MapEntry<K, V>> node = buckets[ toBucketIndex(key) ];
        while ( node != null )
        {
            if ( node.getValue().getKey().equals( (K) key ) )
                return node.getValue().getValue();
            node = node.getNext();
        }
        return null;
    }
    
    /**
     * Adds the specified key value pair to the hash map
     * if the key is not already contained and returns null.
     * If the key is already contained, updates the value
     * to the new value and returns the old value.
     * 
     * @param key       the key in the key value pair
     * @param value     the value in the key value pair
     * @postcondition   the hash map contains an entry with the specified key
     * @return          null if an entry with the key was not already present; otherwise,
     *                  the old value attached to the key
     */
    public V put(K key, V value)
    {
        int index = toBucketIndex(key);
        ListNode<MapEntry<K, V>> node = buckets[ index ];
        while ( node != null )
        {
            if ( node.getValue().getKey().equals( (K) key ) )
            {
                V val = node.getValue().getValue(); 
                node.getValue().setValue( value );
                return val;
            }
            node = node.getNext();
        }
        buckets[ index ] = new ListNode( new MapEntry(key, value), buckets[index] );
        size++;
        return null;
    }
    
    /**
     * Removes the map entry with the specified key if it is present in the map.
     * Returns the attached value, or null if there were no entries with that key.
     * 
     * @param key       the key to be searched for
     * @postcondition   the hash map does not contain any entries with that key
     * @return          null if an entry with the key was not already present; otherwise,
     *                  the value attached to this key
     */
    public V remove(Object key)
    {
        int index = toBucketIndex(key);
        ListNode<MapEntry<K, V>> node = buckets[ index ], prev = null;
        while ( node != null )
        {
            if ( node.getValue().getKey().equals( (K) key ) )
            {
                if ( prev == null )
                    buckets[ index ] = buckets[index].getNext();
                else
                    prev.setNext( node.getNext() );
                size--;
                return node.getValue().getValue();
            }
            prev = node;
            node = node.getNext();
        }
        return null;
    }
    
    /**
     * Adds all elements in Map m into this hash map
     * @param m         the map whose elements will be added to this
     * @postcondition   every key from m is now present in this hash map
     */
    public void putAll(Map<? extends K, ? extends V> m)
    {
        for (K key : m.keySet())
        {
            put(key, m.get(key));
        }
    }
    
    /**
     * Empties this hash map completely
     */
    public void clear()
    {
        for (int i = 0; i < NUM_BUCKETS; i++)
        {
            buckets[i] = null;
        }
    }
    
    /**
     * Returns a set which contains all the keys of the map
     * @return  a set which contains all the keys of the map
     */
    public Set<K> keySet()
    {
        Set<K> keys = new TreeSet<K>();
        for ( ListNode<MapEntry<K, V>> node: buckets )
            while ( node != null )
            {
                keys.add( node.getValue().getKey() );
                node = node.getNext();
            }
        return keys;
    }
    
    /**
     * Returns a collection of all the keys of the map
     * @return  a collection of all the keys of the map
     */
    public Collection<V> values()
    {
        Collection<V> values = new ArrayList<V>();
        for ( ListNode<MapEntry<K, V>> node: buckets )
            while ( node != null )
            {
                values.add( node.getValue().getValue() );
                node = node.getNext();
            }
        return values;
    }
    
    /**
     * Returns a set of all the entries of the map
     * @return  a set of all the entries of the map
     */
    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet()
    {
        Set<java.util.Map.Entry<K, V>> set = new TreeSet<java.util.Map.Entry<K, V>>();
        for ( ListNode<MapEntry<K, V>> node: buckets )
            while ( node != null )
            {
                set.add( node.getValue() );
                node = node.getNext();
            }
        return set;
    }
    
    /**
     * Returns the string form of the map.
     * The map is represented as its key value pairs within curly braces {}.
     * @return  the string form of the map.
     */
    @Override
    public String toString()
    {
        String s = "";
        for ( ListNode<MapEntry<K, V>> node: buckets )
            while ( node != null )
            {
                s += ", " + node.getValue();
                node = node.getNext();
            }
        if ( s.length() == 0 )
            return "{}";
        return "{" + s.substring(2) + "}";
    }
}
