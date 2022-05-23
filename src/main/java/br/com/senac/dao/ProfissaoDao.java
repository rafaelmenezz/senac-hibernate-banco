package br.com.senac.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.com.senac.entidade.Profissao;

public interface ProfissaoDao extends BaseDao<Profissao, Long> {

   List<Profissao> pesquisarPorNome(String nome, Session session) throws HibernateException;
}
