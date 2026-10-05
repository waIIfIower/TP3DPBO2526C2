/**
 * Titik masuk program: menu interaktif untuk menambah data Dosen, Mahasiswa,
 * dan Mapres ke sebuah Universitas.
 *
 * Konsep yang didemonstrasikan:
 *  - Hybrid inheritance (hierarchical + multilevel)
 *  - Composition: Universitas memiliki Alamat
 *  - Aggregation: Universitas menyimpan daftar Manusia*
 *  - Association: setiap Mahasiswa/Mapres wajib punya satu Dosen sebagai dosen wali
 *  - Polimorfisme: tampilkanInfo() dan getPeran() bersifat virtual/override
 *
 * Alur program: pada setiap awal putaran menu, data universitas dicetak
 * terlebih dahulu. Dengan begitu tampilan tersebut menjadi data "sesudah"
 * untuk penambahan putaran sebelumnya sekaligus data "sebelum" untuk
 * penambahan berikutnya. Perulangan berhenti saat pengguna memilih menu 4,
 * yang mencetak data terbaru sekali lagi.
 *
 * Seluruh validasi input ada di file ini, bukan di class domain, supaya
 * class domain hanya berisi data dan perilaku objek.
 *
 * Cara build dan jalankan (cukup kompilasi Main.cpp karena file .cpp lain
 * ikut ter-include):
 *   g++ Main.cpp -o Main.exe
 *   ./Main.exe
 */
#include <cctype>
#include <iostream>
#include <stdexcept>
#include <string>
#include <vector>
#include "Manusia.cpp"
#include "Dosen.cpp"
#include "Mahasiswa.cpp"
#include "Mapres.cpp"
#include "Universitas.cpp"

using namespace std;

// Dosen yang sudah dibuat, dipakai sebagai pilihan dosen wali. Dosen dibuat
// dengan 'new' agar tetap hidup setelah fungsi tambahDosen() selesai.
vector<Dosen *> daftarDosenDibuat;

// -----------------------------------------------------------------------------
// Fungsi pembaca input. Masing-masing mengulang permintaan sampai input valid,
// sehingga program tidak berhenti karena salah ketik.
// -----------------------------------------------------------------------------

// Memeriksa apakah teks tidak kosong dan hanya berisi digit 0-9.
bool hanyaDigit(const string &teks)
{
    if (teks.empty())
    {
        return false;
    }
    for (char c : teks)
    {
        if (!isdigit(static_cast<unsigned char>(c)))
        {
            return false;
        }
    }
    return true;
}

// Membaca satu baris teks yang tidak boleh kosong. Dipakai untuk nama,
// jenis kelamin, jurusan, bidang keahlian, dan data prestasi.
string bacaTeksWajib(const string &label)
{
    while (true)
    {
        cout << label;
        string teks;
        getline(cin, teks);

        if (teks.empty())
        {
            cout << "Input tidak boleh kosong. Silakan ulangi." << endl;
            continue;
        }

        return teks;
    }
}

// Membaca nomor identitas (NIK, NIM, atau NIP) yang hanya boleh berisi digit
// 0-9. Nilainya disimpan sebagai string agar angka nol di depan (misalnya
// "0012345") tidak hilang.
string bacaNomorIdentitas(const string &label)
{
    while (true)
    {
        cout << label;
        string teks;
        getline(cin, teks);

        if (teks.empty())
        {
            cout << "Nomor identitas tidak boleh kosong. Silakan ulangi." << endl;
            continue;
        }

        if (!hanyaDigit(teks))
        {
            cout << "Input harus berupa angka (digit 0-9) tanpa huruf atau simbol. Silakan ulangi." << endl;
            continue;
        }

        return teks;
    }
}

// Membaca umur yang harus berupa angka bulat dalam rentang 1 sampai 120.
int bacaUmur(const string &label)
{
    while (true)
    {
        cout << label;
        string baris;
        getline(cin, baris);

        int umur;
        try
        {
            size_t posisi;
            umur = stoi(baris, &posisi);
            // stoi menerima awalan angka (misalnya "20tahun"), jadi sisa
            // karakter diperiksa agar seluruh teks harus berupa angka.
            if (posisi != baris.size())
            {
                throw invalid_argument("bukan angka murni");
            }
        }
        catch (...)
        {
            cout << "Umur harus berupa angka bulat (tanpa huruf atau simbol). Silakan ulangi." << endl;
            continue;
        }

        if (umur <= 0 || umur > 120)
        {
            cout << "Umur harus berada pada rentang 1 sampai 120 tahun. Silakan ulangi." << endl;
            continue;
        }

        return umur;
    }
}

// Membaca angka bulat untuk pilihan menu atau pilihan dosen wali.
// Input yang bukan angka ditolak dan diminta ulang.
int bacaPilihanMenu()
{
    while (true)
    {
        string baris;
        getline(cin, baris);
        try
        {
            size_t posisi;
            int pilihan = stoi(baris, &posisi);
            if (posisi == baris.size())
            {
                return pilihan;
            }
        }
        catch (...)
        {
        }
        cout << "Input invalid. Coba lagi: ";
    }
}

void tampilkanMenu()
{
    cout << "===== MENU TAMBAH DATA UNIVERSITAS =====" << endl;
    cout << "1. Tambah Dosen" << endl;
    cout << "2. Tambah Mahasiswa" << endl;
    cout << "3. Tambah Mahasiswa Berprestasi (Mapres)" << endl;
    cout << "4. Selesai, tampilkan data terbaru lalu keluar" << endl;
    cout << "Pilih Opsi: ";
}

// Menampilkan daftar dosen dan meminta pengguna memilih satu sebagai dosen
// wali. Pilihan tidak bisa dilewati: permintaan diulang sampai pengguna
// memasukkan nomor yang ada di daftar.
//
// Fungsi ini hanya dipanggil setelah dipastikan ada minimal satu dosen.
Dosen *pilihDosenWali()
{
    cout << "Pilih Dosen Wali:" << endl;
    for (size_t i = 0; i < daftarDosenDibuat.size(); i++)
    {
        cout << "  " << (i + 1) << ". " << daftarDosenDibuat[i]->getNama() << endl;
    }

    while (true)
    {
        cout << "Nomor pilihan: ";
        int nomor = bacaPilihanMenu();
        if (nomor >= 1 && static_cast<size_t>(nomor) <= daftarDosenDibuat.size())
        {
            return daftarDosenDibuat[nomor - 1];
        }
        cout << "Nomor di luar jangkauan, silakan ulangi." << endl;
    }
}

// -----------------------------------------------------------------------------
// Alur penambahan data
// -----------------------------------------------------------------------------

// Membuat Dosen dari input pengguna, lalu memasukkannya ke Universitas
// (aggregation) dan ke daftarDosenDibuat agar bisa dipilih sebagai dosen wali.
void tambahDosen(Universitas &kampus)
{
    cout << endl
         << "--- Tambah Data Dosen ---" << endl;
    string nik = bacaNomorIdentitas("NIK            : ");
    string nama = bacaTeksWajib("Nama           : ");
    int umur = bacaUmur("Umur           : ");
    string jenisKelamin = bacaTeksWajib("Jenis Kelamin  : ");
    string nip = bacaNomorIdentitas("NIP            : ");
    string bidangKeahlian = bacaTeksWajib("Bidang Keahlian: ");

    Dosen *dosenBaru = new Dosen(nik, nama, umur, jenisKelamin, nip, bidangKeahlian);

    kampus.tambahManusia(dosenBaru);
    daftarDosenDibuat.push_back(dosenBaru);

    cout << "Dosen \"" << nama << "\" berhasil ditambahkan." << endl
         << endl;
}

// Membuat Mahasiswa dari input pengguna, termasuk memilih dosen wali.
// Karena dosen wali wajib, proses ditolak sebelum meminta data apa pun
// jika belum ada Dosen terdaftar.
void tambahMahasiswa(Universitas &kampus)
{
    if (daftarDosenDibuat.empty())
    {
        cout << endl
             << "Tidak dapat menambahkan Mahasiswa: belum ada data Dosen." << endl;
        cout << "Silakan tambahkan minimal satu Dosen terlebih dahulu (menu 1)." << endl
             << endl;
        return;
    }

    cout << endl
         << "--- Tambah Data Mahasiswa ---" << endl;
    string nik = bacaNomorIdentitas("NIK           : ");
    string nama = bacaTeksWajib("Nama          : ");
    int umur = bacaUmur("Umur          : ");
    string jenisKelamin = bacaTeksWajib("Jenis Kelamin : ");
    string nim = bacaNomorIdentitas("NIM           : ");
    string jurusan = bacaTeksWajib("Jurusan       : ");

    // Dosen wali dipilih sebelum objek dibuat karena constructor mewajibkannya.
    Dosen *dosenWali = pilihDosenWali();
    Mahasiswa *mahasiswaBaru = new Mahasiswa(nik, nama, umur, jenisKelamin, nim, jurusan, dosenWali);

    kampus.tambahManusia(mahasiswaBaru);

    cout << "Mahasiswa \"" << nama << "\" berhasil ditambahkan." << endl
         << endl;
}

// Membuat Mapres dari input pengguna. Alurnya sama dengan Mahasiswa
// ditambah data prestasi, termasuk kewajiban memiliki dosen wali.
void tambahMapres(Universitas &kampus)
{
    if (daftarDosenDibuat.empty())
    {
        cout << endl
             << "Tidak dapat menambahkan Mapres: belum ada data Dosen." << endl;
        cout << "Silakan tambahkan minimal satu Dosen terlebih dahulu (menu 1)." << endl
             << endl;
        return;
    }

    cout << endl
         << "--- Tambah Data Mahasiswa Berprestasi (Mapres) ---" << endl;
    string nik = bacaNomorIdentitas("NIK              : ");
    string nama = bacaTeksWajib("Nama             : ");
    int umur = bacaUmur("Umur             : ");
    string jenisKelamin = bacaTeksWajib("Jenis Kelamin    : ");
    string nim = bacaNomorIdentitas("NIM              : ");
    string jurusan = bacaTeksWajib("Jurusan          : ");
    string namaPrestasi = bacaTeksWajib("Nama Prestasi    : ");
    string tingkatPrestasi = bacaTeksWajib("Tingkat Prestasi : ");

    Dosen *dosenWali = pilihDosenWali();
    Mapres *mapresBaru = new Mapres(nik, nama, umur, jenisKelamin, nim, jurusan, dosenWali,
                                    namaPrestasi, tingkatPrestasi);

    kampus.tambahManusia(mapresBaru);

    cout << "Mapres \"" << nama << "\" berhasil ditambahkan." << endl
         << endl;
}

int main()
{
    Universitas kampus("Universitas Teknologi Cimahi",
                       "Jawa Barat", "Cimahi", "Jl. Budi Indah No A.4", "40514");

    while (true)
    {
        cout << endl << ">> DATA UNIVERSITAS SAAT INI <<" << endl << endl;
        kampus.tampilkanUniversitas();
        cout << endl;

        tampilkanMenu();
        int pilihan = bacaPilihanMenu();

        if (pilihan == 1)
        {
            tambahDosen(kampus);
        }
        else if (pilihan == 2)
        {
            tambahMahasiswa(kampus);
        }
        else if (pilihan == 3)
        {
            tambahMapres(kampus);
        }
        else if (pilihan == 4)
        {
            cout << endl
                 << ">> DATA TERBARU (PROGRAM SELESAI)" << endl;
            kampus.tampilkanUniversitas();
            break;
        }
        else
        {
            cout << "Pilihan tidak dikenali, silakan coba lagi." << endl
                 << endl;
        }
    }

    // Hanya objek Dosen yang dibebaskan di sini; objek Mahasiswa dan Mapres
    // yang tersimpan di Universitas dibiarkan hingga program berakhir.
    for (Dosen *d : daftarDosenDibuat)
    {
        delete d;
    }

    return 0;
}
