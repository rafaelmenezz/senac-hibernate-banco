package br.com.senac.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import br.com.senac.entidade.Cartao;

public class CartaoDaoImpl extends BaseDaoImpl<Cartao, Long> implements CartaoDao, Serializable{

   @Override
   public Cartao pesquisarPorId(Long id, Session sessao) throws HibernateException {
      return sessao.find(Cartao.class, id);
   }

   @Override
   public List<Cartao> pesquisarPorNumero(String numero, Session sessao) throws HibernateException {
      Query<Cartao> consulta = sessao.createQuery("From Cartao c WHERE c.numero LIKE :parametro", Cartao.class);
      consulta.setParameter("parametro", "%"+numero+"%");
      return consulta.getResultList();
   }
   
}
