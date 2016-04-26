package com.krisnela.test.mvp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import com.krisnela.test.mvp.*;
import com.krisnela.test.mvp.dto.Comments;
import com.krisnela.test.mvp.dto.Employee;
import com.krisnela.test.mvp.dto.Greeting;
import com.krisnela.test.mvp.dto.HelloMessage;
import com.krisnela.test.mvp.dto.Message;
import com.krisnela.test.mvp.dto.MessageThread;
import com.krisnela.test.mvp.dto.User;
import com.krisnela.test.mvp.repository.CommentsRepository;
import com.krisnela.test.mvp.repository.EmployeeDaoImpl;
import com.krisnela.test.mvp.repository.EmployeeRepository;
import com.krisnela.test.mvp.repository.GreetingRepository;
import com.rabbitmq.client.AMQP.Access.Request;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.apache.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.MediaType;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;


@RestController
public class GreetingController {
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	@Autowired
	private GreetingRepository repository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private CommentsRepository commnetsRepository;
	@Autowired
	private EmployeeDaoImpl employeeDaoImpl;
	@Autowired
	private MongoOperations mongoOperations;
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	// @SendTo("/topic/greetings")
	@MessageMapping("/hello/{id}")
	// @SendTo("/topic/greetings")
	public void greeting(HelloMessage message, @DestinationVariable("id") String id, Message uMessage)
			throws Exception {
		
		
		Query query = new Query();
		query.addCriteria(new Criteria().orOperator(
				new Criteria().andOperator(Criteria.where("sender.objectId").is(message.getSenderId()),
						Criteria.where("receiver.objectId").is(id)),
				new Criteria().andOperator(Criteria.where("sender.objectId").is(id),
						Criteria.where("receiver.objectId").is(message.getSenderId()))
				
				));
		MessageThread messageThread= mongoOperations.findOne(query, MessageThread.class);
		if(messageThread==null){
			messageThread=new MessageThread();
			messageThread.setSender(new User(message.getSenderId()));
			messageThread.setReceiver(new User(id));
			mongoOperations.save(messageThread,"messagethread");
		}
		uMessage.setMessageThread(messageThread);
		uMessage.setMessage(message.getName());
		mongoOperations.save(uMessage, "message");
		System.out.println(message.getSenderId());
		simpMessagingTemplate.convertAndSend("/topic/greetings/" + id,
				new Greeting(message.getName(), message.getSenderId()));
		// return new Greeting(message.getName());
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping("/greeting")
	public @ResponseBody List<Greeting> greeting(@RequestParam(required = false, defaultValue = "World") String name) {
		System.out.println("==== in greeting ====");
		// Greeting greeting=new Greeting();
		// greeting.setName("Himanshu Singh");
		// Greeting greeting1=new Greeting();
		// greeting1.setName("Himanshu Singh");
		// Greeting greeting2=new Greeting();
		// greeting2.setName("Himanshu Singh");
		// repository.save(greeting);
		// repository.save(greeting2);
		// repository.save(greeting1);
		return repository.findAll();

	}

	@RequestMapping(value = "/Employee/", method = RequestMethod.POST)
	@Transactional
	public @ResponseBody String saveEmployee(@RequestBody Employee employee, UriComponentsBuilder ucBuilder) {
		User user = new User();
		user.setEmail("himanshu@gmail.com");
		user.setPassword("1");
		mongoOperations.save(user, "user");

		return "saved";
	}

	@RequestMapping(value = "/login/", method = RequestMethod.POST)
	public ResponseEntity<LoginResponse> login(@RequestBody User user) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("username", "Himanshu");
		map.put("emailid", "himanshusingh006@gmail.com");
		map.put("objectId", "a,kfsefjw dnierofgvefvopefkgveprokg");
		list.add(map);
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(user.getEmail()).and("password").is(user.getPassword()));
		User currentUser = mongoOperations.findOne(query, User.class);
		if (currentUser == null) {
			return new ResponseEntity<LoginResponse>(new LoginResponse("Invalid user"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<LoginResponse>(
				new LoginResponse(Jwts.builder().setSubject(user.getEmail()).claim("roles", list)

						.signWith(SignatureAlgorithm.HS256, "secretkey").compact(), currentUser.getName(),
						currentUser.getEmail(), currentUser.getObjectId()),
				HttpStatus.OK);
		// LoginResponse(Jwts.builder().setSubject(user.getEmail()).claim("roles",
		// list)
		//
		// .signWith(SignatureAlgorithm.HS256, "secretkey").compact());
	}

	@SuppressWarnings("unused")
	private static class LoginResponse {
		public String token, name, email, error, objectId;

		public LoginResponse(final String token, final String name, final String email, final String objectId) {
			this.token = token;
			this.name = name;
			this.email = email;
			this.objectId = objectId;
		}

		public LoginResponse(final String error) {
			this.error = error;
		}

	}

	@RequestMapping(value = "/Employee", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Employee> getAllEmployee() {
		Query query = new Query();
		query.limit(30);
		query.skip(0);
		// query.addCriteria(Criteria.where("name").is("Himan Dev"));
		return employeeDaoImpl.find(query);
	}

	@RequestMapping(value = "/Messages", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<MessageThread> getAllMesages() {
//		Query query = new Query();
//		query.addCriteria(new Criteria().orOperator(
//				new Criteria().andOperator(),
//				new Criteria().andOperator()
//				
//				));
		Aggregation agg = newAggregation(
				MessageThread.class,
				group("hosting").count().as("total"),
				project("total").and("hosting").previousOperation(),
				sort(Sort.Direction.DESC, "total"));
					
		return mongoOperations.findAll(MessageThread.class,"messagethread");
	}

	@RequestMapping(value = "/Users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<User> getAllUsers() {
//		Query query = new Query();
//		query.limit(30);
//		query.skip(0);
		// query.addCriteria(Criteria.where("name").is("Himan Dev"));
		List<User> userList=new ArrayList<>();
		User u1=new User("111111a");
		u1.setName("Himan");
		User u2=new User("111111a");
		u2.setName("Dev");
		User u3=new User("111111a");
		u3.setName("Chandan");
		userList.add(u1);
		userList.add(u2);
		userList.add(u3);
		return userList;
	}

	@RequestMapping(value = "/Comments/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Comments> getAllComments() {
		return commnetsRepository.findAll();
	}

	private static final String COLLECTION = "Employee";

	@RequestMapping(value = "/Employee/{objectId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> getEmployee(@PathVariable("objectId") String objectId) {
		Employee employee = new Employee();
		employee = employeeRepository.findByObjectId(objectId);
		employee.setObjectId(objectId);
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}

	@RequestMapping(value = "/Employee/{objectId}", method = RequestMethod.PUT)
	@Transactional
	public ResponseEntity<String> updateUser(@PathVariable String objectId, @RequestBody Employee employee) {
		Employee currentEmployee = employeeRepository.findByObjectId(objectId);
		currentEmployee.setEmail(employee.getEmail());
		currentEmployee.setName(employee.getName());
		currentEmployee.setNumber(employee.getNumber());
		employeeRepository.save(currentEmployee);
		Employee newEmployee = new Employee();
		newEmployee.setObjectId(objectId);
		Comments comments = new Comments();
		comments.setCommentText(employee.getAddress());
		comments.setEmployee(newEmployee);
		employeeDaoImpl.save(comments);
		return new ResponseEntity<>("Done", HttpStatus.OK);
	}

	@RequestMapping("/edit")
	public String testpage() {
		return "editemployee.html";
	}

}
