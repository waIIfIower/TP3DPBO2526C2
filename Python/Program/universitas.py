"""Modul kelas Universitas."""

from typing import List

from alamat import Alamat
from manusia import Manusia


class Universitas:
    """Universitas, dengan dua relasi yang berbeda:

    1. COMPOSITION terhadap Alamat: objek Alamat dibuat di dalam constructor
       Universitas sehingga masa hidupnya mengikuti Universitas.
    2. AGGREGATION terhadap Manusia: Universitas menyimpan daftar Manusia
       (bisa berisi Mahasiswa, Dosen, maupun Mapres berkat polimorfisme).
       Objek-objek tersebut dibuat di luar Universitas dan tetap ada
       walaupun Universitas dihapus.
    """

    def __init__(self, nama: str, provinsi: str, kota: str, jalan: str, kode_pos: str) -> None:
        """Membuat Universitas dengan daftar anggota yang masih kosong.

        Args:
            nama: Nama universitas.
            provinsi: Provinsi lokasi universitas.
            kota: Kota lokasi universitas.
            jalan: Nama jalan lokasi universitas.
            kode_pos: Kode pos lokasi universitas.
        """
        self._nama = nama
        self._alamat = Alamat(provinsi, kota, jalan, kode_pos)
        self._daftar_manusia: List[Manusia] = []

    def tambah_manusia(self, manusia: Manusia) -> None:
        """Menambahkan anggota ke universitas.

        Yang disimpan hanya referensi ke objek yang sudah ada, bukan salinan.

        Args:
            manusia: Mahasiswa, Dosen, atau Mapres yang akan ditambahkan.
        """
        self._daftar_manusia.append(manusia)

    def tampilkan_universitas(self) -> None:
        """Mencetak nama, alamat, jumlah anggota, dan informasi setiap anggota."""
        print(f"=== Universitas: {self._nama} ===")
        print(f"Alamat: {self._alamat}")
        print(f"Jumlah Anggota: {len(self._daftar_manusia)}")

        if not self._daftar_manusia:
            print("  (belum ada anggota)")
            return

        for anggota in self._daftar_manusia:
            # tampilkan_info() yang dijalankan mengikuti tipe objek sebenarnya
            anggota.tampilkan_info()
            print()
