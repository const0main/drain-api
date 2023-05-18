package drain.api;


import drain.api.provider.ProviderApi;
import drain.api.provider.ShaProvider;
import drain.api.user.User;

import java.util.Scanner;

public class Bootstrapper {

    public static void main(String[] args) throws Exception {
        while(true){
            Scanner scanner = new Scanner(System.in);
            System.out.print("Логин - ");
            String login = scanner.nextLine();
            System.out.print("Пароль - ");
            String password = scanner.nextLine();
            User userData = new User(login, password);
            System.out.println("Sha256 токен - " + userData.getToken());
            if(userData.isAuthorized()) {
                System.out.println("[UID] - " + userData.getUid());
                System.out.println("[ROLE] - " + userData.getRole());
                System.out.println("[EXPIRATION DATE] - " + userData.getExpirationDate());
            }else{
                System.out.println("Такого аккаунта не существует");
            }
        }
    }

}
