
package ServerSocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/echo2")
public class EndPointVideo {


    @OnOpen
    public void onOpen(Session session) {

        
        session.getUserProperties().put("comp", "0");
        
        
        /*System.out.println("ce client "+session.getId()+" vient de se connecter");
        session.setMaxBinaryMessageBufferSize(1024 * 512);*/
            
        
    }

    @OnMessage
    public void processVideo(byte[] imageData, Session session) {
        try {
            if(session.getUserProperties().get("comp")== "0") {
                session.getBasicRemote().sendText(session.getId());
                session.getUserProperties().put("comp", "1");
            } 
        } catch (IOException ex) {
            Logger.getLogger(EndPointVideo.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("INsite process Video" + imageData);
        try {
            //Wrap a byte array into a buffer
            ByteBuffer buf = ByteBuffer.wrap(imageData);
            //imageBuffers.add(buf);

            Set set;
            set = session.getOpenSessions();
            System.out.println(set.size() + " this is the number of opened sessions");
            Iterator it = set.iterator();
            while (it.hasNext()) {
                Session s = (Session) it.next();
                System.out.println("ana ghadi nsift");
                if(s!=session){
                s.getBasicRemote().sendBinary(buf);
                System.out.println("ana sifat " + buf.toString());
                }
            }

        } catch (IOException ex) {
        } catch (Throwable ioe) {
            System.out.println("Error sending message " + ioe.getMessage());
        }
    }

    @OnClose
    public void whenClosing(Session session) {
        System.out.println("Goodbye !");
    }

}

