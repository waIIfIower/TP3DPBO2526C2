"""Modul kelas Mapres (Mahasiswa Berprestasi)."""

from dosen import Dosen
from mahasiswa import Mahasiswa


class Mapres(Mahasiswa):
    """Mahasiswa Berprestasi.

    Mapres mewarisi Mahasiswa yang sudah mewarisi Manusia. Rantai tiga tingkat
    Manusia -> Mahasiswa -> Mapres inilah yang disebut MULTILEVEL INHERITANCE.
    Karena turunan Mahasiswa, Mapres juga wajib memiliki dosen wali.
    """

    def __init__(self, nik: str, nama: str, umur: int, jenis_kelamin: str,
                 nim: str, jurusan: str, dosen_wali: Dosen,
                 nama_prestasi: str, tingkat_prestasi: str) -> None:
        """
        Args:
            nik: NIK mahasiswa.
            nama: Nama mahasiswa.
            umur: Umur mahasiswa.
            jenis_kelamin: Jenis kelamin mahasiswa.
            nim: Nomor induk mahasiswa.
            jurusan: Jurusan mahasiswa.
            dosen_wali: Dosen wali mahasiswa, tidak boleh None.
            nama_prestasi: Nama prestasi yang diraih.
            tingkat_prestasi: Tingkat prestasi, misalnya "Nasional" atau "Internasional".
        """
        super().__init__(nik, nama, umur, jenis_kelamin, nim, jurusan, dosen_wali)
        self._nama_prestasi = nama_prestasi
        self._tingkat_prestasi = tingkat_prestasi

    def get_peran(self) -> str:
        return "Mahasiswa Berprestasi"

    def tampilkan_info(self) -> None:
        """Mencetak info Mahasiswa lalu nama dan tingkat prestasi."""
        super().tampilkan_info()
        print(f"  Nama Prestasi    : {self._nama_prestasi}")
        print(f"  Tingkat Prestasi : {self._tingkat_prestasi}")
