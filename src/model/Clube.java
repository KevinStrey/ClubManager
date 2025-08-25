package model;

import java.util.ArrayList;
import java.util.List;

public class Clube {
    private String nome;
    private double valorMensalidade;
    private List<Membro> membros = new ArrayList<>();
    private List<Funcionario> funcionarios = new ArrayList<>();

    public Clube(String nome, double valorMensalidade) {
        this.nome = nome;
        this.valorMensalidade = valorMensalidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValorMensalidade() {
        return valorMensalidade;
    }

    public void setValorMensalidade(double valorMensalidade) {
        this.valorMensalidade = valorMensalidade;
    }

    public int getQtdMembros() {
        return membros.size();
    }

    public List<Membro> getMembros() {
        return new ArrayList<>(membros);
    }

    public void addMembro(Membro membro) {
        if (membro != null && !membros.contains(membro)) {
            membros.add(membro);
            membro.setClube(this);
        }
    }

    public void removeMembro(Membro membro) {
        if (membro != null && membros.remove(membro)) {
            membro.setClube(null);
        }
    }

    public List<Funcionario> getFuncionarios() {
        return new ArrayList<>(funcionarios);
    }

    public void addFuncionario(Funcionario funcionario) {
        if (funcionario != null && !funcionarios.contains(funcionario)) {
            funcionarios.add(funcionario);
            funcionario.setClube(this);
        }
    }

    public void removeFuncionario(Funcionario funcionario) {
        if (funcionario != null && funcionarios.remove(funcionario)) {
            funcionario.setClube(null);
        }
    }
}