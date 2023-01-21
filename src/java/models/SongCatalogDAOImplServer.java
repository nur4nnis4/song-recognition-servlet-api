/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import hibernate.HibernateUtiServer;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author asus
 */
public class SongCatalogDAOImplServer implements SongCatalogDAOServer{
    
    Session session;
    
    @Override
    public List select(int songId) throws HibernateException {
        List resultList=null;
        session =  HibernateUtiServer.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            Query q = session.createQuery("from SongCatalog a where a.songId = '"+songId+"'");
            resultList= q.list();
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }        
        return resultList;}

    @Override
    public List selectAll() throws HibernateException {
        List resultList=null;
        try {
            session = HibernateUtiServer.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("from SongCatalog");
            resultList = q.list();
            session.getTransaction().commit();        
        } catch (HibernateException he) {
            he.printStackTrace();
        }  
        return resultList;
    }
    
}
