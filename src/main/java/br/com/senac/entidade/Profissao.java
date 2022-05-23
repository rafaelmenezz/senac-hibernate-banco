package br.com.senac.entidade;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "profissao")
public class Profissao implements Serializable {

   private static final long serialVersionUID = 1L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false)
   private String nome;

   @Column(nullable = false)
   private String descricao;

   public Profissao() {
   }

   public Profissao(String nome, String descricao) {
      this.nome = nome;
      this.descricao = descricao;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getNome() {
      return nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }

   public String getDescricao() {
      return descricao;
   }

   public void setDescricao(String descricao) {
      this.descricao = descricao;
   }

   @Override
   public int hashCode() {
      int hash = 0;
      hash += (id != null ? id.hashCode() : 0);
      return hash;
   }

   @Override
   public boolean equals(Object object) {
      if (!(object instanceof Profissao)) {
         return false;
      }
      Profissao other = (Profissao) object;
      return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
   }

   @Override
   public String toString() {
      return "br.com.senac.entidade.Profissao[ id=" + id + " ]";
   }
}
