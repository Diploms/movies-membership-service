package ua.karazin.moviesmembershipservice.query.membership;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.karazin.moviesbaseevents.membership.revision1.MembershipStatus;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipService {
  private final MembershipRepository membershipRepository;

  public List<Membership> findActive() {
    return membershipRepository.findByStatus(MembershipStatus.ACTIVE.name());
  }
}
