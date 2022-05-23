package br.com.senac.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import br.com.senac.entidade.Profissao;

public class ProfissaoDaoImpl extends BaseDaoImpl<Profissao, Long> implements ProfissaoDao, Serializable{

   @Override
   public Profissao pesquisarPorId(Long id, Session sessao) throws HibernateException {
      return sessao.find(Profissao.class, id);
   }

   @Override
   public List<Profissao> pesquisarPorNome(String nome, Session session) throws HibernateException {
      Query<Profissao> consulta = session.createQuery("FROM Profissao p WHERE p.nome LIKE :nome", Profissao.class);
      consulta.setParameter("nome", "%"+nome+"%");
      return consulta.getResultList();
   }
   
}
