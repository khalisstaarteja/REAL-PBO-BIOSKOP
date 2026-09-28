import java.util.ArrayList;
import java.util.Scanner;
public class Bioskop {
    private static Scanner scanner = new Scanner(System.in);
    private int kodeBioskop;
    private String namaBioskop;
    private String lokasi;
    private ArrayList<Studio> listStudio;

    // Constructor public
    public Bioskop(int kodeBioskop, String namaBioskop, String lokasi) {
        this.kodeBioskop = kodeBioskop;
        this.namaBioskop = namaBioskop;
        this.lokasi = lokasi;
        this.listStudio = new ArrayList<Studio>();
    }

    // Constructor user
    public Bioskop(ArrayList<Bioskop> listBioskop) {
        System.out.println("\n--- Input Data Bioskop ---");

        // Mencegah adanya kode yang sama
        boolean kodeSudahAda;
        do {
            System.out.print("Masukkan Kode Bioskop: ");
            this.kodeBioskop = scanner.nextInt();
            scanner.nextLine(); // clear buffer newline

            kodeSudahAda = false;
            // Cek apakah ada yang sama
            for (Bioskop b : listBioskop) {
                if (this.kodeBioskop == b.getKodeBioskop()) {
                    kodeSudahAda = true;
                    break;
                }
            }
            if (kodeSudahAda) {
                 System.out.println("\n[Kode bioskop sudah dipakai! Coba kode lain]\n");
            }
        } while (kodeSudahAda);
       
        System.out.print("Masukkan Nama Bioskop: ");
        this.namaBioskop = scanner.nextLine();

        System.out.print("Masukkan Lokasi: ");
        this.lokasi = scanner.nextLine();

        // Buat array kosong untuk listStudio
        this.listStudio = new ArrayList<Studio>();
    }

    // Untuk memasukkan Studio ke Bioskop
    public void addStudio(Studio studio) {
        this.listStudio.add(studio);
    }

    // Untuk menampilkan object Bioskop
    public void showBioskop() {
        System.out.println("Kode Bioskop : " + kodeBioskop);
        System.out.println("Nama Bioskop : " + namaBioskop);
        System.out.println("Lokasi       : " + lokasi);
    }

    // menampilkan bioskop beserta studionya
    public void detailBioskop() {        
        System.out.println("--- Detail Bioskop ---");
        System.out.println("[" + kodeBioskop + "] " + namaBioskop);
        // Jika masih kosong maka tidak akan muncul
        if (listStudio.isEmpty()) {
            System.out.println("\n[Belum ada studio di bioskop ini!]");
            return;
        }
        // Tampilkan list studio milik bioskop ini
        System.out.println("List Studio:");
        for (Studio s : listStudio) {
            System.out.println("- Studio " + s.getNomorStudio() + " (" + s.getJenisStudio() + ")");
        }
    }

    // Setter
    public void setKodeBioskop(int kodeBioskop) {
        this.kodeBioskop = kodeBioskop;
    }
    public void setNamaBioskop(String namaBioskop) {
        this.namaBioskop = namaBioskop;
    }
    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    // Getter
    public int getKodeBioskop() {
        return kodeBioskop;
    }
    public String getNamaBioskop() {
        return namaBioskop;
    }
    public String getLokasi() {
        return lokasi;
    }
    public ArrayList<Studio> getListStudio() {
        return listStudio;
    }  
}