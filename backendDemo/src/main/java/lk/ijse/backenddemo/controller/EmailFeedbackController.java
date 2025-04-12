package lk.ijse.backenddemo.controller;


import lk.ijse.backenddemo.service.impl.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
@Slf4j
public class EmailFeedbackController {

    @Autowired
    private EmailService emailService;


    @GetMapping("/submit")
    public String submitFeedback(@RequestParam String to ,
                                 @RequestParam String subject,
                                 @RequestParam String text) {
       log.info("sendEmail");


        emailService.sendEmailToVendor(to,subject,text);

        return "Email sent successfully";
    }
}
