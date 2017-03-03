package app.email.migration;

/**
 * Сервис для парсинга email
 *
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class EmailParserSrv {

    public static EmailParserDo parseEmails(EmailParserDo emailParserDo) throws Exception {
        EmailParserBo emailParserBo = new EmailParserBo();
        return emailParserBo.parse(emailParserDo);
    }
}
