package com.example.fantasyclient;

public interface BidirectionalMessageQueue<S, R> {

    void initCommunicator();

    void enqueue(S s);

    R dequeue();

    void clearQueue();

    boolean isEmpty();
}
