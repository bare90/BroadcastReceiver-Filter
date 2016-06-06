
import java.util.ArrayList;
import java.util.List;


public class Receiver {
	String source_file;
	boolean important;
	List<String> action_Name;
	List<String> android_Name;
	List<String> export;
	List<String> priority_Num;
	List<String> permission;
	List<String> matched_results;
	
	public Receiver() {this.action_Name = new ArrayList <String>();this.android_Name = new ArrayList <String>();this.priority_Num = new ArrayList <String>();
	                   this.export = new ArrayList <String>();this.permission = new ArrayList <String>(); this.important = true;this.matched_results = new ArrayList <String>();}
	
	public void validate ()
	{
		if(matched_results.size() != action_Name.size()) 
		  this.important = true; 
		else 
		{
			 this.important = false;
			 System.out.println("it has system-protected broadcast");
			 return;			
		}
		
		if((export.isEmpty() || export.get(0).equalsIgnoreCase("true"))  && (permission.isEmpty())) 
			  this.important = true;
		else 
		{
			if(!priority_Num.isEmpty())
			{
				int val =Integer.parseInt(priority_Num.get(0).toString());
				if(val < 999)
				this.important = true;	
			
			}
			else 
				this.important = false;	
			  System.out.println("it is not system-protected broadcast but protected by export or permission");
		}	 	
	}
	
	public void print()
	{
		
		System.out.println("Receiver Source File: " + source_file);
		System.out.println("Important Status: " + important);		
		System.out.print("Action name: ");		
		for (int i = 0; i < action_Name.size(); i++)
		{
			 System.out.print(action_Name.get(i).toString() + " "); 
		}
		System.out.println("");
		
		System.out.print("Android name: ");	
		
		for (int i = 0; i < android_Name.size(); i++)
		{
			 System.out.print(android_Name.get(i).toString() + " "); 
		}
		System.out.println("");
		
		System.out.print("Export: ");		
		for (int i = 0; i < export.size(); i++)
		{
			 System.out.print(export.get(i).toString() + " "); 
		}
		System.out.println("");
		
		System.out.print("Priority: ");	
		for (int i = 0; i < priority_Num.size(); i++)
		{
			 System.out.print(priority_Num.get(i).toString() + " "); 
		}
		System.out.println("");
		
		System.out.print("Permission: ");		
		for (int i = 0; i < permission.size(); i++)
		{
			 System.out.print(permission.get(i).toString() + " "); 
		}
		System.out.println("");
		
		System.out.print("Matched results: ");		
		for (int i = 0; i <  matched_results.size(); i++)
		{
			 System.out.print( matched_results.get(i).toString() + " "); 
		}
		System.out.println("");
		
	}
	
}

