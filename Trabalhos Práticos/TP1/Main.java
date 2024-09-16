package TP1;
import java.io.*;

public class Main {
    private static Arquivo<Tarefa> arqTarefas;

    public static void main(String[] args) {

        Tarefa t1 = new Tarefa(-1, "Estudar", 20210901, 20210930, "Em andamento", 1);
        Tarefa t2 = new Tarefa(-1, "Trabalhar", 20210901, 20210930, "Em andamento", 2);
        Tarefa t3 = new Tarefa(-1, "Dormir", 20210901, 20210930, "Em andamento", 3);
        int id1, id2, id3;

        try {

            // Abre (cria) o arquivo de livros
            new File("tarefas.db").delete();  // apaga o arquivo anterior (apenas para testes)
            arqTarefas = new Arquivo<>("tarefas.db", Tarefa.class.getConstructor());

            // Insere as três tarefas
            id1 = arqTarefas.create(t1);
            t1.setId(id1);
            id2 = arqTarefas.create(t2);
            t2.setId(id2);
            id3 = arqTarefas.create(t3);
            t3.setId(id3);

            // Busca por dois livros
            System.out.println(arqTarefas.read(id3));
            System.out.println(arqTarefas.read(id1));

            // Altera um livro para um tamanho maior e exibe o resultado
            t2.setName("Richard Burton Matheson");
            arqTarefas.update(t2);
            System.out.println(arqTarefas.read(id2));

            // Altera um livro para um tamanho menor e exibe o resultado
            t1.setName("I. Asimov");
            arqTarefas.update(t1);
            System.out.println(arqTarefas.read(id1));

            // Excluir um livro e mostra que não existe mais
            arqTarefas.delete(id3);
            Tarefa l = arqTarefas.read(id3);
            if(l==null)
                System.out.println("Livro excluído");
            else
                System.out.println(l);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}