package ua.karazin.moviesmembershipservice.query.membership;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface MembershipRepository extends ListCrudRepository<Membership, String> {
  List<Membership> findByStatus(String status);
}
