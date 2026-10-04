/**
 * Dosen, anak langsung dari Manusia (hierarchical inheritance).
 *
 * Dosen menjadi tujuan relasi ASSOCIATION dari Mahasiswa: setiap Mahasiswa
 * wajib menyimpan referensi ke satu Dosen sebagai dosen wali, sedangkan satu
 * Dosen dapat menjadi wali bagi banyak Mahasiswa. Dosen tetap objek yang
 * berdiri sendiri dan tidak dimiliki oleh Mahasiswa.
 */
public class Dosen extends Manusia {

    private final String nip;
    private final String bidangKeahlian;

    /**
     * @param nik            NIK dosen
     * @param nama           nama dosen
     * @param umur           umur dosen
     * @param jenisKelamin   jenis kelamin dosen
     * @param nip            nomor induk pegawai
     * @param bidangKeahlian bidang keahlian dosen
     */
    public Dosen(String nik, String nama, int umur, String jenisKelamin,
                 String nip, String bidangKeahlian) {
        super(nik, nama, umur, jenisKelamin);
        this.nip = nip;
        this.bidangKeahlian = bidangKeahlian;
    }

    public String getNip() {
        return nip;
    }

    public String getBidangKeahlian() {
        return bidangKeahlian;
    }

    @Override
    public String getPeran() {
        return "Dosen";
    }

    /** Mencetak info dasar Manusia lalu NIP dan bidang keahlian. */
    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.println("  NIP           : " + nip);
        System.out.println("  Bidang Keahlian: " + bidangKeahlian);
    }
}
