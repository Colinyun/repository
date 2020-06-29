package com.colin.rabbit.mq.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.colin.rabbit.mq.model.ResponseEntity;
import com.colin.rabbit.mq.model.ResultMessage;
import com.colin.rabbit.mq.service.IProducerService;

@RestController
@RequestMapping("/")
public class IndexController {

	@Autowired
	private IProducerService producerService;

	@GetMapping("/senderMessage")
	public String senderMessage() throws InterruptedException {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("number", "123456123");
		properties.put("send_time", LocalDate.now().toString() + " " + LocalTime.now().toString());
		ResponseEntity<String> responseEntity = new ResponseEntity<String>();
		for (int i = 0; i < 100; i++) {
			responseEntity.add("this this Rabbitmq project1gjhjgj" + i);
		}
		properties.put("totalCount", responseEntity.getList().size());
		producerService.senderMessage(responseEntity, properties);
		return "{message:success,code:200}";
	}

	@GetMapping("/sendDeadLetterMessage")
	public ResultMessage sendDeadLetterMessage() {
		return producerService.sendDeadLetterMessage();
	}

	@GetMapping("/sendDelayMessage")
	public ResultMessage sendDelayMessage(HttpServletRequest request) {
		return producerService.sendDelayMessage(request.getParameter("content"));
	}
}
