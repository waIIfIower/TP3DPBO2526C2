"""Modul kelas Alamat."""


class Alamat:
    """Alamat sebuah universitas.

    Alamat adalah bagian dari Universitas lewat relasi COMPOSITION: objek
    Alamat dibuat di dalam constructor Universitas dan tidak punya makna
    tanpa Universitas yang memilikinya.
    """

    def __init__(self, provinsi: str, kota: str, jalan: str, kode_pos: str) -> None:
        """
        Args:
            provinsi: Nama provinsi.
            kota: Nama kota.
            jalan: Nama jalan beserta nomornya.
            kode_pos: Kode pos.
        """
        self._provinsi = provinsi
        self._kota = kota
        self._jalan = jalan
        self._kode_pos = kode_pos

    def __str__(self) -> str:
        """Mengembalikan alamat dalam satu baris: jalan, kota, provinsi, kode pos."""
        return f"{self._jalan}, {self._kota}, {self._provinsi} {self._kode_pos}"
