package br.com.senac.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.hibernate.Session;
import org.junit.Test;

import br.com.senac.entidade.Cartao;
import br.com.senac.entidade.PessoaFisica;
import br.com.senac.util.GeradorUtil;

public class CartaoDaoImplTest {

   private Cartao cartao;
   private CartaoDao cartaoDao;
   private Session session;

   public CartaoDaoImplTest(){
      cartaoDao = new CartaoDaoImpl();
   }

   @Test
   public void testSave(){
      System.out.println("Test Salvar");

      cartao = new Cartao(GeradorUtil.gerarNumero(12), 
      GeradorUtil.gerarBandeiraCartao() , 
      GeradorUtil.gerarNumero(2) +"/"+ GeradorUtil.gerarNumero(4));

      cartao.setCliente(new PessoaFisicaDaoImplTest().buscarPessoaFisicaBd());

      session = HibernateUtil.abrirConexao();
      cartaoDao.salvarOuAlterar(cartao, session);
      session.close();

      assertNotNull(cartao.getId());
   }

   @Test
   public void testAlterar(){
      System.out.println("Test Alterar");

      buscarCartaoBD();
      cartao.setNumero(cartao.getNumero() + "T");
      session = HibernateUtil.abrirConexao();
      cartaoDao.salvarOuAlterar(cartao, session);
      session.close();

      session = HibernateUtil.abrirConexao();
      Cartao alterado = cartaoDao.pesquisarPorId(cartao.getId(), session);
      session.close();

      assertEquals(cartao.getId(), alterado.getId());
      
   }

   @Test
   public void testExcluir(){
      System.out.println("Test Excluir");

      buscarCartaoBD();

      session = HibernateUtil.abrirConexao();
      cartaoDao.excluir(cartao, session);

      session = HibernateUtil.abrirConexao();
      Cartao excluido = cartaoDao.pesquisarPorId(cartao.getId(), session);
      session.close();

      assertNull(excluido);
   }

   @Test
   public void testPesquisarPorId(){
      System.out.println("Test pesquisar por id");

      buscarCartaoBD();

      session = HibernateUtil.abrirConexao();
      Cartao pesquisado = cartaoDao.pesquisarPorId(cartao.getId(), session);
      session.close();

      assertEquals(cartao.getId(), pesquisado.getId());
   }

   @Test
   public void testPesquisarPorNumero(){
      System.out.println("Test pesquisa por numero");

      buscarCartaoBD();

      session = HibernateUtil.abrirConexao();
      List<Cartao> lista = cartaoDao.pesquisarPorNumero(cartao.getNumero(), session);
      session.close();

      assertFalse(lista.isEmpty());
      assertEquals(cartao.getNumero(), lista.get(0).getNumero());
   }

   public Cartao buscarCartaoBD(){
      session = HibernateUtil.abrirConexao();
      List<Cartao> lista = session.createQuery("FROM Cartao c", Cartao.class).getResultList();
      session.close();

      if(lista.isEmpty()){
         testSave();
      }else{
         int indice = (int) (Math.random() * lista.size());
         cartao = lista.get(indice); 
      }

      return cartao;
   }
   
}
