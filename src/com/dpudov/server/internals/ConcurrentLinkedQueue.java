package com.dpudov.server.internals;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ConcurrentLinkedQueue<E> extends AbstractQueue<E> implements Queue<E> {

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super E> action) {

    }

    @Override
    public Stream<E> stream() {
        return null;
    }

    @Override
    public Stream<E> parallelStream() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }

    static final class Node<E> {
        volatile E item;
        volatile Node<E> next;

    }
}
