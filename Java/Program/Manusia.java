/**
 * Kelas dasar yang merepresentasikan seorang manusia.
 *
 * Kelas ini adalah akar seluruh pewarisan pada program:
 *
 *              Manusia
 *             /        \
 *        Mahasiswa     Dosen        (hierarchical inheritance)
 *           |
 *         Mapres                    (multilevel inheritance)
 *
 * Mahasiswa dan Dosen sama-sama mewarisi Manusia secara langsung,
 * sedangkan Mapres mewarisi Mahasiswa yang sudah mewarisi Manusia.
 * Gabungan kedua pola tersebut disebut hybrid inheritance.
 */
public class Manusia {

    private final String nik;
    private final String nama;
    private final int umur;
    private final String jenisKelamin;

    /**
     * @param nik          nomor induk kependudukan
     * @param nama         nama lengkap
     * @param umur         umur dalam tahun
     * @param jenisKelamin jenis kelamin, misalnya "Laki-laki" atau "Perempuan"
     */
    public Manusia(String nik, String nama, int umur, String jenisKelamin) {
        this.nik = nik;
        this.nama = nama;
        this.umur = umur;
        this.jenisKelamin = jenisKelamin;
    }

    public String getNik() {
        return nik;
    }

    public String getNama() {
        return nama;
    }

    public int getUmur() {
        return umur;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    /**
     * Mengembalikan label peran objek. Setiap subclass meng-override method
     * ini sehingga pemanggilan yang sama menghasilkan label yang berbeda
     * sesuai tipe objek sebenarnya (polimorfisme).
     */
    public String getPeran() {
        return "Manusia";
    }

    /**
     * Mencetak peran dan atribut dasar manusia, satu atribut per baris.
     * Subclass memanggil method ini lewat super.tampilkanInfo() lalu
     * menambahkan atribut miliknya sendiri.
     */
    public void tampilkanInfo() {
        System.out.println(getPeran());
        System.out.println("  NIK           : " + nik);
        System.out.println("  Nama          : " + nama);
        System.out.println("  Umur          : " + umur);
        System.out.println("  Jenis Kelamin : " + jenisKelamin);
    }
}
