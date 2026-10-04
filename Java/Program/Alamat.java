/**
 * Alamat sebuah universitas.
 *
 * Alamat adalah bagian dari Universitas lewat relasi COMPOSITION: objek
 * Alamat dibuat di dalam constructor Universitas dan tidak punya makna
 * tanpa Universitas yang memilikinya.
 */
public class Alamat {

    private final String provinsi;
    private final String kota;
    private final String jalan;
    private final String kodePos;

    /**
     * @param provinsi nama provinsi
     * @param kota     nama kota
     * @param jalan    nama jalan beserta nomornya
     * @param kodePos  kode pos
     */
    public Alamat(String provinsi, String kota, String jalan, String kodePos) {
        this.provinsi = provinsi;
        this.kota = kota;
        this.jalan = jalan;
        this.kodePos = kodePos;
    }

    /** Mengembalikan alamat dalam satu baris: jalan, kota, provinsi, kode pos. */
    @Override
    public String toString() {
        return jalan + ", " + kota + ", " + provinsi + " " + kodePos;
    }
}
