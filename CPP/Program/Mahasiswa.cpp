#pragma once

#include <iostream>
#include <stdexcept>
#include <string>
#include "Manusia.cpp"
#include "Dosen.cpp"

using namespace std;

/**
 * Mahasiswa, anak langsung dari Manusia (hierarchical inheritance) sekaligus
 * induk dari Mapres (multilevel inheritance).
 *
 * Mahasiswa berelasi ASSOCIATION dengan Dosen lewat pointer dosenWali.
 * Relasi ini wajib (multiplicity 1): setiap Mahasiswa harus punya tepat satu
 * dosen wali sejak objeknya dibuat, sehingga dosenWali tidak pernah nullptr.
 * Relasi ini bukan composition karena Mahasiswa tidak membuat maupun memiliki
 * Dosen; Mahasiswa hanya menyimpan alamat Dosen yang sudah ada, dan satu
 * Dosen bisa dirujuk oleh banyak Mahasiswa.
 */
class Mahasiswa : public Manusia
{
protected:
    string nim;
    string jurusan;
    Dosen *dosenWali;

public:
    // Melempar invalid_argument jika dosenWali bernilai nullptr.
    Mahasiswa(string nik, string nama, int umur, string jenisKelamin,
              string nim, string jurusan, Dosen *dosenWali)
        : Manusia(nik, nama, umur, jenisKelamin)
    {
        if (dosenWali == nullptr)
        {
            throw invalid_argument("Dosen wali wajib diisi");
        }
        this->nim = nim;
        this->jurusan = jurusan;
        this->dosenWali = dosenWali;
    }

    string getNim() const
    {
        return nim;
    }

    Dosen *getDosenWali() const
    {
        return dosenWali;
    }

    // Mengganti dosen wali dengan Dosen lain. Dosen wali tidak boleh
    // dikosongkan, sehingga nullptr ditolak dengan invalid_argument.
    void setDosenWali(Dosen *dosenWali)
    {
        if (dosenWali == nullptr)
        {
            throw invalid_argument("Dosen wali wajib diisi");
        }
        this->dosenWali = dosenWali;
    }

    string getPeran() const override
    {
        return "Mahasiswa";
    }

    // Mencetak info dasar Manusia lalu NIM, jurusan, dan nama dosen wali.
    void tampilkanInfo() const override
    {
        Manusia::tampilkanInfo();
        cout << "  NIM           : " << nim << endl;
        cout << "  Jurusan       : " << jurusan << endl;
        cout << "  Dosen Wali    : " << dosenWali->getNama() << endl;
    }

    ~Mahasiswa() {}
};
