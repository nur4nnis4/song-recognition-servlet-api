/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import models.SongCatalog;
import models.SongCatalogDAOImplServer;
import models.SongFingerprints;
import models.SongFingerprintsDAOImplServer;

/**
 *
 * @author asus
 */
public class DatabaseController {
    
    public LinkedHashMap<String, List<String>> getReferenceFingerprints(){
        SongFingerprintsDAOImplServer songFingerprintsDAOI = new SongFingerprintsDAOImplServer();
        LinkedHashMap<String, List<String>> mapFullSong = new LinkedHashMap<>();
        
        List fullSongs = songFingerprintsDAOI.selectAll();
        for(int i=0; i<fullSongs.size();i++){
            SongFingerprints sf =(SongFingerprints) fullSongs.get(i);
            mapFullSong.computeIfAbsent(sf.getHash(), k -> new ArrayList<>()).add(sf.getValue());
            fullSongs.remove(i);
        }
        return mapFullSong;
    }
    public SongCatalog getSongCatalog(int songId){
        SongCatalog sc = new SongCatalog();
        if(songId != 0){
            SongCatalogDAOImplServer songCatalogDAOI = new SongCatalogDAOImplServer();  
            List metaData = songCatalogDAOI.select(songId);    
            sc = (SongCatalog) metaData.get(0);
        }
        else{
            sc.setSongId(songId);
            sc.setSongTitle("No songs found");
        }
        return sc ;
    }
    
}
