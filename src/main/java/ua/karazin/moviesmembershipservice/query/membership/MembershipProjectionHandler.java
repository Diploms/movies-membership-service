package ua.karazin.moviesmembershipservice.query.membership;

import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import ua.karazin.moviesbaseevents.membership.revision1.MembershipActivatedEvent1;
import ua.karazin.moviesbaseevents.membership.revision1.MembershipCreatedEvent1;
import ua.karazin.moviesbaseevents.membership.revision1.MembershipRejectedEvent1;
import ua.karazin.moviesbaseevents.membership.revision1.MembershipRenewedEvent1;
import ua.karazin.moviesbaseevents.membership.revision1.MembershipStatus;
import ua.karazin.moviesmembershipservice.query.subscription.SubscriptionRepository;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class MembershipProjectionHandler {
  private final MembershipRepository membershipRepository;
  private final SubscriptionRepository subscriptionRepository;

  @EventHandler
  public void on(MembershipCreatedEvent1 event) {
    var subscription = subscriptionRepository.findById(event.subscriptionId()).orElseThrow();

    membershipRepository.save(Membership.builder()
        .membershipId(event.membershipId())
        .profileId(event.profileId())
        .subscription(subscription)
        .status(MembershipStatus.PENDING.name())
        .updatedAt(Instant.now())
        .build());
  }

  @EventHandler
  public void on(MembershipActivatedEvent1 event) {
    var membership = membershipRepository.findById(event.membershipId()).orElseThrow();

    membership.activate();
    membership.setExpiration(event.expiration());

    membershipRepository.save(membership);
  }

  @EventHandler
  public void on(MembershipRenewedEvent1 event) {
    var membership = membershipRepository.findById(event.membershipId()).orElseThrow();

    membership.activate();
    membership.setExpiration(event.expiration());

    membershipRepository.save(membership);
  }

  @EventHandler
  public void on(MembershipRejectedEvent1 event) {
    var membership = membershipRepository.findById(event.membershipId()).orElseThrow();

    membership.reject();

    membershipRepository.save(membership);
  }
}
