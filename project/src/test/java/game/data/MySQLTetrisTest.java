//package game.data;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//public class MySQLTetrisTest {
//
//    private MySQLTetris repo;
//
//    @Before
//    public void initiate() {
//        repo = Repositories.getInstance().mySQLTetris();
//    }
//
//    @Test
//    public void testGetFaction() {
//        List<String> factions = repo.getFactions();
//        assertEquals(4, factions.size());
//    }
//
//}