package ua.karazin.moviesmembershipservice.query.subscription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

@RedisHash("subscriptions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
  private String id;
  private Duration duration;
  private BigDecimal price;

  public Instant getExpirationFrom(Instant instant) {
    return instant.plus(duration);
  }
}
