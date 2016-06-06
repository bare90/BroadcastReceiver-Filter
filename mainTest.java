

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;





public class mainTest {

	
    
	public static void main(String[] args) {
		String oldfilepath = "C:/Users/User/Desktop/filter/in/";                   ///the file path
		String newfilepath = "C:/Users/User/Desktop/filter/out/";		

        String broadcast= new ReadFromFile().ReadFile("C:/Users/User/Desktop/filter/filter/broadcast_actions.txt");
        //READ
       
        //-----------------------------------------------
		//
       
     //  int y=  new File(oldfilepath).listFiles().length;
       
     //  System.out.println("total files:"+y);
		
			File folder = new File(oldfilepath);
			File[] listOfFiles = folder.listFiles();

			for (File file : listOfFiles) {
				
			   if (file.isFile() && file.getName().endsWith(".xml")) {  //read XML file and parse it.
			       // System.out.println(file.getName());
			    	try{
					
			    		 DocumentBuilderFactory dbFactory  = DocumentBuilderFactory.newInstance();
					     DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					     Document doc = dBuilder.parse(file.getPath());				     
					     NodeList nodeList = doc.getElementsByTagName("receiver");
					     List<String> list=new ArrayList<String>();
					     ReadFromFile infected_file = new ReadFromFile(doc.getDocumentURI(),nodeList.getLength());		   
					     
					     
					     /*
							 * ----------------------------------------------------------------//<receiver> attribute
							 * */	    
								 for (int i = 0; i < nodeList.getLength(); i++) 
								 {
									     Receiver receiver = new Receiver(); //new receiver instance for each receiver element
									     Node receiverNode = nodeList.item(i);						    	
								    	 if (receiverNode instanceof Element) 
								    	 {
								    		 
								    		 int number_of_receiver_attr = receiverNode.getAttributes().getLength(); //get the number of attributes the receiver node has
								    		 for(int t =0;t<number_of_receiver_attr;t++) //loop through each attribute to see if it matches out criteria
								    		 {
									    			if (receiverNode.getAttributes().item(t).getNodeName().equalsIgnoreCase("android:exported"))
									    				receiver.export.add(receiverNode.getAttributes().item(t).getNodeValue());
									    			if (receiverNode.getAttributes().item(t).getNodeName().equalsIgnoreCase("android:name"))
									    				receiver.android_Name.add(receiverNode.getAttributes().item(t).getNodeValue());
									    			if (receiverNode.getAttributes().item(t).getNodeName().equalsIgnoreCase("android:permission"))
									    				receiver.permission.add(receiverNode.getAttributes().item(t).getNodeValue());
									    	 }						    		
						 		 
									    	if(receiverNode.hasChildNodes()) //if receiver has child nodes						    		
									    	{
									    		 
									    		NodeList parentNodes = receiverNode.getChildNodes(); //get the child nodes... this will return intent-filter elements	
									    		for (int j = 0; j < parentNodes.getLength(); j++) 
									    		{					    		 	        
							    			    	 Node intentNode = parentNodes.item(j);				    			    	
							    		 	         if (intentNode instanceof Element) 
							    		 	         {
							    		 	        	 int number_of_intent_attr = intentNode.getAttributes().getLength(); //get the number of attributes the receiver node has
											    		 for(int t =0;t<number_of_intent_attr;t++) //loop through each attribute to see if it matches out criteria
											    		 {
												    			if (intentNode.getAttributes().item(t).getNodeName().equalsIgnoreCase("android:priority"))
												    				receiver.priority_Num.add(intentNode.getAttributes().item(t).getNodeValue());
												    	 }
							    		 	        	 
											    		 if(intentNode.hasChildNodes()) //if intent has child nodes						    		
													     {	
							    		 	        	    NodeList grandSonNodes = intentNode.getChildNodes(); //this will return all the action elements 
							    		 	        	    for (int k = 0; k < grandSonNodes.getLength(); k++)
							    		 	        	    {
								    		 	        		  Node actionNode = grandSonNodes.item(k);
								    		 	        		  if(actionNode instanceof Element)
								    		 	        		  {
								    		 	        			 int number_of_action_attr = actionNode.getAttributes().getLength(); //get the number of attributes the receiver node has
								    		 	        			 for(int t =0;t<number_of_action_attr;t++) //loop through each attribute to see if it matches our criteria
														    		 {
								    		 	        				
															    			if (actionNode.getAttributes().item(t).getNodeName().equalsIgnoreCase("android:name"))
															    		    {   
															    				 String input = actionNode.getAttributes().item(t).getNodeValue();
															    				 receiver.action_Name.add(input);
															    				 Pattern pattern = Pattern.compile(input);									    		                         
											    		                    	 Matcher m = pattern.matcher(broadcast);									    		                    	 
											    		                    							    		                    	        
											    		                    	 if (m.find()) 
											    		                    	 {										    		                    		 
										    		                    	         receiver.matched_results.add(m.group()); // add to list								    							
										    		                    	     }
															    		    }
															    	 }						    		 	        			
								    		 	        		  }					    		 	        			
								    		 	        	}				    		 	        	 
													    }						    		
									    	        }       	
									    	    } 						    	 
									    	}
								    	 }
								    	 infected_file.add_new_receiver(receiver); //add receiver to file class
								   }
					   
				infected_file.displayReceivers();
					
			
				}catch (ParserConfigurationException e) {
			         e.printStackTrace();
			      } catch (SAXException e) {
			         e.printStackTrace();
			      } catch (IOException e) {
			         e.printStackTrace();
			      } 
			        
			    }
			    if (file.isFile() && file.getName().endsWith(".java")){ // read java file and parse it.
			    	//System.out.println(file.getName());
			    	String fileJava =new ReadFromFile().ReadFile(file.getPath());
			    	List<String> list=new ArrayList<String>();
			    	String [] sysBroadcast;
			    	String pattern = "\\bregisterReceiver\\b";
			    	
			    	try {
			    		String line = null;
			    		 FileReader fileReader = new FileReader("C:/Users/User/Desktop/filter/filter/broadcast_actions.txt");
				          BufferedReader bufferedReader = new BufferedReader(fileReader);
				            while((line = bufferedReader.readLine()) != null) {				          
				            	 list.add("\\b(?:addAction|IntentFilter)\\b\\s*\\((\"?)\\s*"+line+"\\s*(\"?)\\)");
				            }
				            bufferedReader.close(); 				            							
					       sysBroadcast= list.toArray(new String[list.size()]);					       
					       Pattern rPattern = Pattern.compile(pattern);
							Matcher mRgister = rPattern.matcher(fileJava);		
							int countRgister= 0;
					  	 while (mRgister.find()) {                              	 
					  		countRgister++;
					  	 }
					  	System.out.println("total dynamic registration:"+countRgister);
					     if (countRgister !=0){
					    	 //System.out.println(m);
					    	 int countSystem = 0;
					         for (int j = 0; j < sysBroadcast.length; j++){  
					      	   Pattern sPattern = Pattern.compile(sysBroadcast[j]);
					      	   Matcher mBroadcast = sPattern.matcher(fileJava);
					      	  while(mBroadcast.find()) {                              	 
					      		  countSystem++;
					      		  }
					      	  }
					         System.out.println("system broadcast:"+countSystem);
					         
					         if (countRgister>countSystem)
					        	 System.out.println("there are some vunerable ");
					         if (countRgister == countSystem)
					        	 System.out.println("there no vunerable ");
					         if (countRgister< countSystem)
					        	 System.out.println("error ");
					     }else{
					    	 System.out.println("There is no dynamic receiver registration "  );
					     }
						//for (int i = 0; i < pathL.size(); ++i) {
							//parse((HashMap) javaList.get(0), newfilepath);
						//}
						//System.out.println(fileName);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
			    } 
			}
		}  
	}


	