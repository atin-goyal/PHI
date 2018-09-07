package com.psr.main;

import com.jcraft.jsch.*;  
import java.io.*;  
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class PhiAuto {

	private String execute(String user, String host, String password, String command)
	{
		System.out.println("Server: " + host + " Username: " + user + " Password: " + password + " Command: " + command);
		String temp= null; 
		try{  
			JSch jsch=new JSch();    
			java.util.Properties config = new java.util.Properties();
	        config.put("StrictHostKeyChecking", "no");
			Session session=jsch.getSession(user, host, 22);  
			session.setConfig(config);
			session.setPassword(password);
			session.connect();  

			Channel channel=session.openChannel("exec");  
			((ChannelExec)channel).setCommand(command);  
			channel.setInputStream(null);  
			InputStream in=channel.getInputStream();  
			channel.connect(30000);  

			byte[] tmp=new byte[1024];  
			while(true){  
				while(in.available()>0){  
					int i=in.read(tmp, 0, 1024);  
					if(i<0)break;  
					temp=(new String(tmp, 0, i));
				}  

				if(channel.isClosed()){  
					System.out.println("exit-status: "+channel.getExitStatus());  
					break;  
				}  
				try{Thread.sleep(1000);}catch(Exception ee){}  
			}  
			channel.disconnect();  
			session.disconnect();  
			return temp;
		} 
		catch(Exception e){  
			System.out.println(e);  
		}
		return null;
	}
	
	public PhiServerBean ServerDriveInfo(PhiServerBean p1)
	{
		String temp=null; String [] disk;
		String Disk_size_command="df -hP " + p1.getDrive() + " | grep \"" + p1.getDrive() + "\" | awk '{print $2,$4}'";
		Pattern pattern = Pattern.compile("([0-9]*[/.]*[0-9]*[TGM])");

		temp=execute(p1.getUsername(),p1.getServer_name(),p1.getPwd(),Disk_size_command);
		
		if(temp!=null && !temp.equals(""))
		{
			disk=temp.split(" ");
			System.out.println("Total Disk Size:" + disk[0] + " Free Disk Space:" + disk[1]);
			Matcher matcher = pattern.matcher(disk[0]);
			if (matcher.find())
			{
				p1.setDspace_total(matcher.group(1));
				p1.setStatus("UP");
			}
			matcher = pattern.matcher(disk[1]);
			if (matcher.find())
			{
				p1.setDspace_free(matcher.group(1));
				temp=null;
			}
		}
		else 
		{
			p1.setStatus("DN");
			p1.setDspace_total("NA");
			p1.setDspace_free("NA");
		}
		
		return p1;
	}
}