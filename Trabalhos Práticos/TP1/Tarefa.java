package TP1;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Tarefa implements Registro {
    private int id;
    private String name;
    private int createAt;
    private int conclusionAt;
    private String status;
    private int Prioridade;

    public Tarefa() {
        this(0, "", 0, 0, "", 0);
    }

    public Tarefa(int id, String name, int createAt, int conclusionAt, String status, int Prioridade) {
        this.id = id;
        this.name = name;
        this.createAt = createAt;
        this.conclusionAt = conclusionAt;
        this.status = status;
        this.Prioridade = Prioridade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCreateAt() {
        return createAt;
    }

    public void setCreateAt(int createAt) {
        this.createAt = createAt;
    }

    public int getConclusionAt() {
        return conclusionAt;
    }

    public void setConclusionAt(int conclusionAt) {
        this.conclusionAt = conclusionAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPrioridade() {
        return Prioridade;
    }

    public void setPrioridade(int Prioridade) {
        this.Prioridade = Prioridade;
    }

    public byte[] toByteArray() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(getId());
        dos.writeUTF(getName());
        dos.writeInt(getCreateAt());
        dos.writeInt(getConclusionAt());
        dos.writeUTF(getStatus());
        dos.writeInt(getPrioridade());

        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        setId(dis.readInt());
        setName(dis.readUTF());
        setCreateAt(dis.readInt());
        setConclusionAt(dis.readInt());
        setStatus(dis.readUTF());
        setPrioridade(dis.readInt());
    }

    public String toString() {
        return "ID: " + getId() + " | Nome: " + getName() + " | Data de Criação: " + getCreateAt() + " | Data de Conclusão: " + getConclusionAt() + " | Status: " + getStatus() + " | Prioridade: " + getPrioridade();
    }
}