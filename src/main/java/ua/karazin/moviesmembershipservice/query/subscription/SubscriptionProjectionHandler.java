package ua.karazin.moviesmembershipservice.query.subscription;

import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import ua.karazin.moviesbaseevents.subscription.revision1.SubscriptionCreatedEvent1;
import ua.karazin.moviesbaseevents.subscription.revision1.SubscriptionUpdatedEvent1;

@Component
@RequiredArgsConstructor
public class SubscriptionProjectionHandler {
  private final SubscriptionRepository repository;

  @EventHandler
  private void on(SubscriptionCreatedEvent1 event) {
    repository.save(Subscription.builder()
        .id(event.id())
        .duration(event.duration())
        .price(event.price())
        .build());
  }

  @EventHandler
  private void on(SubscriptionUpdatedEvent1 event) {
    var item = repository.findById(event.id())
        .map(i -> {
          i.setDuration(event.duration());
          i.setPrice(event.price());
          return i;
        })
        .orElseThrow();

    repository.save(item);
  }
}
