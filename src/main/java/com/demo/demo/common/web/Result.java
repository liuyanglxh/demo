package com.demo.demo.common.web;

/**
 * Created by liuyang on 2018/11/11
 */
public class Result {

    private boolean success;
    private String message;

    public static Result success(String message){
        Result result = new Result();
        result.success = true;
        result.message = message;
        return result;
    }

    public static Result success(){
        return success(null);
    }

    public static Result fail(String message){
        Result result = new Result();
        result.success = false;
        result.message = message;
        return result;
    }

    public static Result fail(){
        return fail(null);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
