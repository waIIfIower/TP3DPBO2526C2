import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Titik masuk program: menu interaktif untuk menambah data Dosen, Mahasiswa,
 * dan Mapres ke sebuah Universitas.
 *
 * Konsep yang didemonstrasikan:
 *  - Hybrid inheritance (hierarchical + multilevel)
 *  - Composition: Universitas memiliki Alamat
 *  - Aggregation: Universitas menyimpan daftar Manusia
 *  - Association: setiap Mahasiswa/Mapres wajib punya satu Dosen sebagai dosen wali
 *  - Polimorfisme: tampilkanInfo() dan getPeran() di-override tiap kelas
 *
 * Alur program: pada setiap awal putaran menu, data universitas dicetak
 * terlebih dahulu. Dengan begitu tampilan tersebut menjadi data "sesudah"
 * untuk penambahan putaran sebelumnya sekaligus data "sebelum" untuk
 * penambahan berikutnya. Perulangan berhenti saat pengguna memilih menu 4,
 * yang mencetak data terbaru sekali lagi.
 *
 * Seluruh validasi input ada di kelas ini, bukan di kelas domain, supaya
 * kelas domain hanya berisi data dan perilaku objek.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    // Dosen yang sudah dibuat, dipakai sebagai pilihan dosen wali.
    private static final List<Dosen> daftarDosenDibuat = new ArrayList<>();

    public static void main(String[] args) {
        Universitas kampus = new Universitas(
                "Universitas DPBO",
                "Jawa Barat", "Bandung", "Jl. Merdeka No. 10", "40115"
        );

        while (true) {
            System.out.println(">> DATA UNIVERSITAS SAAT INI");
            kampus.tampilkanUniversitas();
            System.out.println();

            tampilkanMenu();
            int pilihan = bacaPilihanMenu();

            if (pilihan == 1) {
                tambahDosen(kampus);
            } else if (pilihan == 2) {
                tambahMahasiswa(kampus);
            } else if (pilihan == 3) {
                tambahMapres(kampus);
            } else if (pilihan == 4) {
                System.out.println();
                System.out.println(">> DATA TERBARU (PROGRAM SELESAI)");
                kampus.tampilkanUniversitas();
                break;
            } else {
                System.out.println("Pilihan tidak dikenali, silakan coba lagi.\n");
            }
        }

        scanner.close();
    }

    private static void tampilkanMenu() {
        System.out.println("===== MENU TAMBAH DATA UNIVERSITAS =====");
        System.out.println("1. Tambah Dosen");
        System.out.println("2. Tambah Mahasiswa");
        System.out.println("3. Tambah Mahasiswa Berprestasi (Mapres)");
        System.out.println("4. Selesai, tampilkan data terbaru lalu keluar");
        System.out.print("Pilih menu (1-4): ");
    }

    // ---------------------------------------------------------------------
    // Method pembaca input. Masing-masing mengulang permintaan sampai input
    // valid, sehingga program tidak berhenti karena salah ketik.
    // ---------------------------------------------------------------------

    /**
     * Membaca angka bulat untuk pilihan menu atau pilihan dosen wali.
     * Input yang bukan angka ditolak dan diminta ulang.
     */
    private static int bacaPilihanMenu() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Input harus berupa angka. Coba lagi: ");
            }
        }
    }

    /**
     * Membaca satu baris teks yang tidak boleh kosong. Dipakai untuk nama,
     * jenis kelamin, jurusan, bidang keahlian, dan data prestasi.
     */
    private static String bacaTeksWajib(String label) {
        while (true) {
            System.out.print(label);
            String teks = scanner.nextLine().trim();

            if (teks.isEmpty()) {
                System.out.println("Input tidak boleh kosong. Silakan ulangi.");
                continue;
            }

            return teks;
        }
    }

    /**
     * Membaca nomor identitas (NIK, NIM, atau NIP) yang hanya boleh berisi
     * digit 0-9. Nilainya disimpan sebagai String agar angka nol di depan
     * (misalnya "0012345") tidak hilang.
     */
    private static String bacaNomorIdentitas(String label) {
        while (true) {
            System.out.print(label);
            String teks = scanner.nextLine().trim();

            if (teks.isEmpty()) {
                System.out.println("Nomor identitas tidak boleh kosong. Silakan ulangi.");
                continue;
            }

            if (!teks.matches("[0-9]+")) {
                System.out.println("Input harus berupa angka (digit 0-9) tanpa huruf atau simbol. Silakan ulangi.");
                continue;
            }

            return teks;
        }
    }

    /**
     * Membaca umur yang harus berupa angka bulat dalam rentang 1 sampai 120.
     */
    private static int bacaUmur(String label) {
        while (true) {
            System.out.print(label);
            String teks = scanner.nextLine().trim();

            int umur;
            try {
                umur = Integer.parseInt(teks);
            } catch (NumberFormatException e) {
                System.out.println("Umur harus berupa angka bulat (tanpa huruf atau simbol). Silakan ulangi.");
                continue;
            }

            if (umur <= 0 || umur > 120) {
                System.out.println("Umur harus berada pada rentang 1 sampai 120 tahun. Silakan ulangi.");
                continue;
            }

            return umur;
        }
    }

    /**
     * Menampilkan daftar dosen dan meminta pengguna memilih satu sebagai
     * dosen wali. Pilihan tidak bisa dilewati: permintaan diulang sampai
     * pengguna memasukkan nomor yang ada di daftar.
     *
     * Method ini hanya dipanggil setelah dipastikan ada minimal satu dosen.
     */
    private static Dosen pilihDosenWali() {
        System.out.println("Pilih Dosen Wali:");
        for (int i = 0; i < daftarDosenDibuat.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + daftarDosenDibuat.get(i).getNama());
        }

        while (true) {
            System.out.print("Nomor pilihan: ");
            int nomor = bacaPilihanMenu();
            if (nomor >= 1 && nomor <= daftarDosenDibuat.size()) {
                return daftarDosenDibuat.get(nomor - 1);
            }
            System.out.println("Nomor di luar jangkauan, silakan ulangi.");
        }
    }

    // ---------------------------------------------------------------------
    // Alur penambahan data
    // ---------------------------------------------------------------------

    /**
     * Membuat Dosen dari input pengguna, lalu memasukkannya ke Universitas
     * (aggregation) dan ke daftarDosenDibuat agar bisa dipilih sebagai
     * dosen wali.
     */
    private static void tambahDosen(Universitas kampus) {
        System.out.println("\n--- Tambah Data Dosen ---");
        String nik = bacaNomorIdentitas("NIK            : ");
        String nama = bacaTeksWajib("Nama           : ");
        int umur = bacaUmur("Umur           : ");
        String jenisKelamin = bacaTeksWajib("Jenis Kelamin  : ");
        String nip = bacaNomorIdentitas("NIP            : ");
        String bidangKeahlian = bacaTeksWajib("Bidang Keahlian: ");

        Dosen dosenBaru = new Dosen(nik, nama, umur, jenisKelamin, nip, bidangKeahlian);

        kampus.tambahManusia(dosenBaru);
        daftarDosenDibuat.add(dosenBaru);

        System.out.println("Dosen \"" + nama + "\" berhasil ditambahkan.\n");
    }

    /**
     * Membuat Mahasiswa dari input pengguna, termasuk memilih dosen wali.
     * Karena dosen wali wajib, proses ditolak sebelum meminta data apa pun
     * jika belum ada Dosen terdaftar.
     */
    private static void tambahMahasiswa(Universitas kampus) {
        if (daftarDosenDibuat.isEmpty()) {
            System.out.println("\nTidak dapat menambahkan Mahasiswa: belum ada data Dosen.");
            System.out.println("Silakan tambahkan minimal satu Dosen terlebih dahulu (menu 1).\n");
            return;
        }

        System.out.println("\n--- Tambah Data Mahasiswa ---");
        String nik = bacaNomorIdentitas("NIK           : ");
        String nama = bacaTeksWajib("Nama          : ");
        int umur = bacaUmur("Umur          : ");
        String jenisKelamin = bacaTeksWajib("Jenis Kelamin : ");
        String nim = bacaNomorIdentitas("NIM           : ");
        String jurusan = bacaTeksWajib("Jurusan       : ");

        // Dosen wali dipilih sebelum objek dibuat karena constructor mewajibkannya.
        Dosen dosenWali = pilihDosenWali();
        Mahasiswa mahasiswaBaru = new Mahasiswa(nik, nama, umur, jenisKelamin, nim, jurusan, dosenWali);

        kampus.tambahManusia(mahasiswaBaru);

        System.out.println("Mahasiswa \"" + nama + "\" berhasil ditambahkan.\n");
    }

    /**
     * Membuat Mapres dari input pengguna. Alurnya sama dengan Mahasiswa
     * ditambah data prestasi, termasuk kewajiban memiliki dosen wali.
     */
    private static void tambahMapres(Universitas kampus) {
        if (daftarDosenDibuat.isEmpty()) {
            System.out.println("\nTidak dapat menambahkan Mapres: belum ada data Dosen.");
            System.out.println("Silakan tambahkan minimal satu Dosen terlebih dahulu (menu 1).\n");
            return;
        }

        System.out.println("\n--- Tambah Data Mahasiswa Berprestasi (Mapres) ---");
        String nik = bacaNomorIdentitas("NIK              : ");
        String nama = bacaTeksWajib("Nama             : ");
        int umur = bacaUmur("Umur             : ");
        String jenisKelamin = bacaTeksWajib("Jenis Kelamin    : ");
        String nim = bacaNomorIdentitas("NIM              : ");
        String jurusan = bacaTeksWajib("Jurusan          : ");
        String namaPrestasi = bacaTeksWajib("Nama Prestasi    : ");
        String tingkatPrestasi = bacaTeksWajib("Tingkat Prestasi : ");

        Dosen dosenWali = pilihDosenWali();
        Mapres mapresBaru = new Mapres(nik, nama, umur, jenisKelamin, nim, jurusan, dosenWali,
                namaPrestasi, tingkatPrestasi);

        kampus.tambahManusia(mapresBaru);

        System.out.println("Mapres \"" + nama + "\" berhasil ditambahkan.\n");
    }
}
