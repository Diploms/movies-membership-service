package ua.karazin.moviesmembershipservice.membership;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import ua.karazin.moviesbaseevents.membership.revision1.ActivateMembershipCommand1;
import ua.karazin.moviesbaseevents.membership.revision1.CreateMembershipCommand1;
import ua.karazin.moviesbaseevents.membership.revision1.MembershipActivatedEvent1;
import ua.karazin.moviesbaseevents.membership.revision1.MembershipCreatedEvent1;
import ua.karazin.moviesbaseevents.membership.revision1.MembershipExpiredEvent1;
import ua.karazin.moviesbaseevents.membership.revision1.MembershipRejectedEvent1;
import ua.karazin.moviesbaseevents.membership.revision1.MembershipRenewedEvent1;
import ua.karazin.moviesbaseevents.membership.revision1.MembershipStatus;
import ua.karazin.moviesbaseevents.membership.revision1.RejectMembershipCommand1;
import ua.karazin.moviesbaseevents.membership.revision1.RenewMembershipCommand1;

import java.time.Instant;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Getter
@Setter
@NoArgsConstructor
public class Membership {
  @AggregateIdentifier
  private String membershipId;

  private String profileId;

  private String subscriptionId;

  private Instant expiration;

  private MembershipStatus status;

  @CommandHandler
  private Membership(CreateMembershipCommand1 command) {
    apply(new MembershipCreatedEvent1(command.membershipId(), command.profileId(), command.subscriptionId()));
  }

  @CommandHandler
  private void handle(ActivateMembershipCommand1 command) {
    apply(new MembershipActivatedEvent1(command.membershipId(), command.expiration()));
  }

  @CommandHandler
  private void handle(RenewMembershipCommand1 command) {
    apply(new MembershipActivatedEvent1(command.membershipId(), command.expiration()));
  }

  @CommandHandler
  private void handle(RejectMembershipCommand1 command) {
    apply(new MembershipRejectedEvent1(command.membershipId()));
  }

  @EventSourcingHandler
  private void on(MembershipCreatedEvent1 event) {
    this.membershipId = event.membershipId();
    this.profileId = event.profileId();
    this.subscriptionId = event.subscriptionId();
    this.status = MembershipStatus.PENDING;
  }

  @EventSourcingHandler
  private void on(MembershipActivatedEvent1 event) {
    this.status = MembershipStatus.ACTIVE;
    this.expiration = event.expiration();
  }

  @EventSourcingHandler
  private void on(MembershipRenewedEvent1 event) {
    this.status = MembershipStatus.ACTIVE;
    this.expiration = event.expiration();
  }

  @EventSourcingHandler
  private void on(MembershipExpiredEvent1 event) {
    this.status = MembershipStatus.DISABLED;
  }

  @EventSourcingHandler
  private void on(MembershipRejectedEvent1 event) {
    this.status = MembershipStatus.REJECTED;
  }
}
