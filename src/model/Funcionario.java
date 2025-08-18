package model;

public class Funcionario extends Pessoa {
    private String ctps;
    private double salario;

    public Funcionario(String cpf, String nome, String endereco, String ctps, double salario) {
        super(cpf, nome, endereco);
        this.ctps = ctps;
        this.salario = salario;
    }

    public String getCtps() {
        return ctps;
    }

    public void setCtps(String ctps) {
        this.ctps = ctps;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
}