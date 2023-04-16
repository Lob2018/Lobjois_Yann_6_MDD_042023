package fr.soft64.mddapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.soft64.mddapi.model.Subscription;
import fr.soft64.mddapi.model.Users;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
