package model;

public class Membro extends Pessoa {
    private int matricula;
    private Plano plano;

    public Membro(String cpf, String nome, String endereco, int matricula, Plano plano) {
        super(cpf, nome, endereco);
        this.matricula = matricula;
        this.plano = plano;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }
}