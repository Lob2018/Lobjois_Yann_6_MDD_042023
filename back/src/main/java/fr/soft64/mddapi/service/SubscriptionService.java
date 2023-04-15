package fr.soft64.mddapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.soft64.mddapi.repository.SubscriptionRepository;


@Service
public class SubscriptionService {
	
	@Autowired
	private SubscriptionRepository subscriptionRepository;

}
