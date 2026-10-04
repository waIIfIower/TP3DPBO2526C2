#pragma once

#include <string>

using namespace std;

/**
 * Alamat sebuah universitas.
 *
 * Alamat adalah bagian dari Universitas lewat relasi COMPOSITION: objek
 * Alamat menjadi member object biasa (bukan pointer) di dalam Universitas,
 * sehingga masa hidupnya mengikuti Universitas yang memilikinya.
 */
class Alamat
{
private:
    string provinsi;
    string kota;
    string jalan;
    string kodePos;

public:
    Alamat(string provinsi, string kota, string jalan, string kodePos)
    {
        this->provinsi = provinsi;
        this->kota = kota;
        this->jalan = jalan;
        this->kodePos = kodePos;
    }

    // Mengembalikan alamat dalam satu baris: jalan, kota, provinsi, kode pos.
    string toString() const
    {
        return jalan + ", " + kota + ", " + provinsi + " " + kodePos;
    }

    ~Alamat() {}
};
