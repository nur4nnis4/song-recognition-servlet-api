/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.List;
import org.hibernate.HibernateException;

/**
 *
 * @author asus
 */
public interface SongCatalogDAOServer {
    public List select(int songId) throws HibernateException;
    List selectAll() throws HibernateException;
}
