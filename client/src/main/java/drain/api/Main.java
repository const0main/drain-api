package drain.api;


import drain.api.controller.UserController;
import drain.api.utilities.ColorUtils;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Логин - ");
        String login = scanner.nextLine();
        System.out.print("Пароль - ");
        String password = scanner.nextLine();
        UserController userData = new UserController(login, password);
        System.out.println(ColorUtils.BLUE + "server data - " + userData.getServerData() + ColorUtils.RESET);
        System.out.println(ColorUtils.CYAN + "token - " + userData.getToken() + ColorUtils.RESET);
        System.out.println(ColorUtils.PURPLE + "hwid - " + userData.getHwid() + ColorUtils.RESET);
        if (userData.isAuthorized()) {
            System.out.println(ColorUtils.GREEN + "Вход выполнен успешно" + ColorUtils.RESET);
            System.out.println("[UID] - " + userData.getUid());
            System.out.println("[ROLE] - " + userData.getRole());
            System.out.println("[EXPIRATION DATE] - " + userData.getExpirationDate());
        } else {
            System.out.println(ColorUtils.RED + "Такого аккаунта не существует" + ColorUtils.RESET);
        }
    }



}
