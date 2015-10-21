package ren.wenchao.mysql.data.dumper.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * @author rollenholt
 */
@Service
public class SqlStatementHandlerManager {

    @Resource
    private DefaultSqlStatementInterceptor defaultSqlStatementInterceptor;

    private final List<SqlStatementInterceptor> sqlStatementInterceptors =
            Lists.newCopyOnWriteArrayList();

    @PostConstruct
    public void init() {
        register(defaultSqlStatementInterceptor);
    }

    public void register(SqlStatementInterceptor sqlStatementInterceptor) {
        Preconditions.checkNotNull(sqlStatementInterceptor);
        sqlStatementInterceptors.add(sqlStatementInterceptor);
    }

    public void unregister(SqlStatementInterceptor sqlStatementInterceptor) {
        Preconditions.checkNotNull(sqlStatementInterceptor);
        sqlStatementInterceptors.remove(sqlStatementInterceptor);
    }

    public List<String> doIntercept(String sqlStatement) {
        checkArgument(!isNullOrEmpty(sqlStatement));
        List<SqlSatatementInterceptResult> sqlSatatementInterceptResults = sqlStatementInterceptors
                .stream().map(item -> item.doIntercept(sqlStatement)).collect(Collectors.toList());

        List<String> messages = sqlSatatementInterceptResults.stream().filter(item -> !item.isRet())
                .flatMap(item -> item.getMessages().stream()).collect(Collectors.toList());
        return messages;
    }
}
