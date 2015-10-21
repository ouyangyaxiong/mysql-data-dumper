package ren.wenchao.mysql.data.dumper.service;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * @author rollenholt
 */
public class SqlSatatementInterceptResult {

    private boolean ret;

    private List<String> messages;

    public SqlSatatementInterceptResult() {
    }

    public SqlSatatementInterceptResult(boolean ret, List<String> messages) {
        this.ret = ret;
        this.messages = messages;
    }

    public boolean isRet() {
        return ret;
    }

    public void setRet(boolean ret) {
        this.ret = ret;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
