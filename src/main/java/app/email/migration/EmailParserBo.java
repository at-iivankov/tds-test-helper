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
            String[] row = null;
            row = line.split(fileSplitBy);
            emailParserDo.setEmail(row[0]);
            emailParserDo.setSubscriptionDate(row[1]);
            emailParserDo.setSiteId(row[2]);
            if(emailParserDo.isContainInExceptionList(row[0]))
                emailParserDoListException.add(emailParserDo);
            else emailParserDoList.add(emailParserDo);
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


        int c = 1;
        emailParserDoList = new ArrayList<EmailParserDo>();
        List<String> tempEmailParserEmailList = new ArrayList<String>();
        for(EmailParserDo emailParserDo : emailParserDoListException) {
            EmailParserDo tempEmailParserDo = emailParserDo;
            int a = 0;
            if(emailParserDo.getEmail().equals("oxana.mikhaylova@tele2.ru"))
                a = 0;
            if(!emailParserDo.getSiteId().contains("NULL")) {
                boolean priorityOverFirstFlag = false;
                EmailParserDo tempEmailData = null;
                for(EmailParserDo emailData1: emailParserDoListException) {
                    int b = 0;
                    if(emailData1.getEmail().equals("oxana.mikhaylova@tele2.ru"))
                        b = 0;
                    try {
                        if ((tempEmailParserDo.getEmail().equals(emailData1.getEmail())) && (!tempEmailParserDo.getSiteId().equals(emailData1.getSiteId())) && (tempEmailParserDo.isFirstRegionHigherPriority(emailData1.getSiteId(), tempEmailParserDo.getSiteId()))) {
                            tempEmailData = emailData1;
                            tempEmailParserDo.setSiteIdDefault(emailData1.getSiteId());
                            priorityOverFirstFlag = true;
                        }
                    } catch (Exception e) {
                    }
                }
                if(!priorityOverFirstFlag && !tempEmailParserEmailList.contains(tempEmailParserDo.getEmail())) {
                    emailParserDoList.add(tempEmailParserDo);
                    tempEmailParserEmailList.add(tempEmailParserDo.getEmail());
                }
                else if (priorityOverFirstFlag && !tempEmailParserEmailList.contains(tempEmailData.getEmail())){
                    emailParserDoList.add(tempEmailData);
                    tempEmailParserEmailList.add(tempEmailData.getEmail());
                }
            }
        }

        for(EmailParserDo emailParserDo : emailParserDoList) {
            if(!emailParserDo.getSiteId().contains("NULL")) {
                try {
                    result += "<add-item item-descriptor=\"emailNewsSubscription\">" + "\n" +
                            "<set-property name=\"email\"><![CDATA[" + emailParserDo.getEmail() + "]]></set-property>\n" +
                            "<set-property name=\"subscriptionDate\"><![CDATA[" + emailParserDo.getSubscriptionDate() + "]]></set-property>\n" +
                            "<set-property name=\"siteId\"><![CDATA[ " + emailParserDo.getSiteId() + "]]></set-property>\n" +
                            "</add-item>" + "\n";
                } catch (Exception e) {
                }
            }
        }

        emailData.setXmlData(result);
        return emailData;
    }
}
