package ch.zhaw.gpi.publfoerderung;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * SendDecisionDelegate
 */
@Named("sendDecisionAdapter")
public class SendDecisionDelegate implements JavaDelegate {

    @Autowired
    private MailService mailService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String applicant = (String) execution.getVariable("applicant");
        Integer score = (Integer) execution.getVariable("score");
        Boolean decision = (Boolean) execution.getVariable("decision");
        String decisionReasons = (String) execution.getVariable("decisionReasons");

        String anredeKommabt = "Lieber Stab\n\n";
        String anredeMitarbeiter = "Lieber Antragsteller\n\n";

        String subject;
        String begruendung;
        String bodyMain;

        if(score < 4 || !decision){
            if(score < 4){
                begruendung = "Der automatisch berechnete Score ist kleiner als 4.";
            } else {
                begruendung = decisionReasons;
            }

            subject = "Der Antrag wurde abgelehnt";
            bodyMain = "Der Antrag wurde abgelehnt mit folgender Begründung: " + begruendung;
        } else {
            subject = bodyMain = "Der Antrag wurde angenommen.";
        }

        mailService.sendMail("kommabt@firma.ch", subject, anredeKommabt + bodyMain + "\n\nIhre Prozessplattform");
        mailService.sendMail(applicant + "@firma.ch", subject, anredeMitarbeiter + bodyMain + "\n\nIhre Prozessplattform");
    }    
}