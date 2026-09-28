public class Admin extends User implements Login {
    
    private String username;
    private boolean loginStatus = false;

    public Admin(String username, String password) {
        super(username, password, "Admin"); 
    }
    
    public boolean isLoginBerhasil() {
        return loginStatus;
    }

    @Override
    public void showMenu() {
        System.out.println("\n--- Menu Admin ---");
        System.out.println("1. Kelola Bioskop");
        System.out.println("2. Kelola Studio");
        System.out.println("3. Kelola Film");
        System.out.println("4. Kelola Jadwal Tayang");
        System.out.println("5. Lihat Pemesanan");
        System.out.println("6. Lihat Data User");
        System.out.println("0. Logout");
    }

    @Override
    public void signUp() {
        System.out.println("Admin berhasil mendaftar.");
    }

    @Override
    public void signIn() {
        System.out.println("\n[Admin berhasil masuk!]");
        this.loginStatus = true; 
    }

    // Setter getter
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
