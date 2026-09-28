// library untuk input user
import java.util.Scanner;

public abstract class User {
    private static Scanner scanner = new Scanner(System.in);
    private static int counter = 1;

    // private attributes
    private int kodeUser;
    private String nama;
    private String password;
    private String role;

    // Constructor 1: public
    public User(String nama, String password, String role) {
        this.nama = nama;
        this.password = password;
        this.role = role;

        if (role.equals("Admin")) {
            this.kodeUser = 1;
        }
        else{
            this.kodeUser = counter;
            counter = counter + 1;
        }
       
    }

    // Constructor 2: user input
    public User() {
        System.out.print("Nama = ");
        this.nama = scanner.nextLine();

        System.out.print("Password = ");
        this.password = scanner.nextLine();

        this.kodeUser = counter;
        counter = counter + 1;
    }

    // Abstract method
    public abstract void showMenu();

    // Membuat kode otomatis
    public static void setCounter(int nilaiBaru) {
        if (nilaiBaru >= counter) {
            counter = nilaiBaru;
        }
    }

    public void showUser() {
        System.out.println("Kode User : " + kodeUser);
        System.out.println("Nama      : " + nama);
        System.out.println("Role      : " + role);
    }

    // setters and getters - public
    public void setKodeUser(int kodeUser) {
        this.kodeUser = kodeUser;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getKodeUser() {
        return kodeUser;
    }

    public String getNama() {
        return nama;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}