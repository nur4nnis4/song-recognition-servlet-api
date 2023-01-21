/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import models.SongMetadata;

/**
 *
 * @author nur4nnis4@gmail.com
 */
public class FingerprintMatching {
    
    private static final int MATCHING_THRESHOLD = 15;
    private static String offsetTime;
    
    public SongMetadata search(LinkedHashMap<String, List<String>> mapFullSong, LinkedHashMap<String, List<String>> mapRecord){        
                                
        LinkedHashMap<String, List<String>> map2 = new LinkedHashMap<>();
        
        Iterator it = mapRecord.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String,List<String>> mapRecordPair = (Map.Entry) it.next();
            if(mapFullSong.containsKey(mapRecordPair.getKey())){
                for(String value: mapFullSong.get(mapRecordPair.getKey())){                   
                    map2.computeIfAbsent(mapRecordPair.getKey(), k -> new ArrayList<>()).add(value);                     
                }
            }        
            else
                it.remove();           
        }          
          
        int[] maxSongId = checkTimeCoherency(mapRecord, map2);        
        
        DatabaseController dc = new DatabaseController();
        SongMetadata sm = new SongMetadata();
        sm.setFingerprintHit(maxSongId[0]);
        
        if(maxSongId[0]>=MATCHING_THRESHOLD){   
            sm.setOffsetTime(offsetTime);
            sm.setSongId(maxSongId[1]);
            sm.setSongTitle(dc.getSongCatalog(sm.getSongId()).getSongTitle());
        }
        else{
            sm.setOffsetTime("00:00");
            sm.setSongId(0);
            sm.setSongTitle("No Songs Found!");
        }
        return sm;
        
    }
    private int[] checkTimeCoherency(LinkedHashMap<String,List<String>> record, LinkedHashMap<String,List<String>> fullSong){
        
        LinkedHashMap<Integer,List<Integer>> listOfDelta = new LinkedHashMap<>();
        Iterator it = record.entrySet().iterator();
        int[] maxDeltaOccurrence = {0,0};
        while(it.hasNext()){
            Map.Entry<String,List<String>> pair = (Map.Entry) it.next();
            if(fullSong.containsKey(pair.getKey())){
               for(String recordValue:pair.getValue()){
                   for(String fullSongValue:fullSong.get(pair.getKey())){
                       int songId = Integer.parseInt(fullSongValue)/10000;
                       int fullSongAnchorTime = Integer.parseInt(fullSongValue)%10000;
                       int recordAnchorTime = Integer.parseInt(recordValue);
                       
                       //delta = Absolute time of the anchor in the record - Absolute time of the Anchor in the full song
                       int delta = Math.abs(recordAnchorTime-fullSongAnchorTime);                        
                       listOfDelta.computeIfAbsent(delta,k -> new ArrayList<>()).add(songId);
                       
                       if(maxDeltaOccurrence[0]<listOfDelta.get(delta).size()){
                           maxDeltaOccurrence[0] = listOfDelta.get(delta).size();
                           maxDeltaOccurrence[1] = delta;
                       }                       
                   }
               }
            }
                    
        }               
        record.clear();
        fullSong.clear();
        LinkedHashMap<Integer,List<Integer>> songIdMap = new LinkedHashMap<>();
        int maxSongId[]={0,0};
        try{
            for(int songId: listOfDelta.get(maxDeltaOccurrence[1])){
            songIdMap.computeIfAbsent(songId,k -> new ArrayList<>()).add(maxDeltaOccurrence[0]);
            if(maxSongId[0]<songIdMap.get(songId).size()){
                maxSongId[0] = songIdMap.get(songId).size();
                maxSongId[1] = songId; 
            }
        }            
        }catch(NullPointerException e){
            
        }
        double offset = Math.ceil(maxDeltaOccurrence[1]*0.064);//0.064s is time resolution
        int offsetMin =(int) offset/60;
        int offsetSec =(int) offset%60;
               
        offsetTime = String.format("%02d", offsetMin)+":"+String.format("%02d", offsetSec);        
      
        return maxSongId;
        
    } 
    
    
   
}
