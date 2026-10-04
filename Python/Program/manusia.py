"""Modul kelas Manusia.

Kelas ini adalah akar seluruh pewarisan pada program:

              Manusia
             /        \\
        Mahasiswa     Dosen        (hierarchical inheritance)
           |
         Mapres                    (multilevel inheritance)

Mahasiswa dan Dosen sama-sama mewarisi Manusia secara langsung,
sedangkan Mapres mewarisi Mahasiswa yang sudah mewarisi Manusia.
Gabungan kedua pola tersebut disebut hybrid inheritance.
"""


class Manusia:
    """Kelas dasar yang merepresentasikan seorang manusia."""

    def __init__(self, nik: str, nama: str, umur: int, jenis_kelamin: str) -> None:
        """
        Args:
            nik: Nomor induk kependudukan.
            nama: Nama lengkap.
            umur: Umur dalam tahun.
            jenis_kelamin: Jenis kelamin, misalnya "Laki-laki" atau "Perempuan".
        """
        self._nik = nik
        self._nama = nama
        self._umur = umur
        self._jenis_kelamin = jenis_kelamin

    @property
    def nama(self) -> str:
        return self._nama

    def get_peran(self) -> str:
        """Mengembalikan label peran.

        Subclass meng-override method ini sehingga pemanggilan yang sama
        menghasilkan label sesuai tipe objek sebenarnya (polimorfisme).
        """
        return "Manusia"

    def tampilkan_info(self) -> None:
        """Mencetak peran dan atribut dasar manusia, satu atribut per baris.

        Subclass memanggil method ini lewat super().tampilkan_info() lalu
        menambahkan atribut miliknya sendiri.
        """
        print(self.get_peran())
        print(f"  NIK           : {self._nik}")
        print(f"  Nama          : {self._nama}")
        print(f"  Umur          : {self._umur}")
        print(f"  Jenis Kelamin : {self._jenis_kelamin}")
