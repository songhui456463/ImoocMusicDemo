package com.sh.imoocmusicdemo.utils;

import android.os.Handler;

//program--Thread
public class Test {
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        new MyThread().start();
        new MyThread().start();

//        MyThread mt=new MyThread();
//        new Thread(mt).start();
//        new Thread(mt).start();


    }


    static class MyThread extends Thread{
        private int ticket = 5;
        public void run(){
            while(true){
                System.out.println("Thread ticket = " + ticket--);
                if(ticket < 0){
                    break;
                }
            }
        }
    }
}
