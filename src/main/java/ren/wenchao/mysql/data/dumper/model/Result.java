package ren.wenchao.mysql.data.dumper.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * @author rollenholt
 */
public class Result<T> implements Serializable {

    private boolean ret;

    private String message;

    private T data;

    public Result() {
    }

    public Result(boolean ret, String message, T data) {
        this.ret = ret;
        this.message = message;
        this.data = data;
    }

    public boolean isRet() {
        return ret;
    }

    public void setRet(boolean ret) {
        this.ret = ret;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
