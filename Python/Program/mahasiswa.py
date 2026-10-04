"""Modul kelas Mahasiswa."""

from manusia import Manusia
from dosen import Dosen


class Mahasiswa(Manusia):
    """Mahasiswa, anak langsung dari Manusia (hierarchical inheritance)
    sekaligus induk dari Mapres (multilevel inheritance).

    Mahasiswa berelasi ASSOCIATION dengan Dosen lewat atribut dosen_wali.
    Relasi ini wajib (multiplicity 1): setiap Mahasiswa harus punya tepat satu
    dosen wali sejak objeknya dibuat, sehingga dosen_wali tidak pernah None.
    Relasi ini bukan composition karena Mahasiswa tidak membuat maupun memiliki
    Dosen; Mahasiswa hanya menyimpan referensi ke Dosen yang sudah ada, dan
    satu Dosen bisa dirujuk oleh banyak Mahasiswa.
    """

    def __init__(self, nik: str, nama: str, umur: int, jenis_kelamin: str,
                 nim: str, jurusan: str, dosen_wali: Dosen) -> None:
        """
        Args:
            nik: NIK mahasiswa.
            nama: Nama mahasiswa.
            umur: Umur mahasiswa.
            jenis_kelamin: Jenis kelamin mahasiswa.
            nim: Nomor induk mahasiswa.
            jurusan: Jurusan atau program studi.
            dosen_wali: Dosen wali mahasiswa, tidak boleh None.

        Raises:
            ValueError: Jika dosen_wali bernilai None.
        """
        super().__init__(nik, nama, umur, jenis_kelamin)
        self._nim = nim
        self._jurusan = jurusan
        self._dosen_wali = self._periksa_dosen_wali(dosen_wali)

    @staticmethod
    def _periksa_dosen_wali(dosen_wali: Dosen) -> Dosen:
        """Memastikan dosen wali tidak kosong, lalu mengembalikannya."""
        if dosen_wali is None:
            raise ValueError("Dosen wali wajib diisi")
        return dosen_wali

    @property
    def dosen_wali(self) -> Dosen:
        return self._dosen_wali

    def set_dosen_wali(self, dosen_wali: Dosen) -> None:
        """Mengganti dosen wali dengan Dosen lain.

        Dosen wali tidak boleh dikosongkan, sehingga None ditolak.

        Args:
            dosen_wali: Dosen wali pengganti.

        Raises:
            ValueError: Jika dosen_wali bernilai None.
        """
        self._dosen_wali = self._periksa_dosen_wali(dosen_wali)

    def get_peran(self) -> str:
        return "Mahasiswa"

    def tampilkan_info(self) -> None:
        """Mencetak info dasar Manusia lalu NIM, jurusan, dan nama dosen wali."""
        super().tampilkan_info()
        print(f"  NIM           : {self._nim}")
        print(f"  Jurusan       : {self._jurusan}")
        print(f"  Dosen Wali    : {self._dosen_wali.nama}")
