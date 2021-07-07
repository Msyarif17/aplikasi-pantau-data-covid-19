package pbo;

import java.io.*;
import java.io.IOException;
import java.net.URL;

public class Request {
	
	public void request(String country){
		

		BufferedInputStream in;
		
		FileOutputStream fileOutputStream;
		
			try{
				
				in = new BufferedInputStream(new URL("https://api.covidtracking.com/v1/states/"+country+"/daily.csv")
					.openStream()
					);
				fileOutputStream = new FileOutputStream("dataCovid19.csv");
				byte dataBuffer[] = new byte[1024];
				int bytesRead;
				
				while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
					
					fileOutputStream.write(dataBuffer, 0, bytesRead);
					
				}
				System.out.println("\nSukses Request data Covid");
			}catch (IOException e) {
				System.out.println("ups... sepertinya terjadi kesalahan :\n"+e);
			}
				
	}
}
