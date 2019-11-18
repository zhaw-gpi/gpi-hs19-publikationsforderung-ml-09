package ch.zhaw.gpi.publfoerderung;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * SendReminderDelegate
 */
@Named("sendReminderAdapter")
public class SendReminderDelegate implements JavaDelegate {

    @Autowired
    private MailService mailService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        String body = "Bitte erledigen Sie Ihre fällige Aufgabe.";

        mailService.sendMail("stab@firma.ch", "Erinnerung für Antrag prüfen", body);
    }    
}