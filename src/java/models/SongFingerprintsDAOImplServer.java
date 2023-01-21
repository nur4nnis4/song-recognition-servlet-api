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
public class SongFingerprintsDAOImplServer implements SongFingerprintsDAO{
    Session session;
    @Override
    public List selectAll() throws HibernateException {
        List resultList=null;
        try {
            session = HibernateUtiServer.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("from SongFingerprints");
            resultList = q.list();
            session.getTransaction().commit();        
        } catch (HibernateException he) {
            he.printStackTrace();
        }  
        return resultList;
    }
    
}
