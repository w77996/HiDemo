package com.w77996.exception;

public class ExComsumer implements ExInterface {
    @Override
    public void batch() throws Exception {

            test();
            int i = 1/0;

    }

    private void test(){
        while (true){
            try {
                Thread.sleep(500);
                int i = 1/0;
            }catch (Exception e){
                System.out.println("test ex");
                break;
            }
        }

    }
}
