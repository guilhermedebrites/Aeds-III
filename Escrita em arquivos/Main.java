import java.io.*;

public class Main {

    public static void main(String[] args) {
        AcessoAleatorioAoArquivo();
        AcessoSequencialAoArquivo();
    }

    private static void AcessoAleatorioAoArquivo() {
        Livro l1 = new Livro(1, "Eu, Robô", "Isaac Asimov", 14.9F);
        Livro l2 = new Livro(2, "Eu Sou A Lenda", "Richard Matheson", 21.99F);

        RandomAccessFile arq;
        byte[] ba;

        try {
            arq = new RandomAccessFile("Escrita em arquivos\\dados\\livros.db", "rw");

            long p1 = arq.getFilePointer();
            ba = l1.toByteArray();
            arq.writeInt(ba.length);
            arq.write(ba);

            long p2 = arq.getFilePointer();
            ba = l2.toByteArray();
            arq.writeInt(ba.length);
            arq.write(ba);

            Livro l3 = new Livro();
            Livro l4 = new Livro();
            int tam;

            arq.seek(0);

            arq.seek(p2);
            tam = arq.readInt();
            ba = new byte[tam];
            arq.read(ba);
            l3.fromByteArray(ba);

            arq.seek(p1);
            tam = arq.readInt();
            ba = new byte[tam];
            arq.read(ba);
            l4.fromByteArray(ba);

            System.out.println(l3);
            System.out.println(l4);

            arq.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void AcessoSequencialAoArquivo() {
        Livro l1 = new Livro(1, "Eu, Robô", "Isaac Asimov", 14.9F);
        Livro l2 = new Livro(2, "Eu Sou A Lenda", "Richard Matheson", 21.99F);

        FileOutputStream arq;
        DataOutputStream dos;

        FileInputStream arq2;
        DataInputStream dis;

        byte[] ba;

        try {
            arq = new FileOutputStream("Escrita em arquivos\\dados\\livros.db");
            dos = new DataOutputStream(arq);

            ba = l1.toByteArray();
            dos.writeInt(ba.length);
            arq.write(ba);

            ba = l2.toByteArray();
            dos.writeInt(ba.length);
            arq.write(ba);

            arq.close();

            Livro l3 = new Livro();
            Livro l4 = new Livro();
            int tam;

            arq2 = new FileInputStream("Escrita em arquivos\\dados\\livros.db");
            dis = new DataInputStream(arq2);

            tam = dis.readInt();
            ba = new byte[tam];
            dis.read(ba);
            l3.fromByteArray(ba);

            tam = dis.readInt();
            ba = new byte[tam];
            dis.read(ba);
            l4.fromByteArray(ba);

            System.out.println(l3);
            System.out.println(l4);

        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
