package ua.karazin.moviesmembershipservice.membership;

import jakarta.validation.constraints.NotBlank;

public record StartMembershipRequest(@NotBlank String profileId, @NotBlank String subscriptionId) {
}
