import java.util.ArrayList;
import java.util.Scanner;

public class Studio {
    private static Scanner scanner = new Scanner(System.in);

    // Attribut private
    private int kodeStudio;
    private int nomorStudio;
    private String jenisStudio;
    private Bioskop bioskop;
    private float harga;
    private ArrayList<JadwalFilm> listJadwal = new ArrayList<>();

    // Constructor public
    public Studio(int kodeStudio, int nomorStudio, String jenisStudio, Bioskop bioskop, float harga) {
        this.kodeStudio = kodeStudio;
        this.nomorStudio = nomorStudio;
        this.jenisStudio = jenisStudio;
        this.bioskop = bioskop;
        this.harga = harga;
    }

    // Constructor user
    public Studio(ArrayList<Studio> listStudio, ArrayList<Bioskop> listBioskop) {
        System.out.println("\n--- Input Data Studio ---");

        boolean kodeSudahAda;
        do {
            System.out.print("Masukkan Kode Studio: ");
            this.kodeStudio= scanner.nextInt();
            scanner.nextLine(); // clear buffer newline
            
            kodeSudahAda = false;
            for (Studio s : listStudio) {
                // jika kode yang dimasukkan ada yang sama dengan data
                if (this.kodeStudio == s.getKodeStudio()) {
                    kodeSudahAda = true;
                    break;
                }
            }
            if (kodeSudahAda) {
                 System.out.println("\n[Kode studio sudah dipakai! Coba kode lain]");
            }
        } while (kodeSudahAda);

        System.out.print("Masukkan Nomor Studio : ");
        this.nomorStudio = scanner.nextInt();
        scanner.nextLine(); // Clear buffer newline

        int pilihan;
        do{
            System.out.println("\n=== Jenis Studio ===");
            System.out.println("1. Regular");
            System.out.println("2. IMAX");
            System.out.println("3. Premiere");
            System.out.print("Masukkan Jenis Studio (1-3): ");
            pilihan = scanner.nextInt();
            switch(pilihan) {
                case 1 :
                    this.jenisStudio = "Regular";
                    this.harga = 50000;
                    break;
                case 2 :
                    this.jenisStudio = "IMAX";
                    this.harga = 70000;
                    break;
                case 3 :
                    this.jenisStudio = "Premiere";
                    this.harga = 90000;
                    break;
                default :
                    System.out.println("Pilihan tidak valid!");
                    break;
            }
        } while (pilihan < 1 || pilihan > 3);
        
        Bioskop bioskopTerpilih = null;
        do {
            int no = 1;
            // Loop tampilkan listBioskop
            for (Bioskop b : listBioskop) {
                System.out.println("\n--- Data ke " + no + " ---");
                b.showBioskop();
                no++;
            }
            System.out.print("Pilih kode bioskop: ");
            int inputKodeBioskop = scanner.nextInt();
            
            // Memastikan kode yang diinput benar
            for (Bioskop b : listBioskop) {
                if(inputKodeBioskop == b.getKodeBioskop()) {
                    bioskopTerpilih = b; // menyimpan objek saat ini
                    break;
                }
            }
            if (bioskopTerpilih == null) {
                System.out.println("Kode bioskop tidak valid!");
                continue;
            }
        } while (bioskopTerpilih == null);
        
        this.bioskop = bioskopTerpilih; // Isi object bioskop yang dipilih  
        this.bioskop.addStudio(this); // Studio mendaftarkan diri ke Bioskop
    }

    // Menampilkan Studio
    void showStudio() {
        System.out.println("Kode Studio     : " + kodeStudio);
        System.out.println("Nomor Studio    : " + nomorStudio);
        System.out.println("Jenis Studio    : " + jenisStudio);
        System.out.println("Bioskop         : " + bioskop.getNamaBioskop());
        System.out.println("Harga           : Rp" + String.format("%.0f", harga));
    }

    public void detailStudio() {        
        System.out.println("[" + kodeStudio + "] Studio " + nomorStudio + " (" + jenisStudio + ")");
        // Jika studio yang dipilih belum ada jadwal
        if (listJadwal.isEmpty()) {
            System.out.println("\n[Belum ada jadwal untuk studio ini!]");
            return;
        }

        System.out.println("\n--- Detail Studio ---");
        System.out.println("List Jadwal:");
        for (JadwalFilm j : listJadwal) {
            System.out.println("- " + j.getTanggal() + " | " + j.getJam() +
                               " | Bioskop " + j.getStudio().getBioskop().getNamaBioskop() +
                               " (Studio " + j.getStudio().getNomorStudio() + " - " + j.getStudio().getJenisStudio() + ")");
        }
    }

    // Memasukkan Jadwal ke studio
    public void addJadwal(JadwalFilm jadwal) {
        this.listJadwal.add(jadwal);
    }

    // Setter
    public void setKodeStudio(int kodeStudio) {
        this.kodeStudio = kodeStudio;
    }

    public void setNomorStudio(int nomorStudio) {
        this.nomorStudio = nomorStudio;
    }

    public void setJenisStudio(String jenisStudio) {
        this.jenisStudio = jenisStudio;
    }

    public void setBioskop(Bioskop bioskop) {
        this.bioskop = bioskop;
    }

    public void setHarga(float harga) {
        this.harga = harga;
    }

    // Getter
    public int getKodeStudio() {
        return kodeStudio;
    }

    public int getNomorStudio() {
        return nomorStudio;
    }

    public String getJenisStudio() {
        return jenisStudio;
    }

    public Bioskop getBioskop() {
        return bioskop;
    }

    public float getHarga() {
        return harga;
    }

    public ArrayList<JadwalFilm> getListJadwal() {
        return listJadwal;
    }
}