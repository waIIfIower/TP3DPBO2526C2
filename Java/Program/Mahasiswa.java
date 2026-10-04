import java.util.Objects;

/**
 * Mahasiswa, anak langsung dari Manusia (hierarchical inheritance) sekaligus
 * induk dari Mapres (multilevel inheritance).
 *
 * Mahasiswa berelasi ASSOCIATION dengan Dosen lewat atribut dosenWali.
 * Relasi ini wajib (multiplicity 1): setiap Mahasiswa harus punya tepat satu
 * dosen wali sejak objeknya dibuat, sehingga dosenWali tidak pernah null.
 * Relasi ini bukan composition karena Mahasiswa tidak membuat maupun memiliki
 * Dosen; Mahasiswa hanya menyimpan referensi ke Dosen yang sudah ada, dan satu
 * Dosen bisa dirujuk oleh banyak Mahasiswa.
 */
public class Mahasiswa extends Manusia {

    private final String nim;
    private final String jurusan;
    private Dosen dosenWali;

    /**
     * @param nik          NIK mahasiswa
     * @param nama         nama mahasiswa
     * @param umur         umur mahasiswa
     * @param jenisKelamin jenis kelamin mahasiswa
     * @param nim          nomor induk mahasiswa
     * @param jurusan      jurusan atau program studi
     * @param dosenWali    dosen wali mahasiswa, tidak boleh null
     * @throws NullPointerException jika dosenWali bernilai null
     */
    public Mahasiswa(String nik, String nama, int umur, String jenisKelamin,
                     String nim, String jurusan, Dosen dosenWali) {
        super(nik, nama, umur, jenisKelamin);
        this.nim = nim;
        this.jurusan = jurusan;
        this.dosenWali = Objects.requireNonNull(dosenWali, "Dosen wali wajib diisi");
    }

    public String getNim() {
        return nim;
    }

    public String getJurusan() {
        return jurusan;
    }

    public Dosen getDosenWali() {
        return dosenWali;
    }

    /**
     * Mengganti dosen wali dengan Dosen lain. Dosen wali tidak boleh dikosongkan,
     * sehingga nilai null ditolak.
     *
     * @param dosenWali dosen wali pengganti
     * @throws NullPointerException jika dosenWali bernilai null
     */
    public void setDosenWali(Dosen dosenWali) {
        this.dosenWali = Objects.requireNonNull(dosenWali, "Dosen wali wajib diisi");
    }

    @Override
    public String getPeran() {
        return "Mahasiswa";
    }

    /** Mencetak info dasar Manusia lalu NIM, jurusan, dan nama dosen wali. */
    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.println("  NIM           : " + nim);
        System.out.println("  Jurusan       : " + jurusan);
        System.out.println("  Dosen Wali    : " + dosenWali.getNama());
    }
}
