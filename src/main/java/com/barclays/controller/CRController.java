package com.barclays.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.barclays.model.ChangeRequest;
import com.barclays.repository.ChangeRequestRepository;

@RestController
public class CRController {
	
	@Autowired
	private ChangeRequestRepository changeRequestRepository;

	@GetMapping("approve/{changeNo}")
	public void approveCR(@PathVariable String changeNo) {
		
		ChangeRequest changeRequest = changeRequestRepository.findByChangeRecord(changeNo);
		
		System.out.println(changeRequest);
		
		String url = "https://dev82962.service-now.com/api/now/table/sysapproval_approver/" + changeRequest.getApprovalSysId();

		// create an instance of RestTemplate
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setBasicAuth("admin", "ByteOptimizer@123");
		
		// request body parameters
		Map<String, Object> map = new HashMap<>();
		map.put("state", "approved");
		map.put("approver", "c97024fe53271300e321ddeeff7b1233");

		// build the request
		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
		
		ResponseEntity<Object> response=restTemplate.exchange(url, HttpMethod.PUT, 
				entity, Object.class);
		// check response
		
		System.out.println(response.getStatusCode());

	}
	
	@GetMapping("reject/{changeNo}")
	public void rejectCR(@PathVariable String changeNo) {
		ChangeRequest changeRequest = changeRequestRepository.findByChangeRecord(changeNo);
		
		System.out.println(changeRequest);
		
		String url = "https://dev82962.service-now.com/api/now/table/sysapproval_approver/" + changeRequest.getApprovalSysId();

		// create an instance of RestTemplate
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setBasicAuth("admin", "ByteOptimizer@123");
		
		// request body parameters
		Map<String, Object> map = new HashMap<>();
		map.put("state", "rejected");
		map.put("approver", "c97024fe53271300e321ddeeff7b1233");

		// build the request
		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
		
		ResponseEntity<Object> response=restTemplate.exchange(url, HttpMethod.PUT, 
				entity, Object.class);
		// check response
		
		System.out.println(response.getStatusCode()); 
		
		changeRequest.setFlag(4);
		changeRequestRepository.save(changeRequest);
	}
}
