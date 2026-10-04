#pragma once

#include <iostream>
#include <string>

using namespace std;

/**
 * Kelas dasar yang merepresentasikan seorang manusia.
 *
 * Kelas ini adalah akar seluruh pewarisan pada program:
 *
 *              Manusia
 *             /        \
 *        Mahasiswa     Dosen        (hierarchical inheritance)
 *           |
 *         Mapres                    (multilevel inheritance)
 *
 * Mahasiswa dan Dosen sama-sama mewarisi Manusia secara langsung,
 * sedangkan Mapres mewarisi Mahasiswa yang sudah mewarisi Manusia.
 * Gabungan kedua pola tersebut disebut hybrid inheritance.
 */
class Manusia
{
protected:
    // protected agar dapat diakses langsung oleh subclass
    string nik;
    string nama;
    int umur;
    string jenisKelamin;

public:
    Manusia(string nik, string nama, int umur, string jenisKelamin)
    {
        this->nik = nik;
        this->nama = nama;
        this->umur = umur;
        this->jenisKelamin = jenisKelamin;
    }

    string getNama() const
    {
        return nama;
    }

    // Mengembalikan label peran. Subclass meng-override method ini sehingga
    // pemanggilan yang sama menghasilkan label sesuai tipe objek sebenarnya
    // (polimorfisme).
    virtual string getPeran() const
    {
        return "Manusia";
    }

    // Mencetak peran dan atribut dasar manusia, satu atribut per baris.
    // Subclass memanggil method ini lalu menambahkan atribut miliknya sendiri.
    virtual void tampilkanInfo() const
    {
        cout << getPeran() << endl;
        cout << "  NIK           : " << nik << endl;
        cout << "  Nama          : " << nama << endl;
        cout << "  Umur          : " << umur << endl;
        cout << "  Jenis Kelamin : " << jenisKelamin << endl;
    }

    // Destructor virtual agar objek turunan yang dihapus lewat pointer
    // Manusia* tetap dibersihkan dengan benar.
    virtual ~Manusia() {}
};
