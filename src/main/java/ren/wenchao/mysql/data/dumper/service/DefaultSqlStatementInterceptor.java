package ren.wenchao.mysql.data.dumper.service;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

/**
 * @author rollenholt
 */
@Service
public class DefaultSqlStatementInterceptor implements SqlStatementInterceptor {

    private final Set<String> excludeKeyWords = Sets.newHashSet("delete",
            "drop", "truncate", "create", "insert", "update");

    private final String msg = "sql statement can not contains the follow keywords:\n "
            + excludeKeyWords.toString();

    @Override
    public SqlSatatementInterceptResult doIntercept(String sqlStatement) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(sqlStatement));
        for (String excludeKeyWord : excludeKeyWords) {
            if (sqlStatement.contains(excludeKeyWord)) {
                return new SqlSatatementInterceptResult(false, Lists.newArrayList(msg));
            }
        }
        return new SqlSatatementInterceptResult(true, Collections.emptyList());
    }
}
