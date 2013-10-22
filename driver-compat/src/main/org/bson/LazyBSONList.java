/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bson;

import org.bson.io.BSONByteBuffer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Set;

@SuppressWarnings("rawtypes")
public class LazyBSONList extends LazyBSONObject implements List {

    public LazyBSONList(final byte[] bytes, final LazyBSONCallback callback) {
        super(bytes, callback);
    }

    public LazyBSONList(final byte[] data, final int offset, final LazyBSONCallback callback) {
        super(data, offset, callback);
    }

    public LazyBSONList(final BSONByteBuffer buffer, final LazyBSONCallback callback) {
        super(buffer.array(), callback);
    }

    public LazyBSONList(final BSONByteBuffer buffer, final int offset, final LazyBSONCallback callback) {
        super(buffer.array(), offset, callback);
    }

    @Override
    public int size() {
        return keySet().size();
    }

    @Override
    public boolean contains(final Object o) {
        return indexOf(o) > -1;
    }

    @Override
    public Iterator iterator() {
        return new LazyBSONListIterator();
    }

    @Override
    public boolean containsAll(final Collection collection) {
        Set<Object> values = new HashSet<Object>();
        for (final Object o : this) {
            values.add(o);
        }
        return values.containsAll(collection);
    }

    @Override
    public Object get(final int index) {
        return get(String.valueOf(index));
    }

    @Override
    public int indexOf(final Object o) {
        Iterator it = iterator();
        for (int pos = 0; it.hasNext(); pos++) {
            if (o.equals(it.next())) {
                return pos;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(final Object o) {
        int lastFound = -1;
        Iterator it = iterator();

        for (int pos = 0; it.hasNext(); pos++) {
            if (o.equals(it.next())) {
                lastFound = pos;
            }
        }

        return lastFound;
    }

    public class LazyBSONListIterator implements Iterator {
        private final BSONBinaryReader reader;
        private BSONType cachedBsonType;

        public LazyBSONListIterator() {
            reader = getBSONReader();
            reader.readStartDocument();
        }

        @Override
        public boolean hasNext() {
            if (cachedBsonType == null) {
                cachedBsonType = reader.readBSONType();
            }
            return cachedBsonType != BSONType.END_OF_DOCUMENT;
        }

        @Override
        public Object next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            } else {
                cachedBsonType = null;
                reader.readName();
                return readValue(reader);
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported");
        }

    }

    /* ----------------- Unsupported operations --------------------- */

    @Override
    public ListIterator listIterator() {
        throw new UnsupportedOperationException("Operation is not supported instance of this type");
    }

    @Override
    public ListIterator listIterator(final int index) {
        throw new UnsupportedOperationException("Operation is not supported instance of this type");
    }

    @Override
    public boolean add(final Object o) {
        throw new UnsupportedOperationException("Object is read only");
    }

    @Override
    public boolean remove(final Object o) {
        throw new UnsupportedOperationException("Object is read only");
    }

    @Override
    public boolean addAll(final Collection c) {
        throw new UnsupportedOperationException("Object is read only");
    }

    @Override
    public boolean addAll(final int index, final Collection c) {
        throw new UnsupportedOperationException("Object is read only");
    }

    @Override
    public boolean removeAll(final Collection c) {
        throw new UnsupportedOperationException("Object is read only");
    }

    @Override
    public boolean retainAll(final Collection c) {
        throw new UnsupportedOperationException("Object is read only");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Object is read only");
    }

    @Override
    public Object set(final int index, final Object element) {
        throw new UnsupportedOperationException("Object is read only");
    }

    @Override
    public void add(final int index, final Object element) {
        throw new UnsupportedOperationException("Object is read only");
    }

    @Override
    public Object remove(final int index) {
        throw new UnsupportedOperationException("Object is read only");
    }

    @Override
    public List subList(final int fromIndex, final int toIndex) {
        throw new UnsupportedOperationException("Operation is not supported");
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Operation is not supported");
    }

    @Override
    public Object[] toArray(final Object[] a) {
        throw new UnsupportedOperationException("Operation is not supported");
    }
}