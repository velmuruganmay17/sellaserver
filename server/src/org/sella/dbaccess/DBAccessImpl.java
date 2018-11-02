package org.sella.dbaccess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class DBAccessImpl {
	private static DBAccessImpl _instance = null;
	private static Map<String,String> accessMap= new HashMap<>();
	private static final String USERS="users";
	private static final String FEEDER_CONTENT="feeder";
	
	
	  	private DBAccessImpl() {
		}
	  	
	    public static DBAccessImpl getInstance() {
	        synchronized (DBAccessImpl.class) {
	            if (_instance == null) {
	                _instance = new DBAccessImpl();
	            }
	        }
	        return _instance;
	    }
	    
	    
	    public String getFeedContent() {
	    	if(!accessMap.containsKey(FEEDER_CONTENT)) {
    			accessMap.put(FEEDER_CONTENT, readFile("feeder.json"));
	    	}
	    	return accessMap.get(FEEDER_CONTENT);
	    }
	    public String getUsers() {
	    	if(!accessMap.containsKey(USERS)) {
	    			accessMap.put(USERS, readFile("users.json"));
	    	}
	    	return accessMap.get(USERS);
	    }
	    
	    
	    private String readFile(final String fileName) {
	    	InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
		    final StringBuilder response =new StringBuilder();
		    InputStreamReader isr =null;
		    BufferedReader reader = null;
		    System.out.println( "file name with path >> "+getClass().getClassLoader().getResource("users.json").getFile() );
		    try {
		        if (is != null) {
		              isr = new InputStreamReader(is);
		              reader = new BufferedReader(isr);
		            String text; 
						while ((text = reader.readLine()) != null) {
							response.append(text);
						}
					} 
		       else {
		        	System.out.println("null");
		        }
		    }
	        catch (IOException e) {
				e.printStackTrace();
			}  catch ( Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if(reader!=null) {reader.close(); }
					if(isr!=null) {isr.close(); }
					if(is!=null) {is.close(); }
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		    return response.toString();
	    }
	    
	    public void writeUser(final String fileContent) {
	    	BufferedWriter bw = null;
			FileWriter fw = null;
			try {
				fw = new FileWriter( new File( getClass().getClassLoader().getResource("users.json").getFile()));
				bw = new BufferedWriter(fw);
				bw.write(fileContent);
				System.out.println("Done"); 
			} catch (IOException e) {
				e.printStackTrace();

			} catch ( Exception e) {
				e.printStackTrace();

			} finally {
				try {
					if (bw != null)
						bw.close();
					if (fw != null)
						fw.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			accessMap.put(USERS, readFile("users.json"));
	    }
	    
	    
}
