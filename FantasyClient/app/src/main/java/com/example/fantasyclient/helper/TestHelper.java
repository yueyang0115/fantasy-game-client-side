package com.example.fantasyclient.helper;

public class TestHelper {

    public static class MyClass{
        int x = 0;
        MyClass(int x1){
            x = x1;
        }
        int getX(){
            return x;
        }
        public void setX(int x1){
            x = x1;
        }
    }

    public static void run(){
        System.out.println("Hello World");
        MyClass myClass = handleMyClass();
        System.out.println("Main:" + myClass.getX());
    }

    private static MyClass handleMyClass(){
        MyClass myClass = new MyClass(3);
        System.out.println("Handle:" + myClass.getX());
        return myClass;
    }
}
