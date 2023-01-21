/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author asus
 */
public class SongMetadata {
    private int fingerprintHit;
    private String songTitle;
    private Integer songId;
    private String offsetTime;

    public SongMetadata() {
    }
        
    public SongMetadata(int fingerprintHit,int songId,String songTitle, String offseTime) {
       this.songId = songId;
       this.fingerprintHit = fingerprintHit;
       this.songTitle = songTitle;
       this.offsetTime = offseTime;
    }
    public Integer getSongId() {
        return this.songId;
    }
    
    public void setSongId(Integer songId) {
        this.songId = songId;
    }
    
   
    public Integer getFingerprintHit() {
        return this.fingerprintHit;
    }
    
    public void setFingerprintHit(Integer songId) {
        this.fingerprintHit = songId;
    }
    public String getSongTitle() {
        return this.songTitle;
    }
    
    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }
    
    public String getOffsetTime() {
        return this.offsetTime;
    }
    
    public void setOffsetTime(String offsetTime) {
        this.offsetTime = offsetTime;
    }

}
