package test;

        import com.sun.security.ntlm.Client;
        import jkam.EchoClient;
        import jkam.GreetClient;
        import org.junit.After;
        import org.junit.Before;
        import org.junit.jupiter.api.BeforeAll;
        import org.junit.Test;

        import jkam.GreetClient;

        import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyTests {

    EchoClient client;

    @Before
    public void setup(){
        client = new EchoClient();
        try {
            client.startConnection("127.0.0.1", 5555);
        } catch( Exception e) {
            System.out.println("boom: "+ e );
        }
    }

    @After
    public void tearDown() throws Exception {
        client.stopConnection();

    }

    @Test
    public void givenGreetingClient_whenServerRespondsWhenStarted_thenCorrect() throws Exception {
//        GreetClient client = new GreetClient();
//        client.startConnection("127.0.0.1", 5555);
        String response = client.sendMessage("hello server");
        assertEquals("hello client", response);
    }

    @Test
    public void givenClient_whenServerEchosMessage_thenCorrect() throws Exception {
        EchoClient client2 = new EchoClient();
        client2.startConnection("127.0.0.1", 5555);

        String  resp1 = "",
                resp2 = "",
                resp3 = "",
                resp4 = "",
                resp12 = "",
                resp22 = "",
                resp32 = "",
                resp42 = "";

        try {
            resp1 = client.sendMessage("hello");
            resp2 = client.sendMessage("world");
            resp3 = client.sendMessage("!");
            resp4 = client.sendMessage(".");

            resp12 = client2.sendMessage("hello again");
            resp22 = client2.sendMessage("world again");
            resp32 = client2.sendMessage("!!");
            resp42 = client2.sendMessage(".");

        } catch(Exception e) {
            System.out.println("You messed up. Is server running? " + e);
            client2.stopConnection();
        }

        assertEquals("hello", resp1);
        assertEquals("world", resp2);
        assertEquals("!", resp3);
        assertEquals("bye", resp4);
        assertEquals("hello again", resp1);
        assertEquals("world again", resp2);
        assertEquals("!!", resp3);
        assertEquals("bye", resp4);

        client2.stopConnection();
    }

    @Test
    public void givenClient_whenServerEchosMessageAndStillListening_thenCorrect() throws Exception {
        EchoClient client2 = new EchoClient();
        client2.startConnection("127.0.0.1", 5555);

        String  resp1 = "",
                resp2 = "",
                resp3 = "",
                resp4 = "";
        try {
            resp1 = client2.sendMessage("hello again");
            resp2 = client2.sendMessage("world again");
            resp3 = client2.sendMessage("!!");
            resp4 = client2.sendMessage(".");

        } catch(Exception e) {
            System.out.println("You messed up. Is server running? " + e);
            client2.stopConnection();
        }

        assertEquals("hello again", resp1);
        assertEquals("world again", resp2);
        assertEquals("!!", resp3);
        assertEquals("bye", resp4);

        client2.stopConnection();
    }


}
