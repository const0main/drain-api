package drain.api.controller;

import drain.api.provider.ProviderApi;
import drain.api.provider.ShaProvider;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Scanner;

public class UserController {
    private boolean authStatus = false;
    private String username;
    private String password;
    private int uid;
    private String role;
    private String expirationDate;
    private String userToken;
    private String hwid;
    private String url;

    public UserController(String username, String password) throws Exception {
        this.username = username;
        this.password = password;
        this.userToken = ShaProvider.sha256(username + "/" + password);
        this.hwid = getHWID();
        this.url = "http://localhost/drain/api.php?token=" + userToken + "&provider=drain&hwid=" + hwid;
        this.authStatus = ProviderApi.sendRequest("login", url).equalsIgnoreCase("success") ? true : false;
        this.uid = ProviderApi.sendRequest("uid", url) == null ? -999 : Integer.parseInt(ProviderApi.sendRequest("uid", url));
        this.role = ProviderApi.sendRequest("role", url);
        this.expirationDate = ProviderApi.sendRequest("expire", url);
    }
    public static String getHWID() {
        try{
            String toEncrypt =  System.getenv("COMPUTERNAME") + System.getProperty("user.name") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_LEVEL");
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(toEncrypt.getBytes());
            StringBuffer hexString = new StringBuffer();

            byte byteData[] = md.digest();

            for (byte aByteData : byteData) {
                String hex = Integer.toHexString(0xff & aByteData);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "hwid error";
        }
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

    public String getHwid() {
        return hwid;
    }
    public String getServerData() throws Exception {
        URL urlTest = new URL(url);
        return new Scanner(urlTest.openStream()).nextLine();
    }
}
