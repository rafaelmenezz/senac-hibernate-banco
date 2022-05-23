package br.com.senac.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Session;
import org.junit.Test;

import br.com.senac.entidade.Profissao;
import br.com.senac.util.GeradorUtil;

public class ProfissaoDaoImplTest {
   
   private Profissao profissao;
   private ProfissaoDao profissaoDao;
   private Session session;

   public ProfissaoDaoImplTest(){
      profissaoDao = new ProfissaoDaoImpl();
   }

   @Test
   public void testSave(){

      profissao = new Profissao(GeradorUtil.gerarProfissao(), GeradorUtil.gerarSenha(20));
      
      session = HibernateUtil.abrirConexao();
      profissaoDao.salvarOuAlterar(profissao, session);
      session.close();

      assertNotNull(profissao.getId());
   }

   @Test
   public void testPesquisarPorID(){
      buscarProfissaoBD();

      session = HibernateUtil.abrirConexao();
      Profissao pesquisado = profissaoDao.pesquisarPorId(profissao.getId(), session);
      session.close();

      assertEquals(pesquisado.getId(), profissao.getId());
   }

   @Test
   public void testPesquisarPorNome(){
      buscarProfissaoBD();

      session = HibernateUtil.abrirConexao();
      List<Profissao> profissoes = profissaoDao.pesquisarPorNome(profissao.getNome(), session);
      session.close();

      assertEquals(profissao.getNome(), profissoes.get(0).getNome());
   }

   @Test
   public void testAlterar(){
      buscarProfissaoBD();

      profissao.setNome(GeradorUtil.gerarProfissao() + " Update");
      profissao.setDescricao("Alterado");

      session = HibernateUtil.abrirConexao();
      profissaoDao.salvarOuAlterar(profissao, session);
      session.close();

      session = HibernateUtil.abrirConexao();
      Profissao alterado = profissaoDao.pesquisarPorId(profissao.getId(), session);
      session.close();

      assertEquals(profissao.getId(), alterado.getId());
   }

   @Test
   public void testExcluir(){
      buscarProfissaoBD();

      session = HibernateUtil.abrirConexao();
      profissaoDao.excluir(profissao, session);

      session = HibernateUtil.abrirConexao();
      Profissao excluido = profissaoDao.pesquisarPorId(profissao.getId(), session);
      session.close();

      assertNull(excluido);
   }

   public Profissao buscarProfissaoBD(){
      session = HibernateUtil.abrirConexao();
      List<Profissao> profissoes = session.createQuery("FROM Profissao p", Profissao.class).getResultList();
      session.close();

      if(profissoes.isEmpty()){
         testSave();
      }else{
         int indice = (int) (Math.random() * profissoes.size());
         profissao = profissoes.get(indice);
      }
      return profissao;
   }
}
