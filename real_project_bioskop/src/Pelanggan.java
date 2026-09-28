import java.util.ArrayList; // library untuk arraylist

// public class
public class Pelanggan extends User implements Login {
    private String email;
    private ArrayList<Pemesanan> listPemesanan = new ArrayList<>();

    // Constructor public
    public Pelanggan(String nama, String email, String password, String role) {
        super(nama, password, role); 
        this.email = email;
    }
    
    @Override 
    // menampilkan menu pelanggan
    public void showMenu() {
        System.out.println("\n--- Menu Pelanggan ---");
        System.out.println("1. Lihat Jadwal Tayang");
        System.out.println("2. Pesan Tiket");
        System.out.println("3. Riwayat Pemesanan");
        System.out.println("0. Logout");
    }

    @Override
    public void signUp() {
        System.out.println("\n[Pelanggan berhasil mendaftar!, Silahkan login kembali.]");
    }

    @Override
    public void signIn() {
        System.out.println("\n[Pelanggan berhasil masuk!]");
    }

    public void addPemesanan(Pemesanan p) {
        this.listPemesanan.add(p);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Pemesanan> getListPemesanan() {
        return listPemesanan;
    }
}