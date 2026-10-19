package sesi8;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PenilaianMahasiswa")
class PenilaianMahasiswaTest {

    private PenilaianMahasiswa penilaian;

    @BeforeEach
    void setUp() {
        penilaian = new PenilaianMahasiswa();   // objek baru tiap test (Independent)
    }

    // ---------- hitungNilaiAkhir ----------

    @Test
    @DisplayName("Nilai akhir = 30% tugas + 30% UTS + 40% UAS")
    void nilaiAkhirSesuaiBobot() {
        int hasil = penilaian.hitungNilaiAkhir(80, 70, 90);
        assertEquals(81, hasil);
    }

    @Test
    @DisplayName("Nilai akhir 78,5 dibulatkan ke 79")
    void nilaiAkhirDibulatkanKeAtas() {
        int hasil = penilaian.hitungNilaiAkhir(75, 80, 80);
        assertEquals(79, hasil);
    }

    @Test
    @DisplayName("Komponen di batas 0 dan 100 menghasilkan nilai akhir 0 dan 100")
    void nilaiAkhirDiBatasRentang() {
        assertAll(
            () -> assertEquals(0, penilaian.hitungNilaiAkhir(0, 0, 0)),
            () -> assertEquals(100, penilaian.hitungNilaiAkhir(100, 100, 100))
        );
    }

    @Test
    @DisplayName("Komponen nilai 101 ditolak")
    void komponenLebihDari100Ditolak() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> penilaian.hitungNilaiAkhir(80, 70, 101));
        assertEquals("Nilai harus di antara 0 dan 100", ex.getMessage());
    }

    // ---------- konversiHuruf ----------

    @ParameterizedTest(name = "nilai {0} -> {1}")
    @DisplayName("Huruf mutu di setiap nilai batas (BVA)")
    @CsvSource({
        "0, E",  "39, E",
        "40, D", "54, D",
        "55, C", "69, C",
        "70, B", "84, B",
        "85, A", "100, A"
    })
    void hurufMutuDiNilaiBatas(int nilai, char diharapkan) {
        assertEquals(diharapkan, penilaian.konversiHuruf(nilai));
    }

    @Test
    @DisplayName("Nilai negatif ditolak")
    void nilaiNegatifDitolak() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> penilaian.konversiHuruf(-1));
        assertEquals("Nilai harus di antara 0 dan 100", ex.getMessage());
    }

    @Test
    @DisplayName("Nilai di atas 100 ditolak")
    void nilaiDiAtas100Ditolak() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> penilaian.konversiHuruf(101));
        assertEquals("Nilai harus di antara 0 dan 100", ex.getMessage());
    }

    // ---------- isLulus ----------

    @ParameterizedTest(name = "nilai {0}, kehadiran {1}% -> lulus: {2}")
    @DisplayName("Kelulusan di batas nilai 55 dan kehadiran 75% (BVA)")
    @CsvSource({
        "55, 75, true",
        "54, 75, false",
        "55, 74, false",
        "100, 100, true"
    })
    void kelulusanDiNilaiBatas(int nilaiAkhir, int kehadiran, boolean diharapkan) {
        assertEquals(diharapkan, penilaian.isLulus(nilaiAkhir, kehadiran));
    }

    @Test
    @DisplayName("Kehadiran di atas 100% ditolak")
    void kehadiranDiAtas100Ditolak() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> penilaian.isLulus(80, 101));
        assertEquals("Kehadiran harus di antara 0 dan 100", ex.getMessage());
    }

    @Test
    @DisplayName("Nilai tidak valid tetap ditolak walau kehadiran kurang")
    void nilaiTidakValidDitolakWalauKehadiranKurang() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> penilaian.isLulus(150, 50));
        assertEquals("Nilai harus di antara 0 dan 100", ex.getMessage());
    }
}
