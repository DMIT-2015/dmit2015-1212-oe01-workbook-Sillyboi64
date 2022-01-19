package ca.nait.dmit.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlbertaCovid19CaseManagerTest {

/*    private static AlbertaCovid19CaseManager caseManager;

    @BeforeAll
    static void beforeAll() throws IOException {
        caseManager = new AlbertaCovid19CaseManager();
    }*/

    AlbertaCovid19CaseManager caseManager;

    @BeforeEach
    void setup() throws IOException {
        //caseManager = new AlbertaCovid19CaseManager();
        caseManager = AlbertaCovid19CaseManager.getInstance();
    }

    @Test
    void getAlbertaCovid19CaseList() {
        assertEquals(455660, caseManager.getAlbertaCovid19CaseList().size());
    }

    @Test
    void getAlbertaCovid19CaseList2() {
        assertEquals(455660, caseManager.getAlbertaCovid19CaseList().size());
    }

    @Test
    void getAlbertaCovid19CaseList3() {
        assertEquals(455660, caseManager.getAlbertaCovid19CaseList().size());
    }

    @Test
    void distinctAhsZone() {
        //caseManager.findDistinctAhsZone().forEach(System.out::println);
        List<String> ahsZoneList = caseManager.findDistinctAhsZone();

        //ahsZoneList.stream().forEach(System.out::println); method expression works the same a below
        ahsZoneList.stream().forEach(item -> System.out.println(item));//lambda expression

        assertEquals(6, caseManager.findDistinctAhsZone().size());
    }

    @Test
    void countActiveCases() {
        assertEquals(70_223, caseManager.findActiveCaseCount());
    }
}