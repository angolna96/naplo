package sample;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BeolvasTest {

    private List<Integer> JoJegyek = Arrays.asList(5, 5, 5, 5);
    private List<Hallgato> HallgatoTest;
    private Beolvas BTest;
    private Hallgato hibas, jo, ures;

    @BeforeEach
    void setUp(){
        HallgatoTest = Arrays.asList(
                new Hallgato("Jo", "J1", Arrays.asList(5, 5, 5, 5), Arrays.asList(5, 5, 5),5),
                new Hallgato("Ures", "U2", new ArrayList<>(), new ArrayList<>(), 0),
                new Hallgato("Hibas", "H3", Arrays.asList(1, 2, 10), Arrays.asList(2,3), 3)
        );
        jo = HallgatoTest.get(0);
        ures = HallgatoTest.get(1);
        hibas = HallgatoTest.get(2);
        BTest = new Beolvas(HallgatoTest);
    }

    @Test
    void getAll_Test() {
        assertEquals(HallgatoTest, BTest.getAll());
    }

    @Test
    void hallgatok_neve_Test() {
        assertEquals(Arrays.asList("Hibas", "Jo", "Ures"), BTest.hallgatok_neve());
    }

    @Test
    void hallgato_kivalaszt_Test() {
        assertEquals(new Hallgato("Hibas", "H3", Arrays.asList(1, 2, 10), Arrays.asList(2,3), 3), BTest.hallgato_kivalaszt("Hibas"));
        assertEquals(new Hallgato("Jo", "J1", Arrays.asList(5, 5, 5, 5), Arrays.asList(5, 5, 5),5), BTest.hallgato_kivalaszt("Jo"));
        assertEquals(new Hallgato("Ures", "U2", new ArrayList<>(), new ArrayList<>(), 0), BTest.hallgato_kivalaszt("Ures"));
    }

    @Test
    void adott_hallgato_djegye_Test() {
        assertEquals(Arrays.asList(1, 2, 10), BTest.adott_hallgato_djegye("Hibas"));
        assertEquals(Arrays.asList(5, 5, 5, 5), BTest.adott_hallgato_djegye("Jo"));
        assertEquals(new ArrayList<>(), BTest.adott_hallgato_djegye("Ures"));
    }

    @Test
    void adott_hallgato_tzjegye_Test() {
        assertEquals(Arrays.asList(2,3), BTest.adott_hallgato_tzjegye("Hibas"));
        assertEquals(Arrays.asList(5, 5, 5), BTest.adott_hallgato_tzjegye("Jo"));
        assertEquals(new ArrayList<>(), BTest.adott_hallgato_tzjegye("Ures"));
    }

    @Test
    void adott_hallgato_kjegye_Test() {
        assertEquals(3, BTest.adott_hallgato_kjegye("Hibas"));
        assertEquals(5, BTest.adott_hallgato_kjegye("Jo"));
        assertEquals(0, BTest.adott_hallgato_kjegye("Ures"));
    }

    @Test
    void atlag_szamol_Test() {
        assertEquals(-1, Beolvas.atlag_szamol(hibas.getD(), hibas.getTz(), hibas.getK()));
        assertEquals(0, Beolvas.atlag_szamol(ures.getD(), ures.getTz(), ures.getK()));
        assertEquals(5, Beolvas.atlag_szamol(jo.getD(), jo.getTz(), jo.getK()));
    }

    @AfterEach
    void tearDown(){
        HallgatoTest = null;
    }
}