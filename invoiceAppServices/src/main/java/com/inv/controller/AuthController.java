package com.inv.controller;

	
	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.HttpHeaders;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.HttpStatusCode;
	import org.springframework.http.MediaType;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;

	import com.inv.model.FileModel;
	import com.inv.model.Profile;
	import com.inv.repo.FileRepo;
	import com.inv.service.AuthService;
	import com.inv.utils.APIResponse;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestPart;
	import org.springframework.web.bind.annotation.RestController;
	import org.springframework.web.multipart.MultipartFile;
	import org.springframework.http.MediaType;

	import java.util.List;
	import java.util.Optional;
	import java.io.File;
	import java.io.IOException;
	import java.util.ArrayList;
	import java.util.Comparator;
	import java.util.List;
	import java.util.Optional;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.CrossOrigin;
	import org.springframework.web.bind.annotation.DeleteMapping;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.PutMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RestController;
	import org.springframework.web.multipart.MultipartFile;
	import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



	@CrossOrigin(origins = "http://localhost:8100")
	@RestController
	@RequestMapping("/auth")   

	public class AuthController {
	
	    
		private static final Logger log = LoggerFactory.getLogger(AuthController.class);
	    
	    @Autowired
	    private AuthService service;
	    
	    @Autowired
	    private FileRepo fileRepo;
	    
	    
	    @GetMapping(value="/welcome")
	    public String hello() {
	    	return "hello world !";
	    }
	        
	    
	    
	    @PostMapping("/register")
	    public Profile save(@RequestBody Profile object) {
	    	log.info("Saving new user profile");
	    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    	
	    	try {
	    	// Check if email Id already exists
	    	if(service.existingUser(object.email))
	    	{
	    		object.apiResponse.errorCode = (long)601;
	    		object.apiResponse.errorString ="Existing user";
				object.apiResponse.responseBool = false;
	    		return object;
	    	}
	    	else {
	    			
	    			object.password = passwordEncoder.encode(object.password);
	        		object = service.save(object);
	        		object.password="";
	        		object.apiResponse.responseBool = true;
	        	}
	        	}
	        		catch(Exception e)
	        		{
	        			object.apiResponse.errorCode = (long) 602;
	        			object.apiResponse.errorString ="Exception While Creating Object";
	        			object.apiResponse.responseBool = false;
	        		}
	        		return object;
	        	}
	    
	    
	  
		
		
		@PostMapping(value = "/validate")
		public Profile validateUser(@RequestBody Profile profile) {

			Profile	profileFromDB = new Profile();
			Optional<Profile> option = service.findByEmail(profile.email);
				
			if (option.isPresent()) {
				profileFromDB = option.get();
				
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
			if(passwordEncoder.matches(profile.password, profileFromDB.password))
			{
				profileFromDB.password ="";	
				profileFromDB.apiResponse.responseBool = true;
			}
			else
			{
				profileFromDB.apiResponse.errorCode = (long) 603;
				profileFromDB.apiResponse.responseBool = false;
				profileFromDB.apiResponse.errorString ="Wrong credentials";
			}
			}
			else {
				profileFromDB.apiResponse.errorCode = (long) 604;
				profileFromDB.apiResponse.responseBool = false;
				profileFromDB.apiResponse.errorString ="User not present";
				}
			return profileFromDB;
		}
		
		
		@PostMapping(value = "/profilePhoto", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
		public ResponseEntity<APIResponse> uploadProfile(@RequestPart(name = "imageFile") MultipartFile file,@RequestPart(name = "profileId") String profileId ) throws IOException {
			
		APIResponse apiResponse = new APIResponse();
		FileModel fileModel = new FileModel();

			
			if(Long.parseLong(profileId) !=0)
			{
				try {
					
				    fileModel.fileContent = file.getBytes();;
				    
				    fileModel.fileName = "profileId"+profileId;
				    fileModel.profileId = Long.parseLong(profileId);
				    fileRepo.save(fileModel);
					
					apiResponse.responseBool = true;
					
				}
				catch(Exception e)
				{
					apiResponse.responseBool = false;
					apiResponse.errorCode = (long) 601;
					apiResponse.errorString = e.getLocalizedMessage();
				}
				return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.OK);
			}
			apiResponse.responseBool = false;
			apiResponse.errorCode = (long) 605;
			apiResponse.errorString = "Data Issue";
			return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.NOT_IMPLEMENTED);
			
	}
		
		
		@GetMapping(value="/profilePhoto/{id}", produces=MediaType.IMAGE_PNG_VALUE)
	    public ResponseEntity<byte[]> getProfilePhoto(@PathVariable Long id) {
	        
	    	Optional<FileModel> fileEntityOptional = fileRepo.findByProfileId(id);


	        FileModel fileEntity = fileEntityOptional.get();
	        
	        return ResponseEntity.ok()
	                .contentType(MediaType.IMAGE_PNG) // Adjust content type if needed
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.fileName + "\"")
	                .body(fileEntity.fileContent);
	        		
		}
	
	    @PostMapping("/changePassword")
	    public Profile changePassword(@RequestBody Profile profile) {
	    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    	Profile	profileFromDB = new Profile();
			Optional<Profile> option = service.findByEmail(profile.email);
				
			if (option.isPresent()) {
			
				profileFromDB = option.get();
				
				
				
				if(passwordEncoder.matches(profileFromDB.password, profile.password))
				{
					
					profileFromDB.password = passwordEncoder.encode(profile.changedNewPassword);
					profileFromDB = service.save(profileFromDB);
					profile.password="";
					profile.changedNewPassword="";
					profile.apiResponse.responseBool = true;
				}
				else {
					profile.apiResponse.errorCode = (long) 606;
					profile.apiResponse.responseBool = false;
					profile.apiResponse.errorString ="Old password provided is wrong";
				}
			}
			else {
				profile.apiResponse.errorCode = (long) 604;
				profile.apiResponse.responseBool = false;
				profile.apiResponse.errorString ="User not present";
			}
			return profile;
	
	}
	}	
		











	
