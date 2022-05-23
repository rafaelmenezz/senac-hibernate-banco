
package br.com.senac.dao;

import br.com.senac.entidade.Endereco;
import br.com.senac.entidade.PessoaJuridica;
import br.com.senac.util.GeradorUtil;

import static br.com.senac.util.GeradorUtil.*;
import org.hibernate.Session;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;


public class PessoaJuridicaDaoImplTest {

    private PessoaJuridica pessoaJuridica;
    private PessoaJuridicaDao pessoaJuridicaDao;
    private Session sessao;

    public PessoaJuridicaDaoImplTest() {
        pessoaJuridicaDao = new PessoaJuridicaDaoImpl();
    }

    @Test
    public void testSalvar() {
        System.out.println("salvar");
        pessoaJuridica = new PessoaJuridica("Empresa " + gerarNome(),
                gerarLogin() + "gmail.com", gerarCnpj(), gerarNumero(6));
        Endereco endereco = gerarEndereco();
        pessoaJuridica.setEndereco(endereco);
        endereco.setCliente(pessoaJuridica);
        
        sessao = HibernateUtil.abrirConexao();
        pessoaJuridicaDao.salvarOuAlterar(pessoaJuridica, sessao);
        sessao.close();
        assertNotNull(pessoaJuridica.getId());
    }

    @Test
    public void testAlterar(){
        System.out.println("Alterar");

        buscarPessoaJuridicaBD();

        pessoaJuridica.setNome(gerarNome());
        pessoaJuridica.setCnpj(GeradorUtil.gerarCnpj()+"A");
        pessoaJuridica.setEmail("alterado@test.com");
        pessoaJuridica.setInscricaoEstadual(pessoaJuridica.getInscricaoEstadual()+"A");

        pessoaJuridica.getEndereco().setLogradouro("Alterado");
        pessoaJuridica.getEndereco().setBairro("Bairro Alt");
        pessoaJuridica.getEndereco().setNumero(gerarNumero(3));

        sessao = HibernateUtil.abrirConexao();
        pessoaJuridicaDao.salvarOuAlterar(pessoaJuridica, sessao);
        sessao.close();

        sessao = HibernateUtil.abrirConexao();
        PessoaJuridica pjAlt =  pessoaJuridicaDao.pesquisarPorId(pessoaJuridica.getId(), sessao);
        sessao.close();

        assertEquals(pessoaJuridica.getNome(), pjAlt.getNome());
        assertEquals(pessoaJuridica.getCnpj(), pjAlt.getCnpj());
        assertEquals(pessoaJuridica.getEndereco().getLogradouro(), 
                     pjAlt.getEndereco().getLogradouro());

    }

    @Test
    public void testExcluir(){
        System.out.println("Excluir");

        buscarPessoaJuridicaBD();

        sessao = HibernateUtil.abrirConexao();
        pessoaJuridicaDao.excluir(pessoaJuridica, sessao);

        PessoaJuridica pjExcluido = pessoaJuridicaDao.pesquisarPorId(pessoaJuridica.getId(), sessao);
        sessao.close();

        assertNull(pjExcluido);
    }
    
    @Test
    public void testPesquisarPorId(){
        System.out.println("Pesquisar por ID");

        buscarPessoaJuridicaBD();

        sessao = HibernateUtil.abrirConexao();
        PessoaJuridica pjPesquisado = pessoaJuridicaDao.pesquisarPorId(pessoaJuridica.getId(), sessao);
        sessao.close();

        assertEquals(pessoaJuridica.getId(), pjPesquisado.getId());
    }

    @Test
    public void testPesquisarPorNome(){
        System.out.println("Pesquisar por nome");

        buscarPessoaJuridicaBD();

        sessao = HibernateUtil.abrirConexao();
        List<PessoaJuridica> list = pessoaJuridicaDao.pesquisarPorNome(pessoaJuridica.getNome(), sessao);
        sessao.close();

        assertFalse(list.isEmpty());
    }

    private Endereco gerarEndereco(){
        Endereco end = new Endereco(
                "Rua das Flores",
                "Centro",
                gerarNumero(3),
                gerarCidade(),
                "SC",
                "casa",
                gerarCep()
        );
        return end;
    }

    public PessoaJuridica buscarPessoaJuridicaBD(){
        sessao = HibernateUtil.abrirConexao();
        List<PessoaJuridica> list = sessao.createQuery("FROM PessoaJuridica pf", PessoaJuridica.class).getResultList();
        sessao.close();

        if(list.isEmpty()){
            testSalvar();
        }else{
            int indice = (int) (Math.random() * list.size());
            pessoaJuridica = list.get(indice);
        }
        return pessoaJuridica;
    }

}
