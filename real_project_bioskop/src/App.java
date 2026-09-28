import java.io.File; //library untuk file
import java.util.ArrayList; //library untuk arraylist
import java.util.Scanner; //library supaya bisa input

public class App {
    // arraylist global
    static ArrayList<Bioskop> listBioskop = new ArrayList<>();
    static ArrayList<Studio> listStudio = new ArrayList<>();
    static ArrayList<Film> listFilm = new ArrayList<>();
    static ArrayList<JadwalFilm> listJadwal = new ArrayList<>();
    static ArrayList<Pelanggan> listPelanggan = new ArrayList<>();
    static ArrayList<Pemesanan> listPemesanan = new ArrayList<>();

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        DataFile.readBioskop(listBioskop);
        DataFile.readStudio(listStudio, listBioskop);
        DataFile.readFilm(listFilm);
        DataFile.readJadwal(listJadwal, listStudio, listFilm);
        DataFile.readPelanggan(listPelanggan);
        DataFile.readPemesanan(listPemesanan, listPelanggan, listJadwal);

        // Pembuatan kode user dan nomor pemesanan otomatis
        int counterUserTerbesar = 1; // Kode user admin itu otomatis 1, maka pelanggan akan dimulai dari 2
        for (Pelanggan p : listPelanggan) {
            counterUserTerbesar = Math.max(counterUserTerbesar, p.getKodeUser() + 1);
        }
        User.setCounter(counterUserTerbesar); // Counter terbesar (kode terakhir) akan +1

        int counterPemesananTerbesar = 0; // Kode Pemesanan mulai dari 1 dst
        for (Pemesanan p : listPemesanan) {
            counterPemesananTerbesar = Math.max(counterPemesananTerbesar, p.getKodePemesanan());
        }
        Pemesanan.setCounter(counterPemesananTerbesar); // Counter terbesar (kode terakhir) akan +1

        // Menu awal sebelum login
        int menuAwal;
        do {
            System.out.println("\n--- APLIKASI TIKET BIOSKOP ---");
            System.out.println("1. Login Admin");
            System.out.println("2. Daftar Pelanggan (Sign Up)");
            System.out.println("3. Login Pelanggan (Sign In)");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");

            menuAwal = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (menuAwal) {
                case 1:
                    loginAdmin();
                    break;
                case 2:
                    daftarPelanggan();
                    break;
                case 3:
                    loginPelanggan();
                    break;
                case 0:
                    System.out.println("\nTerima kasih sudah menggunakan aplikasi kami!");
                    System.out.println("Menutup aplikasi...");
                    break;
                default:
                    System.out.println("[\nPilihan tidak valid!]");
            }
        } while (menuAwal != 0);

        scanner.close();
    }
    
    //pelanggan
    public static void daftarPelanggan() {
        System.out.println("\n--- Daftar Pelanggan ---");
        System.out.print("Nama: ");
        String nama = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();

        for (Pelanggan p : listPelanggan) { // buat cek email ada yang sama apa engga
            if (p.getEmail().equalsIgnoreCase(email)) {
                System.out.println("\nPendaftaran gagal! Email '" + email + "' sudah terdaftar.");
                System.out.println("Silakan gunakan email lain atau langsung Login (Sign In).");
                return; 
            }
        }

        System.out.print("Password: ");
        String password = scanner.nextLine();

        String role = "Pelanggan";

        //kalo belum ada buat objek n masukin arraylist 
        Pelanggan pelangganBaru = new Pelanggan(nama, email, password, role);
        pelangganBaru.signUp();
        listPelanggan.add(pelangganBaru);

        DataFile.writePelanggan(listPelanggan);
        DataFile.writeUser(listPelanggan);
    }

    public static void loginPelanggan() {
        System.out.println("\n--- Login Pelanggan ---");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        for (Pelanggan p : listPelanggan) {
            // Jika email dan password yang diinput sama dengan data yang ada di Pelanggan, maka bisa login
            if (p.getEmail().equalsIgnoreCase(email) && p.getPassword().equals(password)) {
                p.signIn(); // Menampilkan pesan berhasil (Interface)
                System.out.println("Login Berhasil! Selamat datang, " + p.getNama());
                
                menuPelanggan(p); // Panggil menu abstract
                return; 
            }
        }
        System.out.println("\n[Email atau Password salah!]\n");
    }

    public static void menuPelanggan(Pelanggan p) {
        int pilihan;
        do {
            p.showMenu(); // Menampilkan menu dari abstract method (Pelanggan)
            System.out.print("Pilih menu: ");
            pilihan = scanner.nextInt();
            scanner.nextLine();
            
            System.out.println();

            switch (pilihan) {
                case 1:
                    showJadwal(); 
                    break;
                case 2:
                    pesanTiket(p);
                    break;
                case 3:
                    riwayatPemesanan(p);
                    break;
                case 0:
                    System.out.println("Logout berhasil...");
                    break;
                default:
                    System.out.println("[\nPilihan tidak valid!]");
            }
        } while (pilihan != 0);
    }

    //admin
    public static void loginAdmin() {
        System.out.println("\n--- Login Admin ---");
        System.out.print("Username: ");
        String usernameAdmin = scanner.nextLine();
        System.out.print("Password: ");
        String passwordAdmin = scanner.nextLine();

        // bikin variabel buat namopung data dari file
        String userAsli = "";
        String passAsli = "";

        try { 
            // Buka file admin.txt
            File fileAdmin = new File("src/admin.txt");
            Scanner scAdmin = new Scanner(fileAdmin); // Ambil data dari admin.txt
            // Baca seluruh text dalam 1 baris, trim untuk hapus spasi kosong diawal atau akhir text
            if (scAdmin.hasNextLine()) userAsli = scAdmin.nextLine().trim(); 
            if (scAdmin.hasNextLine()) passAsli = scAdmin.nextLine().trim();
            scAdmin.close(); // tutup file
        } catch (Exception e) {
            System.out.println("File admin.txt tidak ditemukan!");
        }

        // Jika inputan username dan password sesuai dengan yang ada di file admin.txt maka berhasil login
        if (usernameAdmin.equals(userAsli) && passwordAdmin.equals(passAsli)) {
            // Bikin object Admin
            Admin admin = new Admin(usernameAdmin, passwordAdmin);
            admin.signIn(); // Tampilin pesan berhasil (Interface)
            menuAdmin(admin); // Menu admin (Abstract)
        }
        else {
            System.out.println("\n[Username atau Password salah!]");
        }
    }

    public static void menuAdmin(Admin admin) {
        int pilihan;
        do {
            admin.showMenu(); // Menu abstract untuk admin
    
            System.out.print("Pilih opsi: ");
            pilihan = scanner.nextInt();
            scanner.nextLine(); // clear buffer
    
            switch (pilihan) {
                case 1:
                    menuBioskop(); 
                    break;
                case 2:
                    menuStudio(); 
                    break;
                case 3:
                    menuFilm(); 
                    break;
                case 4:
                    menuJadwal(); 
                    break;
                case 5:
                    showPemesanan(); 
                    break;
                case 6: 
                    showUser();
                    break;
                case 0:
                    System.out.println("Logout berhasil..."); break;
    
                default:
                    System.out.println("\n[Pilihan tidak valid!]\n");
            }
        } while (pilihan != 0);
    }

    //bioskop
    static void menuBioskop() {
        int pilihan;
        do {
            System.out.println("\n--- Bioskop ---");
            System.out.println("1. Tambah Bioskop");
            System.out.println("2. Lihat Semua Bioskop");
            System.out.println("3. Lihat Detail Bioskop");
            System.out.println("0. Kembali");
            System.out.print("Pilih opsi: ");
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1: addBioskop(); break;
                case 2: showBioskop(); break;
                case 3: lihatDetailBioskop(); break;
                case 0: break;
                default: System.out.println("\n[Pilihan tidak valid!]\n");
            }
        } while (pilihan != 0);
    }

    static void addBioskop() {
        // Bikin object Bioskop
        listBioskop.add(new Bioskop(listBioskop));
        // Write ke file
        DataFile.writeBioskop(listBioskop);
    }

    static void showBioskop() {
        if (listBioskop.isEmpty()) {
            System.out.println("\n[Belum ada data bioskop!]\n");
            return;
        }
        int no = 1;
        // Loop tampilkan listBioskop
        for (Bioskop b : listBioskop) {
            System.out.println("\n--- Data ke " + no + " ---");
            b.showBioskop();
            no++;
        }
    }

    static void lihatDetailBioskop() {
        // Jika data kosong maka akan return
        if (listBioskop.isEmpty()) {
            System.out.println("\n[Belum ada data bioskop!]\n");
            return;
        }

        showBioskop(); 
        System.out.print("\nMasukkan Kode Bioskop untuk melihat detail: ");
        int kode = scanner.nextInt();
        scanner.nextLine();

        for (Bioskop b : listBioskop) {
            if (b.getKodeBioskop() == kode) {
                // Panggil lewat object Bioskop agar bisa menampilkan studio yang tepat
                // Jalankan method ini untuk object b
                b.detailBioskop();
                return;
            }
        }
        System.out.println("\n[Kode Bioskop tidak ditemukan!]");
    }

    //studio
    static void menuStudio() {
        // Menu studio
        int pilihan;
        do {
            System.out.println("\n--- Studio ---");
            System.out.println("1. Tambah Studio");
            System.out.println("2. Lihat Semua Studio");
            System.out.println("3. Lihat Detail Studio");
            System.out.println("0. Kembali");
            System.out.print("Pilih opsi: ");
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1: addStudio(); break;
                case 2: showStudio(); break;
                case 3 : lihatDetailStudio(); break;
                case 0: break;
                default: System.out.println("\n[Pilihan tidak valid!]\n");
            }
        } while (pilihan != 0);
    }

    static void addStudio() {
        // Tambah Studio
        if (listBioskop.isEmpty()) {
            System.out.println("\n[Belum ada bioskop yang terdaftar! Silahkan daftarkan terlebih dahulu.]\n");
            return;
        }
        listStudio.add(new Studio(listStudio, listBioskop));
        System.out.println("\n[Data berhasil ditambahkan!]\n");

        DataFile.writeStudio(listStudio);
    }

    static void showStudio() {
        // Menampilkan studio
        if (listStudio.isEmpty()) {
            System.out.println("\n[Belum ada data studio!]\n");
            return;
        }
        int no = 1;
        for (Studio s : listStudio) {
            System.out.println("\n--- Data ke " + no + " ---");
            s.showStudio();
            System.out.println();
            no++;
        }
    }

    static void lihatDetailStudio() {
        // Lihat studio serta jadwal
        if (listStudio.isEmpty()) {
            System.out.println("\n[Belum ada data studio!]\n");
            return;
        }
        showStudio();
        System.out.print("\nMasukkan Kode Studio untuk melihat detail: ");
        int kode = scanner.nextInt();
        scanner.nextLine();

        for (Studio s : listStudio) {
            if (s.getKodeStudio() == kode) {
                s.detailStudio();
                return;
            }
        }
        System.out.println("\n[Kode Studio tidak ditemukan!]");
    }

    //jadwal
    static void menuJadwal() {
        // Menu jadwal
        int pilihan;
        do {
            System.out.println("\n--- Jadwal ---");
            System.out.println("1. Tambah Jadwal");
            System.out.println("2. Lihat Semua Jadwal");
            System.out.println("0. Kembali");
            System.out.print("Pilih opsi: ");
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1: addJadwal(); break;
                case 2: showJadwal(); break;
                case 0: break;
                default: System.out.println("\n[Pilihan tidak valid!]\n");
            }
        } while (pilihan != 0);
    }

    static void addJadwal() {
        // Tambah jadwal
        if (listBioskop.isEmpty() || listStudio.isEmpty() || listFilm.isEmpty()) {
            System.out.println("\n[Data bioskop, studio, dan film harus ada dulu!]\n");
            return;
        }
        else{
            // Cek apakah proses input tidak berhasil karena bioskop belum ada studio
            JadwalFilm jadwalBaru = new JadwalFilm(listJadwal, listBioskop, listStudio, listFilm);
            if (jadwalBaru.getStudio() == null) {
                return;
            }
            // Masukkan ke arraylist
            listJadwal.add(jadwalBaru);
            System.out.println("\n[Data berhasil ditambahkan!]\n");
            DataFile.writeJadwal(listJadwal);
        }
    }

    static void showJadwal() {
        // Menampilkan jadwal
        if (listJadwal.isEmpty()) {
            System.out.println("\n[Belum ada data jadwal!]\n");
            return;
        }
        int no = 1;
        for (JadwalFilm j : listJadwal) {
            System.out.println("\n--- Data ke " + no + " ---");
            j.showJadwal();
            System.out.println();
            no++;
        }
    }

    //film
    static void menuFilm() {
        // Menu film
        int pilihan;
        do {
            System.out.println("\n--- Film ---");
            System.out.println("1. Tambah Film");
            System.out.println("2. Lihat Semua Film");
            System.out.println("3. Lihat Detail Film (Beserta Jadwalnya)");
            System.out.println("0. Kembali");
            System.out.print("Pilih opsi: ");
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1: addFilm(); break;
                case 2: showFilm(); break;
                case 3: lihatDetailFilm(); break;
                case 0: break;
                default: System.out.println("\n[Pilihan tidak valid!]\n");
            }
        } while (pilihan != 0);
    }

    static void lihatDetailFilm() {
        // Menampilkan film beserta jadwal
        if (listFilm.isEmpty()) {
            System.out.println("\n[Belum ada data film!]\n");
            return;
        }
        showFilm();
        System.out.print("\nMasukkan Kode Film untuk melihat detail: ");
        int kode = scanner.nextInt();
        scanner.nextLine();

        for (Film f : listFilm) {
            if (f.getKodeFilm() == kode) {
                f.detailFilm();
                return;
            }
        }
        System.out.println("\n[Kode Film tidak ditemukan!]");
    }

    static void addFilm() {
        // Tambah film
        listFilm.add(new Film(listFilm));
        DataFile.writeFilm(listFilm);
    }
    
    static void showFilm() {
        // Tampilkan film
        if (listFilm.isEmpty()) {
            System.out.println("\n[Belum ada data film!]\n");
            return;
        }
        int no = 1;
        for (Film f : listFilm) {
            System.out.println("\n--- Data ke " + no + " ---");
            f.showFilm();
            no++;
        }
    }

    //tiket
    static void pesanTiket(Pelanggan pelangganAktif) {
        if (listBioskop.isEmpty() || listStudio.isEmpty() || listFilm.isEmpty() || listJadwal.isEmpty()) {
            System.out.println("[Anda belum bisa memesan tiket karena admin belum memasukkan data bioskop, studio, film, dan jadwal!]");
            return;
        }
        Pemesanan pesananBaru = new Pemesanan(listFilm, listJadwal, listBioskop, pelangganAktif);
        // Jika user memesan melebihi kapasitas maka tidak bisa
        if (pesananBaru.getKodeJadwal() == null) {
            return;
        }

        // Memasukkan pemesanan ke pelanggan
        pelangganAktif.addPemesanan(pesananBaru);
        listPemesanan.add(pesananBaru);
        
        // Update pemesanan dan jadwal (kursi terisi)
        DataFile.writePemesanan(listPemesanan);
        DataFile.writeJadwal(listJadwal);
        // Cetak tiket
        pesananBaru.cetakTiket();
        System.out.println("\n[Tiket berhasil dipesan, Terima kasih dan selamat menonton!]\n");
    }

    static void showPemesanan() {
        // Menampilkan pesanan
        if (listPemesanan.isEmpty()) {
            System.out.println("Belum ada data pemesanan!");
            return;
        }
        int no = 1;
        for (Pemesanan p : listPemesanan) {
            System.out.println("\n--- Data ke " + no + " ---");
            p.showPemesanan();
            System.out.println();
            no++;
        }
    }

    static void showUser() {
        // Menampilkan data user
        System.out.println("\n--- Data User ---");

        System.out.println("\n--- Data ke " + 1 + " ---");
        System.out.println("Kode User : 1");
        System.out.println("Nama      : Admin Utama");
        System.out.println("Role      : Admin");

        int no = 2;

        for (Pelanggan p : listPelanggan) {
            System.out.println("\n--- Data ke " + no + " ---");
            p.showUser();
            no++;
        }
    }

    static void riwayatPemesanan(Pelanggan p) {
        // Menampilkan riwayat pemesanan
        if (p.getListPemesanan().isEmpty()) {
            System.out.println("\n[Anda belum pernah memesan tiket!]\n");
            return;
        }
        int no = 1;
        // Looping list pemesanan pelanggan
        for (Pemesanan pm : p.getListPemesanan()) {
            System.out.println("\n--- Pemesanan ke " + no + " ---");
            pm.showPemesanan();
            no++;
        }
    }

}