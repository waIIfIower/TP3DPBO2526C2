"""Titik masuk program: menu interaktif untuk menambah data Dosen, Mahasiswa,
dan Mapres ke sebuah Universitas.

Konsep yang didemonstrasikan:
    - Hybrid inheritance (hierarchical + multilevel)
    - Composition: Universitas memiliki Alamat
    - Aggregation: Universitas menyimpan daftar Manusia
    - Association: setiap Mahasiswa/Mapres wajib punya satu Dosen sebagai dosen wali
    - Polimorfisme: tampilkan_info() dan get_peran() di-override tiap kelas

Alur program: pada setiap awal putaran menu, data universitas dicetak
terlebih dahulu. Dengan begitu tampilan tersebut menjadi data "sesudah"
untuk penambahan putaran sebelumnya sekaligus data "sebelum" untuk
penambahan berikutnya. Perulangan berhenti saat pengguna memilih menu 4,
yang mencetak data terbaru sekali lagi.

Seluruh validasi input ada di file ini, bukan di kelas domain, supaya
kelas domain hanya berisi data dan perilaku objek.

Jalankan dengan: python main.py
"""

from typing import List

from mapres import Mapres
from universitas import Universitas
from mahasiswa import Mahasiswa
from dosen import Dosen

# Dosen yang sudah dibuat, dipakai sebagai pilihan dosen wali.
daftar_dosen_dibuat: List[Dosen] = []


# -----------------------------------------------------------------------------
# Fungsi pembaca input. Masing-masing mengulang permintaan sampai input valid,
# sehingga program tidak berhenti karena salah ketik.
# -----------------------------------------------------------------------------


def baca_teks_wajib(label: str) -> str:
    """Membaca satu baris teks yang tidak boleh kosong.

    Dipakai untuk nama, jenis kelamin, jurusan, bidang keahlian, dan data prestasi.
    """
    while True:
        teks = input(label).strip()

        if teks == "":
            print("Input tidak boleh kosong. Silakan ulangi.")
            continue

        return teks


def baca_nomor_identitas(label: str) -> str:
    """Membaca nomor identitas (NIK, NIM, atau NIP) yang hanya boleh berisi digit 0-9.

    Nilainya disimpan sebagai str agar angka nol di depan (misalnya
    "0012345") tidak hilang.
    """
    while True:
        teks = input(label).strip()

        if teks == "":
            print("Nomor identitas tidak boleh kosong. Silakan ulangi.")
            continue

        # isdigit() bernilai True hanya jika seluruh karakter adalah digit
        if not teks.isdigit():
            print("Input harus berupa angka (digit 0-9) tanpa huruf atau simbol. Silakan ulangi.")
            continue

        return teks


def baca_umur(label: str) -> int:
    """Membaca umur yang harus berupa angka bulat dalam rentang 1 sampai 120."""
    while True:
        teks = input(label).strip()

        try:
            umur = int(teks)
        except ValueError:
            print("Umur harus berupa angka bulat (tanpa huruf atau simbol). Silakan ulangi.")
            continue

        if umur <= 0 or umur > 120:
            print("Umur harus berada pada rentang 1 sampai 120 tahun. Silakan ulangi.")
            continue

        return umur


def baca_pilihan_menu() -> int:
    """Membaca angka bulat untuk pilihan menu atau pilihan dosen wali.

    Input yang bukan angka ditolak dan diminta ulang.
    """
    while True:
        teks = input().strip()
        try:
            return int(teks)
        except ValueError:
            print("Input harus berupa angka. Coba lagi: ", end="")


def tampilkan_menu() -> None:
    print("===== MENU TAMBAH DATA UNIVERSITAS =====")
    print("1. Tambah Dosen")
    print("2. Tambah Mahasiswa")
    print("3. Tambah Mahasiswa Berprestasi (Mapres)")
    print("4. Selesai, tampilkan data terbaru lalu keluar")
    print("Pilih menu (1-4): ", end="")


def pilih_dosen_wali() -> Dosen:
    """Menampilkan daftar dosen dan meminta pengguna memilih satu sebagai dosen wali.

    Pilihan tidak bisa dilewati: permintaan diulang sampai pengguna
    memasukkan nomor yang ada di daftar.

    Fungsi ini hanya dipanggil setelah dipastikan ada minimal satu dosen.
    """
    print("Pilih Dosen Wali:")
    for i, dosen in enumerate(daftar_dosen_dibuat, start=1):
        print(f"  {i}. {dosen.nama}")

    while True:
        print("Nomor pilihan: ", end="")
        nomor = baca_pilihan_menu()
        if 1 <= nomor <= len(daftar_dosen_dibuat):
            return daftar_dosen_dibuat[nomor - 1]
        print("Nomor di luar jangkauan, silakan ulangi.")


# -----------------------------------------------------------------------------
# Alur penambahan data
# -----------------------------------------------------------------------------


def tambah_dosen(kampus: Universitas) -> None:
    """Membuat Dosen dari input pengguna, lalu memasukkannya ke Universitas
    (aggregation) dan ke daftar_dosen_dibuat agar bisa dipilih sebagai dosen wali.
    """
    print("\n--- Tambah Data Dosen ---")
    nik = baca_nomor_identitas("NIK            : ")
    nama = baca_teks_wajib("Nama           : ")
    umur = baca_umur("Umur           : ")
    jenis_kelamin = baca_teks_wajib("Jenis Kelamin  : ")
    nip = baca_nomor_identitas("NIP            : ")
    bidang_keahlian = baca_teks_wajib("Bidang Keahlian: ")

    dosen_baru = Dosen(nik, nama, umur, jenis_kelamin, nip, bidang_keahlian)

    kampus.tambah_manusia(dosen_baru)
    daftar_dosen_dibuat.append(dosen_baru)

    print(f'Dosen "{nama}" berhasil ditambahkan.\n')


def tambah_mahasiswa(kampus: Universitas) -> None:
    """Membuat Mahasiswa dari input pengguna, termasuk memilih dosen wali.

    Karena dosen wali wajib, proses ditolak sebelum meminta data apa pun
    jika belum ada Dosen terdaftar.
    """
    if not daftar_dosen_dibuat:
        print("\nTidak dapat menambahkan Mahasiswa: belum ada data Dosen.")
        print("Silakan tambahkan minimal satu Dosen terlebih dahulu (menu 1).\n")
        return

    print("\n--- Tambah Data Mahasiswa ---")
    nik = baca_nomor_identitas("NIK           : ")
    nama = baca_teks_wajib("Nama          : ")
    umur = baca_umur("Umur          : ")
    jenis_kelamin = baca_teks_wajib("Jenis Kelamin : ")
    nim = baca_nomor_identitas("NIM           : ")
    jurusan = baca_teks_wajib("Jurusan       : ")

    # Dosen wali dipilih sebelum objek dibuat karena constructor mewajibkannya.
    dosen_wali = pilih_dosen_wali()
    mahasiswa_baru = Mahasiswa(nik, nama, umur, jenis_kelamin, nim, jurusan, dosen_wali)

    kampus.tambah_manusia(mahasiswa_baru)

    print(f'Mahasiswa "{nama}" berhasil ditambahkan.\n')


def tambah_mapres(kampus: Universitas) -> None:
    """Membuat Mapres dari input pengguna.

    Alurnya sama dengan Mahasiswa ditambah data prestasi, termasuk
    kewajiban memiliki dosen wali.
    """
    if not daftar_dosen_dibuat:
        print("\nTidak dapat menambahkan Mapres: belum ada data Dosen.")
        print("Silakan tambahkan minimal satu Dosen terlebih dahulu (menu 1).\n")
        return

    print("\n--- Tambah Data Mahasiswa Berprestasi (Mapres) ---")
    nik = baca_nomor_identitas("NIK              : ")
    nama = baca_teks_wajib("Nama             : ")
    umur = baca_umur("Umur             : ")
    jenis_kelamin = baca_teks_wajib("Jenis Kelamin    : ")
    nim = baca_nomor_identitas("NIM              : ")
    jurusan = baca_teks_wajib("Jurusan          : ")
    nama_prestasi = baca_teks_wajib("Nama Prestasi    : ")
    tingkat_prestasi = baca_teks_wajib("Tingkat Prestasi : ")

    dosen_wali = pilih_dosen_wali()
    mapres_baru = Mapres(nik, nama, umur, jenis_kelamin, nim, jurusan, dosen_wali,
                          nama_prestasi, tingkat_prestasi)

    kampus.tambah_manusia(mapres_baru)

    print(f'Mapres "{nama}" berhasil ditambahkan.\n')


def main() -> None:
    kampus = Universitas(
        "Universitas DPBO",
        "Jawa Barat", "Bandung", "Jl. Merdeka No. 10", "40115",
    )

    while True:
        print(">> DATA UNIVERSITAS SAAT INI")
        kampus.tampilkan_universitas()
        print()

        tampilkan_menu()
        pilihan = baca_pilihan_menu()

        if pilihan == 1:
            tambah_dosen(kampus)
        elif pilihan == 2:
            tambah_mahasiswa(kampus)
        elif pilihan == 3:
            tambah_mapres(kampus)
        elif pilihan == 4:
            print()
            print(">> DATA TERBARU (PROGRAM SELESAI)")
            kampus.tampilkan_universitas()
            break
        else:
            print("Pilihan tidak dikenali, silakan coba lagi.\n")


if __name__ == "__main__":
    main()
