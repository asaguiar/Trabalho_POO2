package presenter;

import DAO.SQLServer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import model.Cidade;
import padrao.state.manterAluno.State;
import view.ManterAlunoView;

public class ManterAlunoPresenter {

    private State manterAlunoState = null;
    private ManterAlunoView manterAluno = null;
    SQLServer bancoDados;

    public ManterAlunoPresenter(SQLServer banco) {

        bancoDados = banco;
        manterAluno = new ManterAlunoView();

        manterAluno.getComboUF().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                configurarCidades();
            }
        });

        manterAluno.getBtnCancelar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                cancelar();
            }
        });

        manterAluno.getBtnConfirmar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                confirmar();
            }
        });

    }

    public void cancelar() {
        manterAlunoState.btnCancelarEditar();
    }

    public void confirmar() {
        manterAlunoState.btnConfirmarFechar();
    }

    public State getManterAlunoState() {
        return manterAlunoState;
    }

    public void setManterAlunoState(State manterAlunoState) {
        this.manterAlunoState = manterAlunoState;
    }

    public void configurarCidades() {
        //Peguei a UF do Estado Selecionado
        String uf = manterAluno.getComboUF().getModel().getSelectedItem().toString();
        manterAluno.getComboCidade().removeAllItems();

        List<Cidade> lista = new ArrayList();
        try {
            lista = bancoDados.recuperaCidadesEstado(uf);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de Dados", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        for (Iterator<Cidade> it = lista.iterator(); it.hasNext();) {
            Cidade cidade = it.next();
            manterAluno.getComboCidade().addItem(cidade.getNomeCidade());
        }
    }

    public SQLServer getBancoDados() {
        return bancoDados;
    }

    public ManterAlunoView getManterAluno() {
        return manterAluno;
    }

}
