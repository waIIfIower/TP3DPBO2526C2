#pragma once

#include <iostream>
#include <string>
#include "Manusia.cpp"

using namespace std;

/**
 * Dosen, anak langsung dari Manusia (hierarchical inheritance).
 *
 * Dosen menjadi tujuan relasi ASSOCIATION dari Mahasiswa: setiap Mahasiswa
 * wajib menyimpan pointer ke satu Dosen sebagai dosen wali, sedangkan satu
 * Dosen dapat menjadi wali bagi banyak Mahasiswa. Dosen tetap objek yang
 * berdiri sendiri dan tidak dimiliki oleh Mahasiswa.
 */
class Dosen : public Manusia
{
private:
    string nip;
    string bidangKeahlian;

public:
    Dosen(string nik, string nama, int umur, string jenisKelamin,
          string nip, string bidangKeahlian)
        : Manusia(nik, nama, umur, jenisKelamin)
    {
        this->nip = nip;
        this->bidangKeahlian = bidangKeahlian;
    }

    string getNip() const
    {
        return nip;
    }

    string getPeran() const override
    {
        return "Dosen";
    }

    // Mencetak info dasar Manusia lalu NIP dan bidang keahlian.
    void tampilkanInfo() const override
    {
        Manusia::tampilkanInfo();
        cout << "  NIP           : " << nip << endl;
        cout << "  Bidang Keahlian: " << bidangKeahlian << endl;
    }

    ~Dosen() {}
};
