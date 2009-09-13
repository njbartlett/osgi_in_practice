package org.osgi.book.utils;

public interface Visitor<T> {
   void visit(T object);
}