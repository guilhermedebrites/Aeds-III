import java.io.*;
import java.text.DecimalFormat;

public class Livro {
    protected int id;
    protected String title;
    protected String actor;
    protected float price;

    public Livro() {
        this.id = 0;
        this.title = "";
        this.actor = "";
        this.price = 0;
    }

    public Livro(int i, String t, String a, float p) {
        this.id = i;
        this.title = t;
        this.actor = a;
        this.price = p;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,##0.00");

        return "\nID: " + this.id +
                "\nTitle: " + this.title +
                "\nActor: " + this.actor +
                "\nPrice: R$ " + df.format(this.price);
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeUTF(this.title);
        dos.writeUTF(this.actor);
        dos.writeFloat(this.price);
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.id = dis.readInt();
        this.title = dis.readUTF();
        this.actor = dis.readUTF();
        this.price = dis.readFloat();
    }

}