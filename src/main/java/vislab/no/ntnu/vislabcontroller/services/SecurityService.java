package vislab.no.ntnu.vislabcontroller.services;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
