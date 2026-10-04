#pragma once

#include <iostream>
#include <string>
#include "Mahasiswa.cpp"

using namespace std;

/**
 * Mapres (Mahasiswa Berprestasi).
 *
 * Mapres mewarisi Mahasiswa yang sudah mewarisi Manusia. Rantai tiga tingkat
 * Manusia -> Mahasiswa -> Mapres inilah yang disebut MULTILEVEL INHERITANCE.
 * Karena turunan Mahasiswa, Mapres juga wajib memiliki dosen wali.
 */
class Mapres : public Mahasiswa
{
private:
    string namaPrestasi;
    string tingkatPrestasi;

public:
    Mapres(string nik, string nama, int umur, string jenisKelamin,
           string nim, string jurusan, Dosen *dosenWali,
           string namaPrestasi, string tingkatPrestasi)
        : Mahasiswa(nik, nama, umur, jenisKelamin, nim, jurusan, dosenWali)
    {
        this->namaPrestasi = namaPrestasi;
        this->tingkatPrestasi = tingkatPrestasi;
    }

    string getPeran() const override
    {
        return "Mahasiswa Berprestasi";
    }

    // Mencetak info Mahasiswa lalu nama dan tingkat prestasi.
    void tampilkanInfo() const override
    {
        Mahasiswa::tampilkanInfo();
        cout << "  Nama Prestasi    : " << namaPrestasi << endl;
        cout << "  Tingkat Prestasi : " << tingkatPrestasi << endl;
    }

    ~Mapres() {}
};
