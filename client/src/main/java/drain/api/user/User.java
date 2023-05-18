package drain.api.user;

import com.sun.jdi.BooleanType;
import drain.api.Bootstrapper;
import drain.api.provider.ProviderApi;
import drain.api.provider.ShaProvider;

public class User {
    private boolean authStatus = false;
    private String username;
    private String password;
    private int uid;
    private String role;
    private String expirationDate;
    private String userToken;
    private String url;
    public User(String username, String password) throws Exception {
        this.username = username;
        this.password = password;
        this.userToken = ShaProvider.sha256(username + "/" + password);
        this.url = "http://localhost/drain/api.php?token=" + userToken + "&provider=drain";
        this.authStatus = ProviderApi.sendRequest("login", url).equalsIgnoreCase("success") ? true : false;
        this.uid = ProviderApi.sendRequest("uid", url) == null ? -999 : Integer.parseInt(ProviderApi.sendRequest("uid", url));
        this.role = ProviderApi.sendRequest("role", url);
        this.expirationDate = ProviderApi.sendRequest("expire", url);;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAuthorized() {
        return authStatus;
    }

    public String getToken() {
        return userToken;
    }

    public int getUid() {
        return uid;
    }

    public String getRole() {
        return role;
    }

    public String getExpirationDate() {
        return expirationDate;
    }
}
