package ShoppingListPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileManager{    
    private FileOutputStream fileOutStream;
    private ObjectOutputStream objectOutStream;
    private FileInputStream fileInStream;
    private ObjectInputStream objectInStream;
    File currDirectory;
    
    public FileManager(){
        currDirectory = new File(System.getProperty("user.dir"));
    }
    
    public ArrayList LoadPurchases(){
        ArrayList readFileArray = new ArrayList();
        try{
            fileInStream = new FileInputStream(currDirectory + "/data/purchasedata.ser");
            objectInStream = new ObjectInputStream(fileInStream);
            readFileArray = (ArrayList) objectInStream.readObject();
            
            objectInStream.close();
            fileInStream.close();
        }catch(IOException i){
            String path = currDirectory.getPath() + File.separator + "data" + File.separator + "purchasedata.ser";
            File newFile = new File(path);
            newFile.getParentFile().mkdirs();
            try {
                newFile.createNewFile();
            } catch (IOException ex) {
            }
        }catch(ClassNotFoundException c){
            c.printStackTrace();
        }
        return readFileArray;
    }   
      
    public void SavePurchases(ArrayList currentPurchaseList){
        try{
            fileOutStream = new FileOutputStream(currDirectory + "/data/purchasedata.ser");
            objectOutStream = new ObjectOutputStream(fileOutStream);
            objectOutStream.writeObject(currentPurchaseList);
            objectOutStream.close();
            fileOutStream.close();
        }catch(IOException i){
        }
    }
}
