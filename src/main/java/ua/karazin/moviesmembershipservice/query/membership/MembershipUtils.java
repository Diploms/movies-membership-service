package ua.karazin.moviesmembershipservice.query.membership;

import java.time.Instant;

public class MembershipUtils {
  public static boolean isGoingToExpire(Membership membership) {
    return getExpirationControlPoint().isAfter(membership.getExpiration());
  }

  private static Instant getExpirationControlPoint() {
    return Instant.now().minusSeconds(10L);
  }
}
