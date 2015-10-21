package ren.wenchao.mysql.data.dumper.service;

import org.springframework.stereotype.Service;

/**
 * @author rollenholt
 */
@Service
public class DefaultSqlStatementInterceptor implements SqlStatementInterceptor {
    @Override
    public SqlSatatementInterceptResult doIntercept(String sqlStatement) {
        return null;
    }
}
