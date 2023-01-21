/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import controllers.DatabaseController;
import controllers.FingerprintMatching;

import java.io.BufferedReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.SongCatalog;
import models.SongMetadata;

/**
 *
 * @author nur4nnis4@gmail.com
 */
@WebServlet(urlPatterns = {"/Servlet"})
public class Servlet extends HttpServlet {

    private Gson gson = new Gson();
    private static LinkedHashMap<String, List<String>> mapFullSong;
    SongCatalog songCatalog = new SongCatalog();
    DatabaseController dc = new DatabaseController();
    
    @Override
    public void init() throws ServletException { 
        mapFullSong = dc.getReferenceFingerprints();        
    }
    
    @Override
    protected long getLastModified(HttpServletRequest req) {
        return super.getLastModified(req); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getInitParameter(String name) {
        return super.getInitParameter(name); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<SongMetadata> list = new ArrayList<>();    
        
        try {
            BufferedReader br = request.getReader();
            String jsonString ="";
            String line;
            while((line=br.readLine()) !=null){
                jsonString+=line;
            }
            JsonObject jObEntries = (JsonObject) new JsonParser().parse(jsonString);
            JsonArray jArEntries = jObEntries.getAsJsonArray("fingerprint");
            LinkedHashMap<String, List<String>> mapRecord = new LinkedHashMap<>();
        
            for(int i=0;i<jArEntries.size();i++){
                JsonObject jo = jArEntries.get(i).getAsJsonObject();
                mapRecord.computeIfAbsent(jo.get("hash").getAsString(), k -> new ArrayList<>()).add(jo.get("value").getAsString());
               
            }
            SongMetadata songMetadata= new FingerprintMatching().search(mapFullSong, mapRecord);            
            list.add(songMetadata);
            
        } catch (Exception e) {
        }
        JsonArray jarray = this.gson.toJsonTree(list).getAsJsonArray();
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("SongMetadata", jarray);
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();   
        
        out.println(jsonObject.toString());
        out.flush();
 
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
