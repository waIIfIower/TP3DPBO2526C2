#pragma once

#include <iostream>
#include <string>
#include <vector>
#include "Alamat.cpp"
#include "Manusia.cpp"

using namespace std;

/**
 * Universitas, dengan dua relasi yang berbeda:
 *
 *  1. COMPOSITION terhadap Alamat: Alamat adalah member object biasa yang
 *     dibuat bersamaan dengan Universitas.
 *
 *  2. AGGREGATION terhadap Manusia: Universitas menyimpan daftar pointer
 *     Manusia* (bisa menunjuk Mahasiswa, Dosen, maupun Mapres berkat
 *     polimorfisme). Objek-objek tersebut dibuat di luar Universitas dan
 *     tetap ada walaupun Universitas dihapus.
 */
class Universitas
{
private:
    string nama;
    Alamat alamat;
    vector<Manusia *> daftarManusia;

public:
    // Alamat dibuat lewat member initializer list saat Universitas dibuat.
    Universitas(string nama, string provinsi, string kota, string jalan, string kodePos)
        : alamat(provinsi, kota, jalan, kodePos)
    {
        this->nama = nama;
    }

    // Menambahkan anggota ke universitas. Yang disimpan hanya pointer ke
    // objek yang sudah ada, bukan salinan.
    void tambahManusia(Manusia *manusia)
    {
        daftarManusia.push_back(manusia);
    }

    int getJumlahAnggota() const
    {
        return daftarManusia.size();
    }

    // Mencetak nama, alamat, jumlah anggota, dan informasi setiap anggota.
    void tampilkanUniversitas() const
    {
        cout << "=== " << nama << " ===" << endl;
        cout << "Alamat: " << alamat.toString() << endl;
        cout << "\nJumlah Anggota: " << daftarManusia.size() << endl;

        if (daftarManusia.empty())
        {
            cout << "  (belum ada anggota)" << endl;
            return;
        }

        for (const Manusia *anggota : daftarManusia)
        {
            // tampilkanInfo() yang dijalankan mengikuti tipe objek sebenarnya
            anggota->tampilkanInfo();
            cout << endl;
        }
    }

    ~Universitas() {}
};
