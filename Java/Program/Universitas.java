import java.util.ArrayList;
import java.util.List;

/**
 * Universitas, dengan dua relasi yang berbeda:
 *
 *  1. COMPOSITION terhadap Alamat: objek Alamat dibuat di dalam constructor
 *     Universitas sehingga masa hidupnya mengikuti Universitas.
 *
 *  2. AGGREGATION terhadap Manusia: Universitas menyimpan daftar Manusia
 *     (bisa berisi Mahasiswa, Dosen, maupun Mapres berkat polimorfisme).
 *     Objek-objek tersebut dibuat di luar Universitas dan tetap ada
 *     walaupun Universitas dihapus.
 */
public class Universitas {

    private final String nama;
    private final Alamat alamat;
    private final List<Manusia> daftarManusia;

    /**
     * Membuat Universitas dengan daftar anggota yang masih kosong.
     *
     * @param nama     nama universitas
     * @param provinsi provinsi lokasi universitas
     * @param kota     kota lokasi universitas
     * @param jalan    nama jalan lokasi universitas
     * @param kodePos  kode pos lokasi universitas
     */
    public Universitas(String nama, String provinsi, String kota, String jalan, String kodePos) {
        this.nama = nama;
        this.alamat = new Alamat(provinsi, kota, jalan, kodePos);
        this.daftarManusia = new ArrayList<>();
    }

    /**
     * Menambahkan anggota ke universitas. Yang disimpan hanya referensi ke
     * objek yang sudah ada, bukan salinan.
     *
     * @param manusia Mahasiswa, Dosen, atau Mapres yang akan ditambahkan
     */
    public void tambahManusia(Manusia manusia) {
        daftarManusia.add(manusia);
    }

    public int getJumlahAnggota() {
        return daftarManusia.size();
    }

    /** Mencetak nama, alamat, jumlah anggota, dan informasi setiap anggota. */
    public void tampilkanUniversitas() {
        System.out.println("=== " + nama + " ===");
        System.out.println("Alamat: " + alamat);
        System.out.println("\nJumlah Anggota: " + daftarManusia.size());

        if (daftarManusia.isEmpty()) {
            System.out.println("  (belum ada anggota)");
            return;
        }

        for (Manusia anggota : daftarManusia) {
            // tampilkanInfo() yang dijalankan mengikuti tipe objek sebenarnya
            anggota.tampilkanInfo();
            System.out.println();
        }
    }
}
