import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate; 
import java.time.LocalTime;

public class DataFile {
    //bioskop
    public static void writeBioskop(ArrayList<Bioskop> listBioskop) {
        try {
            FileWriter fw = new FileWriter("src/bioskop.txt");
            for(Bioskop b : listBioskop) {
                fw.write(String.valueOf(b.getKodeBioskop()));  fw.write("|");
                fw.write(b.getNamaBioskop()); fw.write("|");
                fw.write(b.getLokasi());
                fw.write(System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Terjadi error!");
            e.printStackTrace();
        }
    }

    public static void readBioskop(ArrayList<Bioskop> listBioskop) {
        try {
            File file = new File("src/bioskop.txt");
            if (!file.exists()) return;
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] str = data.split("\\|");
                listBioskop.add(new Bioskop(Integer.parseInt(str[0]), (str[1]), (str[2])));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Terjadi Error!");
            e.printStackTrace();
        }
    }

    //studio
    public static void writeStudio(ArrayList<Studio> listStudio) {
        try {
            FileWriter fw = new FileWriter("src/studio.txt");
            for(Studio s : listStudio) {
                fw.write(String.valueOf(s.getKodeStudio()));  fw.write("|");
                fw.write(String.valueOf(s.getNomorStudio())); fw.write("|");
                fw.write(s.getJenisStudio()); fw.write("|");
                fw.write(s.getBioskop().getNamaBioskop()); fw.write("|");
                fw.write(String.valueOf(s.getHarga())); 
                fw.write(System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Terjadi error!");
            e.printStackTrace();
        }
    }


    public static void readStudio(ArrayList<Studio> listStudio, ArrayList<Bioskop> listBioskop) {
        File file = new File("src/studio.txt");

        if (!file.exists()) return;

        try {
            Scanner myReader = new Scanner(file);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] str = data.split("\\|");

                String namaBioskop = str[3];

                // cari objek Bioskop berdasarkan nama yang tersimpan di file
                Bioskop bioskop = null;

                for (Bioskop b : listBioskop) {
                    if (b.getNamaBioskop().equals(namaBioskop)) {
                        bioskop = b;
                        break;
                    }
                }

                if (bioskop != null) {
                    Studio studioBaru = new Studio(
                        Integer.parseInt(str[0]),
                        Integer.parseInt(str[1]),
                        str[2],
                        bioskop,
                        Float.parseFloat(str[4])
                    );
                    bioskop.addStudio(studioBaru);   // masuk ke listStudio milik bioskop
                    listStudio.add(studioBaru);      // masuk ke listStudio global
                }
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Terjadi Error!");
            e.printStackTrace();
        }
    }


    // film

    public static void writeFilm(ArrayList<Film> listFilm) {
        try {
            FileWriter fw = new FileWriter("src/film.txt");

            for (Film f : listFilm) {
                fw.write(f.getKodeFilm() + "|"
                        + f.getJudul() + "|"
                        + f.getGenre() + "|"
                        + f.getDurasi());

                fw.write(System.lineSeparator());
            }

            fw.close();

        } catch (IOException e) {
            System.out.println("Terjadi error!");
            e.printStackTrace();
        }
    }

    public static void readFilm(ArrayList<Film> listFilm) {
        File file = new File("src/film.txt");

        if (!file.exists()) return;

        try {
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                String[] p = data.split("\\|");

                listFilm.add(new Film(
                    Integer.parseInt(p[0]),
                    p[1],
                    p[2],
                    Integer.parseInt(p[3])
                ));
            }

            sc.close();

        } catch (FileNotFoundException e) {
            System.out.println("Terjadi Error!");
            e.printStackTrace();
        }
    }


    // jadwal
    public static void writeJadwal(ArrayList<JadwalFilm> listJadwal) {
        try {
            FileWriter fw = new FileWriter("src/jadwal.txt");

            for (JadwalFilm j : listJadwal) {
                fw.write(j.getKodeJadwal() + "|"
                        + j.getFilm().getKodeFilm() + "|"
                        + j.getStudio().getBioskop().getKodeBioskop() + "|"
                        + j.getStudio().getKodeStudio() + "|"
                        + j.getTanggal() + "|"
                        + j.getJam() + "|"
                        + j.getKapasitas() + "|"
                        + j.getKursiTerisi());

                fw.write(System.lineSeparator());
            }

            fw.close();

        } catch (IOException e) {
            System.out.println("Terjadi error!");
            e.printStackTrace();
        }
    }

    public static void readJadwal(ArrayList<JadwalFilm> listJadwal,
                                ArrayList<Studio> listStudio,
                                ArrayList<Film> listFilm) {

        File file = new File("src/jadwal.txt");

        if (!file.exists()) return;

        try {
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                String[] p = data.split("\\|");

                int kodeJadwal = Integer.parseInt(p[0]);
                int kodeFilm = Integer.parseInt(p[1]);
                int kodeBioskop = Integer.parseInt(p[2]);
                int kodeStudio = Integer.parseInt(p[3]);
                LocalDate tanggal = LocalDate.parse(p[4]);
                LocalTime jam = LocalTime.parse(p[5]);
                int kapasitas = Integer.parseInt(p[6]);
                int kursiTerisi = Integer.parseInt(p[7]);

                Studio studio = null;

                for (Studio s : listStudio) {
                    if (s.getBioskop().getKodeBioskop() == kodeBioskop
                            && s.getKodeStudio() == kodeStudio) {
                        studio = s;
                        break;
                    }
                }

                Film film = null;

                for (Film f : listFilm) {
                    if (f.getKodeFilm() == kodeFilm) {
                        film = f;
                        break;
                    }
                }

                if (studio != null && film != null) {
                    JadwalFilm j = new JadwalFilm(
                        kodeJadwal,
                        studio,
                        film,
                        tanggal,
                        jam,
                        kapasitas,
                        kursiTerisi
                    );
                    
                    listJadwal.add(j);
                    // Masukkan ke object Film dan Studio
                    film.addJadwal(j);
                    studio.addJadwal(j);
                }
            }

            sc.close();

        } catch (FileNotFoundException e) {
            System.out.println("Terjadi Error!");
            e.printStackTrace();
        }
    }

    //pelanggan
    public static void writeUser(ArrayList<Pelanggan> listPelanggan) {
        try {
            FileWriter fw = new FileWriter("src/user.txt");

            fw.write("1|Admin Utama|admin|admin123|Admin");
            fw.write(System.lineSeparator());

            for(Pelanggan p : listPelanggan) {
                fw.write(String.valueOf(p.getKodeUser())); fw.write("|");
                fw.write(p.getNama()); fw.write("|");
                fw.write(p.getEmail()); fw.write("|");
                fw.write(p.getPassword()); fw.write("|");
                fw.write(p.getRole());
                fw.write(System.lineSeparator());
            }

            fw.close();
        } catch (IOException e) {
            System.out.println("Terjadi error!");
            e.printStackTrace();
        }
    }

    public static void writePelanggan(ArrayList<Pelanggan> listPelanggan) {
        try {
            FileWriter fw = new FileWriter("src/pelanggan.txt");

            for(Pelanggan p : listPelanggan) {
                fw.write(String.valueOf(p.getKodeUser())); fw.write("|");
                fw.write(p.getNama()); fw.write("|");
                fw.write(p.getEmail()); fw.write("|");
                fw.write(p.getPassword()); fw.write("|");
                fw.write(p.getRole());
                fw.write(System.lineSeparator());
            }

            fw.close();
        } catch (IOException e) {
            System.out.println("Terjadi error!");
            e.printStackTrace();
        }
    }

    public static void readPelanggan(ArrayList<Pelanggan> listPelanggan) {
        File file = new File("src/pelanggan.txt");

        if (!file.exists()) return;

        try {
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String baris = sc.nextLine();

                if (baris.trim().isEmpty()) continue;

                String[] data = baris.split("\\|");

                if (data.length == 5) {
                    int kodeUser = Integer.parseInt(data[0]);
                    String nama = data[1];
                    String email = data[2];
                    String password = data[3];
                    String role = data[4];

                    Pelanggan p = new Pelanggan(nama, email, password, role);
                    p.setKodeUser(kodeUser);

                    listPelanggan.add(p);
                }
            }

            sc.close();

        } catch (FileNotFoundException e) {
            System.out.println("Terjadi Error!");
            e.printStackTrace();
        }
    }

    // pemesanan
    public static void writePemesanan(ArrayList<Pemesanan> listPemesanan) {
        try {
            FileWriter fw = new FileWriter("src/pemesanan.txt");

            for(Pemesanan p : listPemesanan) {
                int nomorPesanan = p.getKodePemesanan();
                String email = ((Pelanggan) p.getUser()).getEmail();
                int kodeJadwal = p.getKodeJadwal().getKodeJadwal();
                int jumlah = p.getJumlahPemesanan();
                String metode = p.getMetodePembayaran();
                float totalBayar = p.getTotalBayar();

                fw.write(nomorPesanan + "|" + email + "|" + kodeJadwal + "|" + jumlah + "|" + metode + "|" + String.format("%.0f", totalBayar));
                fw.write(System.lineSeparator());
            }

            fw.close();

        } catch (IOException e) {
            System.out.println("Terjadi error saat menyimpan data pemesanan!");
            e.printStackTrace();
        }
    }


    public static void readPemesanan(ArrayList<Pemesanan> listPemesanan, ArrayList<Pelanggan> listPelanggan, ArrayList<JadwalFilm> listJadwal) {

        File file = new File("src/pemesanan.txt");

        if (!file.exists()) return;

        try {
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String baris = sc.nextLine();

                if (baris.trim().isEmpty()) continue;

                String[] data = baris.split("\\|");

                if (data.length == 6) {
                    int nomorPesanan = Integer.parseInt(data[0].trim());
                    String email = data[1].trim();
                    int kodeJadwal = Integer.parseInt(data[2].trim());
                    int jumlah = Integer.parseInt(data[3].trim());
                    String metode = data[4].trim();
                    float totalBayar = Float.parseFloat(data[5].trim());

                    Pelanggan pelangganKetemu = null;

                    for(Pelanggan user : listPelanggan) {
                        if (user.getEmail().equalsIgnoreCase(email)) {
                            pelangganKetemu = user;
                            break;
                        }
                    }

                    JadwalFilm jadwalKetemu = null;

                    for(JadwalFilm j : listJadwal) {
                        if (j.getKodeJadwal() == kodeJadwal) {
                            jadwalKetemu = j;
                            break;
                        }
                    }

                    if (pelangganKetemu != null && jadwalKetemu != null) {
                        Pemesanan pesananLama = new Pemesanan(
                            nomorPesanan,
                            jadwalKetemu,
                            jumlah,
                            metode,
                            totalBayar,
                            pelangganKetemu
                        );

                        listPemesanan.add(pesananLama);
                        pelangganKetemu.addPemesanan(pesananLama); 
                    }
                }
            }

            sc.close();

        } catch (Exception e) {
            System.out.println("Terjadi Error saat membaca pemesanan!");
            e.printStackTrace();
        }
    }
}
