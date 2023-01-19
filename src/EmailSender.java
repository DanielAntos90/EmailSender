import java.net.*;
import java.io.*;

public class EmailSender {
    private Socket s;
    /*
     * Constructor opens Socket to host/port. If the Socket throws an exception during opening,
     * the exception is not handled in the constructor.
     */
    public EmailSender(String host, int port) throws UnknownHostException, IOException {
        s = new Socket(host,port);
    }
    /*
     * sends email from an email address to an email address with some subject and text.
     * If the Socket throws an exception during sending, the exception is not handled by this method.
     */
    public void send(String from, String to, String subject, String text) throws IOException {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), "utf-8"));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), "utf-8"));

            send(in, out, String.format("MAIL FROM: <%s>",from));
            send(in, out, String.format("RCPT TO: <%s>",to));
            send(in, out, "DATA");
            send(out, String.format("Subject: ",subject));
            send(out, String.format("From: <%s>",from));
            send(out, "\n");
            send(out, text);
            send(in, out, "QUIT");
            this.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * sends QUIT and closes the socket
     */
    public void close() throws IOException {
        s.close();
    }

    public void send(BufferedReader in, BufferedWriter out, String s) {
        try {
            out.write(s + "\n");
            out.flush();
            System.out.println(s);
            s = in.readLine();
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(BufferedWriter out, String s) {
        try {
            out.write(s + "\n");
            out.flush();
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
