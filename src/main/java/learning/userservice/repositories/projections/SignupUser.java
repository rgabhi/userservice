package learning.userservice.repositories.projections;

public interface SignupUser {
    Long getId();
    String getFirstName();
    String getLastName();
    String getEmail();
    String getUsername();
}