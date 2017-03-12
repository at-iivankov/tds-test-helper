package app.email.migration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Business Object для парсинга email
 *
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class EmailParserBo {

    public EmailParserDo parse(EmailParserDo emailData) throws Exception {
        String filePath = emailData.getPathToFile();
        String line = "";
        String fileSplitBy = ";";
        String result = "";

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        List<EmailParserDo> emailParserDoList = new ArrayList<EmailParserDo>();
        List<EmailParserDo> emailParserDoListException = new ArrayList<EmailParserDo>();
        while ((line = bufferedReader.readLine()) != null) {
            EmailParserDo emailParserDo = new EmailParserDo();
            String[] row;
            row = line.split(fileSplitBy);
            emailParserDo.setEmail(row[0]);
            emailParserDo.setSubscriptionDate(row[1]);
            emailParserDo.setSiteId(row[2]);
            emailParserDoList.add(emailParserDo);
        }

        for(EmailParserDo emailParserDo : emailParserDoList) {
            if(!emailParserDo.getSiteId().contains("NULL") && !emailParserDoListException.contains(emailParserDo)) {
                result += "<add-item item-descriptor=\"emailNewsSubscription\">" + "\n" +
                        "<set-property name=\"email\"><![CDATA[" + emailParserDo.getEmail() + "]]></set-property>\n" +
                        "<set-property name=\"subscriptionDate\"><![CDATA[" + emailParserDo.getSubscriptionDate() + "]]></set-property>\n" +
                        "<set-property name=\"siteId\"><![CDATA[" + emailParserDo.getSiteId() + "]]></set-property>\n" +
                        "</add-item>" + "\n";
            }
        }

        emailData.setXmlData(result);
        return emailData;
    }
}
