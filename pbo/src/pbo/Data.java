package pbo;
import java.io.*;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class Data
{  
	public DefaultTableModel read()   
	{  
						// Create JTable
		
		DefaultTableModel model = new DefaultTableModel();
		
		JFileChooser fileopen = new JFileChooser();			// Choose File
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text/CSV file", "txt", "csv");
		fileopen.addChoosableFileFilter(filter);
		int ret = fileopen.showDialog(null, "pilih file");		// Open File
		if (ret == JFileChooser.APPROVE_OPTION ) {
			File file = fileopen.getSelectedFile();
			int i = 0; 
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));	// Read file
				String line;													// in Line
				int row = 0 ;													// Set row
				while ((line = br.readLine()) != null ) {	
												// split ','  (CSV file)
					String[] data = line.split(",");
					String data1 = data[0];
					String data2 =  data[2];
					String data3 = data[19];
					String data4 = data[26];
					String data5 = data[16];
					String[] value = {String.valueOf(i),data1,data2,data3,data4,data5};
					if (i==0) {
						model.addColumn("No");
						model.addColumn(data1);
						model.addColumn(data2);
						model.addColumn(data3);
						model.addColumn(data4);
						model.addColumn(data5);
						
					}
					else{
						model.insertRow(row,value);
						
						row++;
						
					}i++;							// add data third to row2
					
				}
				br.close();										
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
         return model;
	} 
}
