package com.tuvn.webNA.models.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.tuvn.webNA.models.dao.GenericaDao;

public class GenericaDaoImpl<T> implements GenericaDao<T>{
	
private Class<T> entityClass;
    
	//Inicialisar el entityManagerFactory utilizando el proyectoNA para la creacion de la base de datos
    protected static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyectoNA");
    //Inicialisar el entityManager
    protected EntityManager entityManager;

    public GenericaDaoImpl()
    {
        entityManager = emf.createEntityManager();
    }
    
    public GenericaDaoImpl(Class<T>entityClass)
    {
        this.entityClass = entityClass;
        entityManager = emf.createEntityManager();
    }
    
    //Definimos el CRUD
    public T create(T t)
    {
    	//Definicion para crear la persitencia de datos 
        this.entityManager.persist(t);
        return t;
    }
    
    public T read (Object id)
    {
        return this.entityManager.find(entityClass, id);
    }
    
    public T update (T t)
    {
    	//Definicion del metodo para realizar la actualizacion de datos
        return this.entityManager.merge(t);
    }
    
    public void delete(T t)
    {
    	//Definicion del metodo para realizar la eliminacion de datos
        t = this.entityManager.merge(t);
        this.entityManager.remove(t);
    }
    
    
    //Definir los parámetros
    public void beginTransaction()
    {
        if(!entityManager.getTransaction().isActive())
            entityManager = emf.createEntityManager();
        
        entityManager.getTransaction().begin();    
    }
    
    public void commit()
    {
    	//Funcion para confirmar las transacciones
        if(entityManager.getTransaction().isActive())
            entityManager.getTransaction().commit();
    }
    
    public void rollback()
    {
         if(entityManager.getTransaction().isActive())
            entityManager.getTransaction().rollback();
    }
    
    public void closeTransaction()
    {
        entityManager.close();
    }
    
    public void commitAndCloseTransaction()
    {
        commit();
        closeTransaction();
    }
    
    public void flush()
    {
        entityManager.flush();
    }
    
    public List<T> findAll()
    {
        /*javax.persistence.criteria.CriteriaQuery cq = this.entityManager.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));*/
        return null;//this.entityManager.createQuery(cq).getResultList();
    }

}
