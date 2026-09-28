import java.time.LocalDate; // class untuk mengambil tanggal saat ini dari komputer
import java.time.LocalTime; // class untuk mengambil jam saat ini dari komputer
import java.time.format.DateTimeParseException; // class untuk menangani error saat mengubah teks (String) menjadi tanggal/jam
import java.util.Scanner; // library supaya bisa input
import java.util.ArrayList; // library untuk arraylist

// public class
public class JadwalFilm {
    private static Scanner scanner = new Scanner(System.in);
    // private atributs
    private int kodeJadwal;
    private Studio studio;
    private Film film;
    private LocalDate tanggal;
    private LocalTime jam;
    private int kapasitas;
    private int kursiTerisi;

    // Constructor 1: public
    public JadwalFilm(int kodeJadwal, Studio studio, Film film, LocalDate tanggal, LocalTime jam, int kapasitas, int kursiTerisi) {
        this.kodeJadwal = kodeJadwal;
        this.studio = studio;
        this.film = film;
        this.tanggal = tanggal;
        this.jam = jam;
        this.kapasitas = kapasitas;
        this.kursiTerisi = kursiTerisi;
    }

    // Constructor 2: user input
    public JadwalFilm(ArrayList<JadwalFilm> listJadwal, ArrayList<Bioskop> listBioskop, ArrayList<Studio> listStudio, ArrayList<Film> listFilm) {
        System.out.println("\n--- Input Data Jadwal Film ---");

        boolean kodeSudahAda;
        do {
            System.out.print("Masukkan Kode Jadwal: ");
            this.kodeJadwal = scanner.nextInt();
            scanner.nextLine(); // clear buffer newline

            kodeSudahAda = false;
            for (JadwalFilm j : listJadwal) {
                // jika kode yang dimasukkan ada yang sama dengan data
                if (this.kodeJadwal == j.getKodeJadwal()) {
                    kodeSudahAda = true;
                    break;
                }
            }
            // jika kode sudah pernah dipakai akan muncul pesan (tidak bisa dipilih)
            if (kodeSudahAda) {
                 System.out.println("\n[Kode jadwal sudah dipakai! Coba kode lain]\n");
            }
        } while (kodeSudahAda);

        // Menampilkan daftar bioskop
        System.out.println("\n--- Daftar Bioskop ---");
        for (Bioskop b : listBioskop) {
            System.out.println("[" + b.getKodeBioskop() + "] " + b.getNamaBioskop() + " | " + b.getLokasi());
        }
        // Input bioskop
        Bioskop bioskopTerpilih = null;
        int inputKodeBioskop;
        do{
            System.out.print("Pilih Kode Bioskop: ");
            inputKodeBioskop = scanner.nextInt();
            
            // Memastikan input user benar
            for (Bioskop b : listBioskop) {
                if (b.getKodeBioskop() == inputKodeBioskop) {
                    bioskopTerpilih = b;
                    break;
                }
            }
            // Jika input kode tidak ada di data maka input tidak valid
            if (bioskopTerpilih == null) {
                System.out.println("\n[Kode bioskop tidak ditemukan!]\n");
                continue;
            }
        } while (bioskopTerpilih == null);

        // Menampilkan daftar studio yang ada di bioskop tersebut
        System.out.println("\n--- Daftar Studio ---");
        boolean adaStudio = false;
        for (Studio s : listStudio) {
            // filter hanya studio yang di bioskop yang dipilih
            if(s.getBioskop() == bioskopTerpilih){
                adaStudio = true;
                System.out.println("[" + s.getKodeStudio() + "] Studio " + s.getNomorStudio() + " | " + s.getJenisStudio());
            }  
        }
        // jika bioskop yang dipilih belum memiliki studio akan muncul pesan (tidak bisa dipilih)
        if (!adaStudio) {
            System.out.println("\n[Bioskop ini belum punya studio!]");
            return;
        }

        // Input studio
        Studio studioTerpilih = null;
        do{
            System.out.print("Masukkan Kode Studio: ");
            int inputKodeStudio = scanner.nextInt();

             // Memastikan input user benar
            for (Studio s : listStudio) {
                // Admin akan input kode studio, namun kode tersebut harus sesuai dengan bioskop yang dipilih
                if (s.getKodeStudio() == inputKodeStudio && s.getBioskop() == bioskopTerpilih) {
                    studioTerpilih = s;
                    break;
}
            }
            // Jika input kode tidak ada di data maka input tidak valid
            if(studioTerpilih == null) {
                System.out.println("\n[Kode studio tidak ditemukan!]\n");
                continue;
            }
        } while(studioTerpilih == null);
        this.studio = studioTerpilih;
        
        // Menampilkan list semua film yang tersedia (yang sudah diinput)
        System.out.println("\nDaftar Film Tersedia: ");
        for (Film f : listFilm) {
            System.out.println(f.getKodeFilm() + ". " + f.getJudul() + " | " + f.getGenre() + " | " + f.getDurasi());
        }
        // Input film
        Film filmTerpilih = null;
        int inputKodeFilm;
        do{
            System.out.print("Pilih Kode Film: ");
            inputKodeFilm = scanner.nextInt();
            
            // Memastikan input user benar
            for (Film f : listFilm) {
                if (f.getKodeFilm() == inputKodeFilm) {
                    filmTerpilih = f;
                    break;
                }
            }
            // Jika input kode tidak ada di data maka input tidak valid
            if (filmTerpilih == null) {
                System.out.println("Kode film tidak ditemukan!");
                continue;
            }
        } while (filmTerpilih == null);
        scanner.nextLine(); 
        // input film
        this.film = filmTerpilih;
        
        // Jika user salah menginput format tanggal atau jam maka LocalDate.parse akan melempar error
        LocalDate tgl = null;
        while (tgl == null) {
            System.out.print("Masukkan Tanggal (yyyy-mm-dd): ");
            try {
                tgl = LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Format tanggal salah!");
            }
        }
        this.tanggal = tgl;

        LocalTime waktu = null;
        while (waktu == null) {
            System.out.print("Jam (hh:mm) = ");
            try {
                waktu = LocalTime.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Format jam salah! Contoh: 14:30");
            }
        }
        this.jam = waktu;

        System.out.print("Kapasitas = ");
        this.kapasitas = scanner.nextInt();
        scanner.nextLine();

        this.kursiTerisi = 0;
        // Masukkan jadwal ke objek Film
        this.film.addJadwal(this);
    }
    // Menampilkan jadwal film
    void showJadwal() {
        System.out.println("Kode Jadwal : " + kodeJadwal);
        System.out.println("Bioskop     : " + studio.getBioskop().getNamaBioskop());
        System.out.println("Studio      : " + studio.getNomorStudio() + " (" + studio.getJenisStudio() + ")");
        System.out.println("Film        : " + film.getJudul());
        System.out.println("Tanggal     : " + tanggal);
        System.out.println("Jam         : " + jam);
        System.out.println("Kapasitas   : " + kapasitas);
    }
    // kapasitas kursi berkurang
    public int sisaKursi() {
        return kapasitas - kursiTerisi;
    }
    // kursi terisinya nambah
    public void tambahKursiTerisi(int jumlah) {
        this.kursiTerisi += jumlah;
    }

    // setters and getters
    public void setKodeJadwal(int kodeJadwal) {
        this.kodeJadwal = kodeJadwal;
    }
    public void setStudio(Studio studio) {
        this.studio = studio;
    }
    public void setFilm(Film film) {
        this.film = film; 
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public void setJam(LocalTime jam) {
        this.jam = jam;
    }

    public void setKapasitas(int kapasitas) {
        this.kapasitas = kapasitas;
    }

    public void setKursiTerisi(int kursiTerisi) {
        this.kursiTerisi = kursiTerisi;
    }

    public int getKodeJadwal() {
        return kodeJadwal;
    }

    public Studio getStudio() {
        return studio;
    }

    public Film getFilm() {
        return film;
    }
    
    public LocalDate getTanggal() {
        return tanggal;
    }

    public LocalTime getJam() {
        return jam;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public int getKursiTerisi() {
        return kursiTerisi;
    }
}