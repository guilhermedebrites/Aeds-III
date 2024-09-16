package TP1;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;

public class Arquivo<T extends Registro> {
    private static int header = 4;
    private RandomAccessFile file;
    private String fileName = "";
    Constructor<T> constructor;


    // CONSTRUTOR DA CLASSE ARQUIVO
    public Arquivo(String fn, Constructor<T> constructor) throws Exception {
        this.fileName = fn;
        this.constructor = constructor;
        file = new RandomAccessFile(fileName, "rw");
        if (file.length() < header) {
            file.seek(0);
            file.writeInt(0);
        }
    }

    // MÉTODO PARA REAPROVEITAMENTO DE ESPAÇO
    public boolean reaproveitamento(byte[] ba) throws Exception {
        boolean controle = false;
        long endereco;
        byte lapide;
        short size;
        short sizeObject = (short) ba.length;

        file.seek(header);
        while (file.getFilePointer() < file.length() && !controle) {
            endereco = file.getFilePointer();
            lapide = file.readByte();
            size = file.readShort();
            if (lapide == '*' && sizeObject <= size) {
                file.write(ba);
                file.seek(endereco);
                file.write(' ');
                controle = true;
            }
            else {
                file.skipBytes(size);
            }
        }

        return controle;
    }

    // MÉTODO PARA CRIAÇÃO DE REGISTRO
    public int create(T object) throws Exception {
        file.seek(0);
        int lastId = file.readInt();
        lastId++;
        object.setId(lastId);
        file.seek(0);
        file.writeInt(lastId);

        byte[] ba = object.toByteArray();
        short sizeObject = (short) ba.length;

        boolean controle = reaproveitamento(ba);
        if (!controle) {
            file.seek(file.length());
            file.write(' '); // Lápide
            file.writeShort(sizeObject);
            file.write(ba);
        }

        return object.getId();
    }

    // MÉTODO PARA LEITURA DE REGISTRO
    public T read(int id) throws Exception {
        T object = constructor.newInstance();
        byte[] ba;
        short size;
        byte deleted;

        file.seek(header);
        while (file.getFilePointer() < file.length()) {
            deleted = file.readByte(); // Lápide
            size = file.readShort();
            if (deleted == ' ') {
                ba = new byte[size];
                file.read(ba);
                object.fromByteArray(ba);
                if (object.getId() == id) {
                    return object;
                }
            } else {
                file.skipBytes(size);
            }
        }
        return null;
    }

    // MÉTODO PARA DELEÇÃO DE REGISTRO
    public boolean delete(int id) throws Exception {
        T object = constructor.newInstance();
        byte deleted;
        short size;
        byte[] ba;
        long position;

        file.seek(header);
        while (file.getFilePointer() < file.length()) {
            position = file.getFilePointer();
            deleted = file.readByte(); // Lápide
            size = file.readShort();
            if (deleted == ' ') {
                ba = new byte[size];
                file.read(ba);
                object.fromByteArray(ba);
                if (object.getId() == id) {
                    file.seek(position);
                    file.write('*');
                    return true;
                }
            } else {
                file.skipBytes(size);
            }
        }
        return false;
    }

    // MÉTODO PARA ATUALIZAÇÃO DE REGISTRO
    public boolean update(T objAlterado) throws Exception {
        T object = constructor.newInstance();
        byte[] ba;
        short tam;
        byte lapide;
        long endereco;

        file.seek(header);
        while (file.getFilePointer() < file.length()) {

            endereco = file.getFilePointer();
            lapide = file.readByte();
            tam = file.readShort();
            if (lapide == ' ') {
                ba = new byte[tam];
                file.read(ba);
                object.fromByteArray(ba);
                if (object.getId() == objAlterado.getId()) {
                    byte[] ba2 = objAlterado.toByteArray();
                    short tam2 = (short) ba2.length;
                    if (tam2 <= tam) {
                        file.seek(endereco + 1 + 2);
                        file.write(ba2);
                    } else {
                        boolean controle = reaproveitamento(ba2);
                        if (!controle) {
                            file.seek(file.length());
                            file.write(' '); // Lápide
                            file.writeShort(tam2);
                            file.write(ba2);
                        }
                        file.seek(endereco); ///
                        file.write('*'); ///
                    }
                    return true;
                }
            } else {
                file.skipBytes(tam);
            }
        }
        return false;
    }

    // MÉTODO PARA LISTAGEM DE REGISTROS
    public void listar() throws Exception {
        T object = constructor.newInstance();
        byte lapide;
        short tamanho;
        byte[] ba;

        file.seek(header);
        while (file.getFilePointer() < file.length()) {
            lapide = file.readByte();
            tamanho = file.readShort();
            if(lapide == ' ') {
                ba = new byte[tamanho];
                file.read(ba);
                object.fromByteArray(ba);
                System.out.println(object);
                System.out.println("----------------------");
            }
            else {
                file.skipBytes(tamanho);
            }
        }
    }

    // MÉTODO PARA LIMPAR O ARQUIVO
    public void clear() throws Exception {
        file.setLength(header);
        file.seek(0);
        file.writeInt(0);
    }
}