package com.dao.Controller.UserController;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dao.Customworks.CustomUtilityClass;
import com.dao.Emails.EmailDTO;
import com.dao.Emails.EmailService;
import com.dao.Entity.ERole;
import com.dao.Entity.Role;
import com.dao.Entity.UserEntity;
import com.dao.Security.services.UserDetailsImpl;
import com.dao.payload.request.LoginRequest;
import com.dao.payload.request.SignupRequest;
import com.dao.payload.response.JwtResponse;
import com.dao.payload.response.MessageResponse;

import antlr.StringUtils;
import freemarker.template.utility.StringUtil;

import com.dao.Security.jwt.JwtUtils;
import com.dao.Repository.UserRepository.UserRepository;
import com.dao.Repository.UserRepository.RoleRepository;






@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth/user")
public class User_AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;
  
  @Autowired
  EmailService emailService;
  
  @Value("${projectUrl}")
  private String projectUrl;
  
  @Value("${projectInfo.name}")
  private String projectInfoName;
  
  @Autowired
  CustomUtilityClass customUtilityClass;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt, 
                         userDetails.getId(), 
                         userDetails.getUsername(), 
                         userDetails.getEmail(), 
                         roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    UserEntity user = new UserEntity();
    user.setEmail(signUpRequest.getEmail());
    user.setPassword(encoder.encode(signUpRequest.getPassword()));

    Set<Role> roles = new HashSet<>();
    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
    LocalDate today = LocalDate.now();
    String dateValue= String.valueOf(today.getYear())+String.valueOf(today.getMonthValue())+String.valueOf(today.getDayOfMonth())
    +String.valueOf(customUtilityClass.genrateRandomNumber());
    
    String tokenforConfirmation=user.getEmail().substring(0, 3)+dateValue;
    user.setTempValue(tokenforConfirmation);
    user.setAccountStatus(1);
    user.setRoles(roles);
    userRepository.save(user);
    
    Map<String, Object> templateData = new HashMap<>();
	templateData.put("email", user.getEmail());
	templateData.put("projectConfirmaton", projectUrl+"api/auth/user/confirmMe/"+user.getEmail()+"/"+user.getTempValue());	
	EmailDTO email = new EmailDTO();
	email.setTo(user.getEmail());
	email.setSubject(projectInfoName+" Account Confirmation");
	email.setEmailData(templateData);
	emailService.sendWelcomeEmail(email);



    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
  
  
  @GetMapping("/confirmMe/{email}/{code}")
  public ResponseEntity<?> confirmMe(@PathVariable("code") String code,@PathVariable("email") String email) {
	  
	  MessageResponse messageResponse=new MessageResponse();
	  if( code !=null && email !=null) {
		  int status=userRepository.countByEmailAndTempValue(email, code);
		  if(status>=1) {
			  int updaateStatus=userRepository.updateEntityAccountStatusAndTempValue(1, "Active", email);
			  if(updaateStatus>=1) {
				  messageResponse.setMessage("Sucessfully Confirmed");
				  messageResponse.setReturncode(200);
				  messageResponse.setReturnNotes("Account:Confirmaton:sucess:1");  
			  }else {
				  messageResponse.setMessage("Fail for Account Confirmation");
				  messageResponse.setReturncode(200);
				  messageResponse.setReturnNotes("Account:Confirmaton:failure-US:1");   
			  }
		  }else {
			  messageResponse.setMessage("Fail for Account Confirmation");
			  messageResponse.setReturncode(200);
			  messageResponse.setReturnNotes("Account:Confirmaton:failure-S:1");  
		  }
	  }else {
		  messageResponse.setMessage("Fail for Account Confirmation");
		  messageResponse.setReturncode(200);
		  messageResponse.setReturnNotes("Account:Confirmaton-O:1");
	  }

    return ResponseEntity.ok(messageResponse);
  }
}
