/**
 * Mapres (Mahasiswa Berprestasi).
 *
 * Mapres mewarisi Mahasiswa yang sudah mewarisi Manusia. Rantai tiga tingkat
 * Manusia -> Mahasiswa -> Mapres inilah yang disebut MULTILEVEL INHERITANCE.
 * Karena turunan Mahasiswa, Mapres juga wajib memiliki dosen wali.
 */
public class Mapres extends Mahasiswa {

    private final String namaPrestasi;
    private final String tingkatPrestasi;

    /**
     * @param nik             NIK mahasiswa
     * @param nama            nama mahasiswa
     * @param umur            umur mahasiswa
     * @param jenisKelamin    jenis kelamin mahasiswa
     * @param nim             nomor induk mahasiswa
     * @param jurusan         jurusan mahasiswa
     * @param dosenWali       dosen wali mahasiswa, tidak boleh null
     * @param namaPrestasi    nama prestasi yang diraih
     * @param tingkatPrestasi tingkat prestasi, misalnya "Nasional" atau "Internasional"
     */
    public Mapres(String nik, String nama, int umur, String jenisKelamin,
                  String nim, String jurusan, Dosen dosenWali,
                  String namaPrestasi, String tingkatPrestasi) {
        super(nik, nama, umur, jenisKelamin, nim, jurusan, dosenWali);
        this.namaPrestasi = namaPrestasi;
        this.tingkatPrestasi = tingkatPrestasi;
    }

    public String getNamaPrestasi() {
        return namaPrestasi;
    }

    public String getTingkatPrestasi() {
        return tingkatPrestasi;
    }

    @Override
    public String getPeran() {
        return "Mahasiswa Berprestasi";
    }

    /** Mencetak info Mahasiswa lalu nama dan tingkat prestasi. */
    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.println("  Nama Prestasi    : " + namaPrestasi);
        System.out.println("  Tingkat Prestasi : " + tingkatPrestasi);
    }
}
