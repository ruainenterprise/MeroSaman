package com.dao.Emails;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.dao.Entity.UserEntity;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private FreeMarkerConfigurer freemarkerConfig;
	
	@Value("${projectUrl}")
	private String projectUrl;

	public void sendWelcomeEmail(EmailDTO emailDTO) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		try {

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());

			String templateContent = FreeMarkerTemplateUtils
					.processTemplateIntoString(freemarkerConfig.getConfiguration()
							.getTemplate("/email/ConfirmationEmail.ftl"),
							emailDTO.getEmailData());

			 ClassPathResource clr = new ClassPathResource("Freefiles/images/aa.jpg");
			 helper.addInline("MEROSAMAN", clr, "image/jpg");

			helper.setTo(emailDTO.getTo());
			helper.setSubject(emailDTO.getSubject());
			helper.setText(templateContent, true);
			mailSender.send(mimeMessage);

			System.out.println("######## Welcome email sent ######");
		} catch (Exception e) {
			System.out.println("Sending welcome email failed, check log...");
			e.printStackTrace();
		}
	}

}
