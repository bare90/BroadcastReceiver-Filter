

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReadFromFile {
	public String file_path;
	public int broadcast_receiver_count;
	public List<Receiver> defined_receivers;
	public ReadFromFile() {this.file_path = ""; this.broadcast_receiver_count = 0; this.defined_receivers=new ArrayList<Receiver> ();}
	public ReadFromFile(String path,int rev) {this.file_path = path; this.broadcast_receiver_count = rev; this.defined_receivers=new ArrayList<Receiver> ();}
    public void add_new_receiver(Receiver rev) { rev.source_file = file_path; rev.validate();defined_receivers.add(rev); }
	
    public String ReadFile(String Path) {
		BufferedReader reader = null;
		String laststr = "";
		try {
			FileInputStream fileInputStream = new FileInputStream(Path);
			InputStreamReader inputStreamReader = new InputStreamReader(
					fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				laststr = laststr + tempString;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return laststr;
	}

	public void writeFile(String Path, String fileName, String content) {
		FileOutputStream out = null;
		File file = null;
		try {
			file = new File(Path);
			if (!file.exists())
				file.mkdir();
			out = new FileOutputStream(new File(Path + "\\" + fileName),true);
			out.write(content.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void displayReceivers() 
	    {		
			
	    	for (int i = 0; i < defined_receivers.size(); i++)
			{
				defined_receivers.get(i).print();	
				System.out.println("------------------------------------");
			}                       			 
	   }


	public static List<HashMap<String, String>> readfile(String filepath)
			throws FileNotFoundException, IOException {
		List pathL = new ArrayList();
		try {
			//File file = new File(filepath);
			//String[] filelist = file.list();
			//for (int i = 0; i < filelist.length; ++i) {
				//String pathName = filepath + "\\" + filelist[i];
				File readfile = new File(filepath);
				HashMap fileInfo = new HashMap();
				fileInfo.put("name", readfile.getName());
				fileInfo.put("path", readfile.getPath());
				pathL.add(fileInfo);
			//}
		} catch (Exception e) {
			System.out.println("readfile()   Exception:" + e.getMessage());
		}
		return pathL;
	}

}