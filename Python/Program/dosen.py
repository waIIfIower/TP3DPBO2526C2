"""Modul kelas Dosen."""

from manusia import Manusia


class Dosen(Manusia):
    """Dosen, anak langsung dari Manusia (hierarchical inheritance).

    Dosen menjadi tujuan relasi ASSOCIATION dari Mahasiswa: setiap Mahasiswa
    wajib menyimpan referensi ke satu Dosen sebagai dosen wali, sedangkan satu
    Dosen dapat menjadi wali bagi banyak Mahasiswa. Dosen tetap objek yang
    berdiri sendiri dan tidak dimiliki oleh Mahasiswa.
    """

    def __init__(self, nik: str, nama: str, umur: int, jenis_kelamin: str,
                 nip: str, bidang_keahlian: str) -> None:
        """
        Args:
            nik: NIK dosen.
            nama: Nama dosen.
            umur: Umur dosen.
            jenis_kelamin: Jenis kelamin dosen.
            nip: Nomor induk pegawai.
            bidang_keahlian: Bidang keahlian dosen.
        """
        super().__init__(nik, nama, umur, jenis_kelamin)
        self._nip = nip
        self._bidang_keahlian = bidang_keahlian

    def get_peran(self) -> str:
        return "Dosen"

    def tampilkan_info(self) -> None:
        """Mencetak info dasar Manusia lalu NIP dan bidang keahlian."""
        super().tampilkan_info()
        print(f"  NIP           : {self._nip}")
        print(f"  Bidang Keahlian: {self._bidang_keahlian}")
