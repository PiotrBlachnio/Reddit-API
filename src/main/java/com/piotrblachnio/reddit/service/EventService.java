package com.piotrblachnio.reddit.service;

import com.piotrblachnio.reddit.model.NotificationEmail;
import com.piotrblachnio.reddit.model.User;
import com.piotrblachnio.reddit.model.VerificationToken;
import com.piotrblachnio.reddit.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EventService {
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;

    public void sendConfirmationMail(User user) {
        var token = UUID.randomUUID().toString();

        var verificationToken = new VerificationToken(user);

        verificationTokenRepository.save(verificationToken);

        mailService.sendMail(new NotificationEmail("Please activate your account", user.getEmail(),"http://localhost:8080/api/v1/auth/confirm-email/" + token));
    }
}