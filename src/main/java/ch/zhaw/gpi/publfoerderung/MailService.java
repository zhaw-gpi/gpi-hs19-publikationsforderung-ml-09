package ch.zhaw.gpi.publfoerderung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * MailService
 */
@Component
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;
    
    @Value("${mail.overrideAddress}")
    private String overrideAddress;

    @Value("${spring.mail.username}")
    private String userName;

    public void sendMail(String to, String subject, String body){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        if(!overrideAddress.isEmpty()){
            to = overrideAddress;
        }

        mailMessage.setFrom(userName);
        mailMessage.setReplyTo(userName);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);
        mailMessage.setTo(to);

        try {
            javaMailSender.send(mailMessage);
            System.out.println("MAIL SENDEN ERFOLGREICH");
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
    }
}