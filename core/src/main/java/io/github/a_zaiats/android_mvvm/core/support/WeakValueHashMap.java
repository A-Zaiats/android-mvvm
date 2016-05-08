package io.github.a_zaiats.android_mvvm.core.support;

/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the "License").  You may not use this file except
 * in compliance with the License.
 *
 * You can obtain a copy of the license at
 * glassfish/bootstrap/legal/CDDLv1.0.txt or
 * https://glassfish.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * HEADER in each file and include the License file at
 * glassfish/bootstrap/legal/CDDLv1.0.txt.  If applicable,
 * add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your
 * own identifying information: Portions Copyright [yyyy]
 * [name of copyright owner]
 */

/*
 * Copyright 2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * A WeakValueHashMap is implemented as a HashMap that maps keys to
 * WeakValues.  Because we don't have access to the innards of the
 * HashMap, we have to wrap/unwrap value objects with WeakValues on
 * every operation.  Fortunately WeakValues are small, short-lived
 * objects, so the added allocation overhead is tolerable. This
 * implementaton directly extends java.util.HashMap.
 *
 * @author Markus Fuchs
 * @see java.util.HashMap
 * @see java.lang.ref.WeakReference
 */

/**
 * Generics support and quality fixes by Andrei Zaiats
 * @param <K> - the type of the key object
 * @param <V> - the type of the value object
 */
public class WeakValueHashMap<K, V> extends AbstractMap<K, V> {

    /* Reference queue for cleared WeakValues */
    private ReferenceQueue<V> queue;
    /* The internal hash map to the weak references of the actual value objects */
    private HashMap<K, WeakValue<V>> references;

    /**
     * Creates a WeakValueHashMap with a desired initial capacity
     * @param capacity - the initial capacity
     */
    public WeakValueHashMap(int capacity) {
        references = new HashMap<>(capacity);
        queue = new ReferenceQueue<>();
    }

    /**
     * Creates a WeakValueHashMap with an initial capacity of 1
     */
    public WeakValueHashMap() {
        this(1);
    }

    /**
     * Returns the number of key-value mappings in this map.<p>
     *
     * @return the number of key-value mappings in this map.
     */
    public int size() {
        processQueue();
        return references.size();
    }

    /**
     * Returns <tt>true</tt> if this map contains no key-value mappings.<p>
     *
     * @return <tt>true</tt> if this map contains no key-value mappings.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns <tt>true</tt> if this map contains a mapping for the specified
     * key.<p>
     *
     * @param key key whose presence in this map is to be tested
     * @return <tt>true</tt> if this map contains a mapping for the specified
     * key.
     */
    public boolean containsKey(Object key) {
        processQueue();
        return references.containsKey(key);
    }

    /**
     * Returns <tt>true</tt> if this map maps one or more keys to the
     * specified value.<p>
     *
     * @param value value whose presence in this map is to be tested
     * @return <tt>true</tt> if this map maps one or more keys to this value.
     */
    public boolean containsValue(Object value) {
        processQueue();
        for (Map.Entry<K, WeakValue<V>> entry : references.entrySet()) {
            if (value == getReferenceObject(entry.getValue())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the value for the given key.<p>
     *
     * @param key key whose associated value, if any, is to be returned
     * @return the value to which this map maps the specified key.
     */
    @Nullable
    public V get(Object key) {
        // We don't need to remove garbage collected values here;
        // if they are garbage collected, the get() method returns null;
        // the next put() call with the same key removes the old value
        // automatically so that it can be completely garbage collected
        return getReferenceObject(references.get(key));
    }

    /**
     * Puts a new (key,value) into the map.<p>
     *
     * @param key   key with which the specified value is to be associated.
     * @param value value to be associated with the specified key.
     * @return previous value associated with specified key, or null
     * if there was no mapping for key or the value has been garbage
     * collected by the garbage collector.
     */
    @Nullable
    public V put(K key, V value) {
        // If the map already contains an equivalent key, the new key
        // of a (key, value) pair is NOT stored in the map but the new
        // value only. But as the key is strongly referenced by the
        // map, it can not be removed from the garbage collector, even
        // if the key becomes weakly reachable due to the old
        // value. So, it isn't necessary to remove all garbage
        // collected values with their keys from the map before the
        // new entry is made. We only clean up here to distribute
        // clean up calls on different operations.
        processQueue();

        WeakValue<V> oldValue = references.put(key, new WeakValue<>(key, value, queue));
        return getReferenceObject(oldValue);
    }

    /**
     * Removes key and value for the given key.<p>
     *
     * @param key key whose mapping is to be removed from the map.
     * @return previous value associated with specified key, or null
     * if there was no mapping for key or the value has been garbage
     * collected by the garbage collector.
     */
    @Nullable
    public V remove(Object key) {
        return getReferenceObject(references.remove(key));
    }

    @Override
    public void clear() {
        references.clear();
    }

    /**
     * A convenience method to return the object held by the
     * weak reference or <code>null</code> if it does not exist.
     */
    @Nullable
    private V getReferenceObject(WeakValue<V> ref) {
        return (ref == null) ? null : ref.get();
    }

    /**
     * Removes all garbage collected values with their keys from the map.
     * Since we don't know how much the ReferenceQueue.poll() operation
     * costs, we should not call it every map operation.
     */
    @SuppressWarnings("unchecked")
    private void processQueue() {
        WeakValue<V> wv;

        while ((wv = (WeakValue<V>) queue.poll()) != null) {
            references.remove(wv.key);
        }
    }

    @Override
    @NonNull
    public Set<K> keySet() {
        processQueue();
        return references.keySet();
    }

    /**
     * Returns a <code>Set</code> view of the mappings in this map.<p>
     *
     * @return a <code>Set</code> view of the mappings in this map.
     */
    @NonNull
    public Set<Map.Entry<K, V>> entrySet() {
        processQueue();
        Set<Map.Entry<K, V>> entries = new LinkedHashSet<>();
        for (Map.Entry<K, WeakValue<V>> entry : references.entrySet()) {
            entries.add(new AbstractMap.SimpleEntry<>(entry.getKey(), getReferenceObject(entry.getValue())));
        }
        return entries;
    }

    /**
     * Returns a <code>Collection</code> view of the values contained
     * in this map.<p>
     *
     * @return a <code>Collection</code> view of the values contained
     * in this map.
     */
    @NonNull
    public Collection<V> values() {
        processQueue();

        Collection<V> values = new ArrayList<>();
        for (WeakValue<V> valueRef : references.values()) {
            values.add(getReferenceObject(valueRef));
        }
        return values;
    }

    /**
     * We need this special class to keep the backward reference from
     * the value to the key, so that we are able to remove the key if
     * the value is garbage collected.
     */
    private final class WeakValue<T> extends WeakReference<T> {
        /**
         * It's the same as the key in the map. We need the key to remove
         * the value if it is garbage collected.
         */
        private K key;

        private WeakValue(K key, T value, ReferenceQueue<T> queue) {
            super(value, queue);
            this.key = key;
        }

        /**
         * A WeakValue is equal to another WeakValue iff they both refer
         * to objects that are, in turn, equal according to their own
         * equals methods.
         */
        public boolean equals(Object obj) {
            if (this == obj)
                return true;

            if (!(obj instanceof WeakValue))
                return false;

            Object ref1 = this.get();
            Object ref2 = ((WeakValue) obj).get();

            if (ref1 == ref2)
                return true;

            if ((ref1 == null) || (ref2 == null))
                return false;

            return ref1.equals(ref2);
        }

        /**
         *
         */
        public int hashCode() {
            Object ref = this.get();

            return (ref == null) ? 0 : ref.hashCode();
        }
    }
}
