package ren.wenchao.mysql.data.dumper.service;

/**
 * @author rollenholt
 */
public interface SqlStatementInterceptor {

    SqlSatatementInterceptResult doIntercept(String sqlStatement);
}