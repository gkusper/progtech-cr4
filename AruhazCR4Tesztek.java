import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;
import org.aruhaz.*;

class AruhazCR4Tesztek {
    static Aruhaz target;
    static Idoszak normal;
    @BeforeAll
    public static void initAruhaz() {
        target = new Aruhaz();
        normal = new Idoszak("Normál");
        normal.setEgysegAr(Termek.ALMA, 500.0);
        normal.setEgysegAr(Termek.BANAN, 450.0);
        normal.setKedvezmeny(Termek.ALMA, 5.0, 0.1);
        normal.setKedvezmeny(Termek.ALMA, 20.0, 0.15);
        normal.setKedvezmeny(Termek.BANAN, 2.0, 0.1);
        target.addIdoszak(normal);
    }
    @Test
    void teszt_cr4_pelda1_EgyMAX() {
        Kosar kosar = new Kosar(List.of(new Tetel(Termek.ALMA, 2.0)));
        ArInfo ar = target.getKosarAr(kosar, normal, List.of("A5-MAX10"));
        assertEquals(950.0, ar.getAr(), 0.001);
        assertEquals(List.of(), ar.getFelNemHasznaltKuponok());
    }
    @Test
    void teszt_cr4_pelda2_KetA5esMAX10() {
        Kosar kosar = new Kosar(List.of(new Tetel(Termek.ALMA, 1.0)));
        ArInfo ar = target.getKosarAr(kosar, normal, List.of("A5", "A5", "A5-MAX10"));
        assertEquals(450.0, ar.getAr(), 0.001);
        assertEquals(List.of("A5"), ar.getFelNemHasznaltKuponok());
    }
    @Test
    void teszt_cr4_pelda3_MAX15esMAX10() {
        Kosar kosar = new Kosar(List.of(new Tetel(Termek.ALMA, 1.0)));
        ArInfo ar = target.getKosarAr(kosar, normal, List.of("A5", "A5-MAX15", "A5-MAX10"));
        assertEquals(450.0, ar.getAr(), 0.001);
        assertEquals(List.of("A5-MAX15"), ar.getFelNemHasznaltKuponok());
    }
    @Test
    void teszt_cr4_pelda4_MAX15esMAX10() {
        Kosar kosar = new Kosar(List.of(new Tetel(Termek.ALMA, 1.0)));
        ArInfo ar = target.getKosarAr(kosar, normal, List.of("A5-MAX15", "A5-MAX10"));
        assertEquals(450.0, ar.getAr(), 0.001);
        assertEquals(List.of(), ar.getFelNemHasznaltKuponok());
    }
    @Test
    void teszt_cr4_pelda5_MAX15_es_A5() {
        Kosar kosar = new Kosar(List.of(new Tetel(Termek.ALMA, 1.0)));
        ArInfo ar = target.getKosarAr(kosar, normal, List.of("A5", "A5-MAX15"));
        assertEquals(450.0, ar.getAr(), 0.001);
        assertEquals(List.of(), ar.getFelNemHasznaltKuponok());
    }
    @Test
    void teszt_cr4_pelda6_Akcio_erosebb_mint_kupon() {
        Idoszak tavaszi = new Idoszak("Tavaszi");
        tavaszi.setEgysegAr(Termek.ALMA, 600.0);
        tavaszi.setKedvezmeny(Termek.ALMA, 2.0, 0.15);
        tavaszi.setKedvezmeny(Termek.ALMA, 5.0, 0.20);
        target.addIdoszak(tavaszi);
        Kosar kosar = new Kosar(List.of(new Tetel(Termek.ALMA, 2.0)));
        ArInfo ar = target.getKosarAr(kosar, tavaszi, List.of("A5", "A5", "A5-MAX15"));
        assertEquals(1020.0, ar.getAr(), 0.001);
        assertEquals(List.of("A5", "A5", "A5-MAX15"), ar.getFelNemHasznaltKuponok());
    }
    @Test
    void teszt_cr4_pelda7_MAX15plus2A5() {
        Kosar kosar = new Kosar(List.of(new Tetel(Termek.ALMA, 1.0)));
        ArInfo ar = target.getKosarAr(kosar, normal, List.of("A5-MAX15", "A5", "A5"));
        assertEquals(425.0, ar.getAr(), 0.001);
        assertEquals(List.of(), ar.getFelNemHasznaltKuponok());
    }
    @Test
    void teszt_cr4_pelda8_A5_MAX15_A5() {
        Kosar kosar = new Kosar(List.of(new Tetel(Termek.ALMA, 1.0)));
        ArInfo ar = target.getKosarAr(kosar, normal, List.of("A5", "A5-MAX15", "A5"));
        assertEquals(425.0, ar.getAr(), 0.001);
        assertEquals(List.of(), ar.getFelNemHasznaltKuponok());
    }
    @Test
    void teszt_cr4_pelda9_2A5_MAX15() {
        Kosar kosar = new Kosar(List.of(new Tetel(Termek.ALMA, 1.0)));
        ArInfo ar = target.getKosarAr(kosar, normal, List.of("A5", "A5", "A5-MAX15"));
        assertEquals(425.0, ar.getAr(), 0.001);
        assertEquals(List.of(), ar.getFelNemHasznaltKuponok());
    }
}
