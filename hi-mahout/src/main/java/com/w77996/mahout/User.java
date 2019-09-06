package com.w77996.mahout;

public class User {

    private final Eat eat;

    public interface  Eat{

    }

    public User(Eat eat) {
        this.eat = eat;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> clas;

        clas = Class.forName("com.w77996.mahout.User.Eat");
        System.out.println(clas.getClassLoader());
//        User.Eat eat = clas.;
    }
}


