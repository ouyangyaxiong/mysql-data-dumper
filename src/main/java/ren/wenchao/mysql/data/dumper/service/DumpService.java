package ren.wenchao.mysql.data.dumper.service;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.stereotype.Service;
import ren.wenchao.mysql.data.dumper.model.DbConf;
import ren.wenchao.mysql.data.dumper.model.DumpRequest;

import javax.annotation.Resource;
import java.time.LocalTime;
import java.util.Map;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * @author rollenholt
 */
@Service
public class DumpService {

    @Resource
    private DbConfigLoader dbConfigLoader;

    private final String scriptTemplate1 = "mysqldump -h${sourceIp} -u${sourceUsername} -p${sourcePassword} -P${sourcePort}" +
            " --skip-lock-tables ${sourceDatabase} ${tableNames} > /tmp/${fileName}; cat /tmp/${fileName} | " +
            "mysql -h${destinationIp} -u${destinationUsername} -p${destinationPassword} -P${destinationPort} ${destinationDatabase};" +
            " rm /tmp/${fileName};";

    private final String scriptTemplate2 = "mysqldump -h${sourceIp} -u${sourceUsername} -p${sourcePassword} -P${sourcePort}" +
            " --skip-lock-tables ${sourceDatabase} ${tableNames} > /tmp/${fileName}; cat /tmp/${fileName} | " +
            "mysql -h${destinationIp} -u${destinationUsername}  -P${destinationPort} ${destinationDatabase};" +
            " rm /tmp/${fileName};";

    private final String scriptTemplate3 = "mysqldump -h${sourceIp} -u${sourceUsername} -P${sourcePort}" +
            " --skip-lock-tables ${sourceDatabase} ${tableNames} > /tmp/${fileName}; cat /tmp/${fileName} | " +
            "mysql -h${destinationIp} -u${destinationUsername} -p${destinationPassword} -P${destinationPort} ${destinationDatabase};" +
            " rm /tmp/${fileName};";

    private final String scriptTemplate4 = "mysqldump -h${sourceIp} -u${sourceUsername} -P${sourcePort}" +
            " --skip-lock-tables ${sourceDatabase} ${tableNames} > /tmp/${fileName}; cat /tmp/${fileName} | " +
            "mysql -h${destinationIp} -u${destinationUsername} -P${destinationPort}  ${destinationDatabase};" +
            " rm /tmp/${fileName};";

    public String generateDumpScript(DumpRequest dumpRequest) {
        String sourceDatabase = dumpRequest.getSourceDatabase();
        String tableNames = dumpRequest.getTableNames();

        DbConf dbConf = dbConfigLoader.getByDatabase(sourceDatabase);

        Map<String, String> infoHolder = Maps.newHashMap();
        infoHolder.put("sourceDatabase", sourceDatabase);
        infoHolder.put("sourceIp", dbConf.getHost());
        infoHolder.put("sourcePort", dbConf.getPort());
        infoHolder.put("sourceUsername", dbConf.getUsername());
        String password = dbConf.getPassword();
        infoHolder.put("sourcePassword", password);
        if (tableNames.equals("*")) {
            infoHolder.put("tableNames", "");
        } else {
            String tables = Joiner.on(" ").join(Splitter.on(",").splitToList(tableNames));
            infoHolder.put("tableNames", tables);
        }
        infoHolder.put("fileName", LocalTime.now().toString());
        infoHolder.put("destinationDatabase", dumpRequest.getDestinationDatabase());
        infoHolder.put("destinationIp", dumpRequest.getDestinationHost());
        infoHolder.put("destinationPort", dumpRequest.getDestinationPort());
        infoHolder.put("destinationUsername", dumpRequest.getDestinationDatabaseUser());
        String destinationDatabasePassword = dumpRequest.getDestinationDatabasePassword();
        infoHolder.put("destinationPassword", destinationDatabasePassword);

        StrSubstitutor strSubstitutor = new StrSubstitutor(infoHolder);
        if (isNullOrEmpty(password) && isNullOrEmpty(destinationDatabasePassword)) {
            return strSubstitutor.replace(scriptTemplate4);
        } else if (isNullOrEmpty(password) && !isNullOrEmpty(destinationDatabasePassword)) {
            return strSubstitutor.replace(scriptTemplate3);
        } else if (!isNullOrEmpty(password) && isNullOrEmpty(destinationDatabasePassword)) {
            return strSubstitutor.replace(scriptTemplate2);
        } else {
            return strSubstitutor.replace(scriptTemplate1);
        }
    }

}
