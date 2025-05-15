package ua.karazin.moviesmembershipservice.query.membership;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.karazin.moviesbaseevents.membership.revision1.MembershipExpiredEvent1;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class MembershipRenewHandler {
  private final MembershipService service;
  private final CommandGateway commandGateway;

  @Scheduled(fixedRate = 30L, timeUnit = TimeUnit.SECONDS)
  public void renewMemberships() {
    service.findActive()
        .stream()
        .filter(MembershipUtils::isGoingToExpire)
        .forEach(this::renew);
  }

  private void renew(Membership membership) {
    commandGateway.send(new MembershipExpiredEvent1(membership.getProfileId(),
        membership.getSubscription().getId(),
        membership.getExpiration()));
  }
}
