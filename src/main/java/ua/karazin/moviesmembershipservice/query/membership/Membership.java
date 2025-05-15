package ua.karazin.moviesmembershipservice.query.membership;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import ua.karazin.moviesbaseevents.membership.revision1.MembershipStatus;
import ua.karazin.moviesmembershipservice.query.subscription.Subscription;

import java.time.Instant;

@RedisHash("memberships")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Membership {
  @Id
  private String membershipId;

  private String profileId;

  private Subscription subscription;

  @Indexed
  private String status;

  private Instant updatedAt;

  private Instant expiration;

  public void activate() {
    status = MembershipStatus.ACTIVE.name();
  }

  public void reject() {
    status = MembershipStatus.REJECTED.name();
  }
}
