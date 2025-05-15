package ua.karazin.moviesmembershipservice.membership;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.karazin.moviesbaseevents.membership.revision1.CreateMembershipCommand1;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class MembershipController {
  private final CommandGateway commandGateway;

  @PostMapping
  public CompletableFuture<String> create(@RequestBody StartMembershipRequest request) {
    var command = new CreateMembershipCommand1(UUID.randomUUID().toString(), request.profileId(), request.subscriptionId());
    return commandGateway.send(command);
  }
}
