package fr.soft64.mddapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import fr.soft64.mddapi.model.Subscription;
import fr.soft64.mddapi.repository.SubscriptionRepository;

@Service
public class SubscriptionService {

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	public final Subscription createSubscription(final Subscription subscription) {
		if (subscriptionIsNull(subscription))
			throw new Error();
		return subscriptionRepository.save(subscription);
	}

	public final Optional<Subscription> getSubscriptionWithUserAndSubjectId(Long user_id, Long subject_id) {
		if (user_id == null || subject_id == null) {
			throw new Error();
		}
		Example<Subscription> example = Example.of(new Subscription(user_id, subject_id));
		return subscriptionRepository.findOne(example);
	}

	public final void deleteSubscriptionById(Long id) {
		if (id == null) {
			throw new Error();
		}
		subscriptionRepository.deleteById(id);
	}

	public final boolean subscriptionIsNull(final Subscription subscription) {
		if (subscription == null) {
			return true;
		}
		return false;
	}

}
