package br.com.senac.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.com.senac.entidade.Cartao;

public interface CartaoDao extends BaseDao<Cartao, Long> {

   List<Cartao> pesquisarPorNumero(String numero, Session sessao) throws HibernateException;
   
}
