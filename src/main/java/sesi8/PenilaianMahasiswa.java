package sesi8;

/**
 * Menghitung nilai akhir, huruf mutu, dan status kelulusan mahasiswa.
 *
 * Skala huruf: A >= 85, B >= 70, C >= 55, D >= 40, E < 40.
 * Lulus jika huruf minimal C dan kehadiran minimal 75%.
 */
public class PenilaianMahasiswa {

    public int hitungNilaiAkhir(int tugas, int uts, int uas) {
        validasiRentang(tugas, "Nilai");
        validasiRentang(uts, "Nilai");
        validasiRentang(uas, "Nilai");
        // bobot: tugas 30%, UTS 30%, UAS 40%
        return (int) Math.round((tugas * 30 + uts * 30 + uas * 40) / 100.0);
    }

    public char konversiHuruf(int nilai) {
        validasiRentang(nilai, "Nilai");
        if (nilai >= 85) return 'A';
        if (nilai >= 70) return 'B';
        if (nilai >= 55) return 'C';
        if (nilai >= 40) return 'D';
        return 'E';
    }

    public boolean isLulus(int nilaiAkhir, int persenKehadiran) {
        validasiRentang(persenKehadiran, "Kehadiran");
        char huruf = konversiHuruf(nilaiAkhir);   // ikut memvalidasi nilaiAkhir
        return persenKehadiran >= 75 && huruf <= 'C';   // 'A' < 'B' < 'C'
    }

    private void validasiRentang(int angka, String nama) {
        if (angka < 0 || angka > 100) {
            throw new IllegalArgumentException(nama + " harus di antara 0 dan 100");
        }
    }
}
