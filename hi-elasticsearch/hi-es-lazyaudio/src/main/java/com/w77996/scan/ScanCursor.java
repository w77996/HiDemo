package com.w77996.scan;

public interface ScanCursor<T>{

    T getData();

    boolean hasNext();

    int size();

}