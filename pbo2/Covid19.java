import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.*;
class ConsoleHelper {
	private String lastLine = "";

	public void print(String line) {

		if (lastLine.length() > line.length()) {
			String temp = "";
			for (int i = 0; i < lastLine.length(); i++) {
				temp += " ";
			}
			if (temp.length() > 1)
				System.out.print("\r" + temp);
		}
		System.out.print("\r" + line);
		lastLine = line;
	}

	private byte anim;

	public void animate(String line) {
		switch (anim) {
			case 1:
			print("[ \\ ] " + line);
			break;
			case 2:
			print("[ | ] " + line);
			break;
			case 3:
			print("[ / ] " + line);
			break;
			default:
			anim = 0;
			print("[ - ] " + line);
		}
		anim++;
	}
}



class ReadCSV 
{  
	public static void read()   
	{  

		String line = "";  
		String splitBy = ","; 
		int i = 0; 
		try   
		{
			BufferedReader br = new BufferedReader(new FileReader("dataCovid19.csv"));
			while ((line = br.readLine()) != null)    
			{
				String[] data = line.split(splitBy);
				String data1 = String.format("|%-12s", data[0]);
				String data2 = String.format("|%-12s", data[2]);
				String data3 = String.format("|%-9s", data[19]);
				String data4 = String.format("|%-17s|", data[26]);
				String data5 = String.format("%-17s|", data[16]);
				if (i==0) {
					for (int k = 0;k<78 ;k++ ) {
						System.out.print("=");
					}
					System.out.println("\n"+String.format("|%-4s","No")+data1+data2+data3+data4+data5);
					for (int k = 0;k<78 ;k++ ) {
						System.out.print("=");
					}
					System.out.print("\n");
					i++;
				}
				else{
					System.out.println(String.format("|%-4s",i)+data1+data2+data3+data4+data5);
					for (int k = 0;k<78 ;k++ ) {
						if (k==5||k==data1.length() +5 ||k==5 + data2.length() + data1.length() ||k== 5 + data3.length() + data2.length() + data1.length() ||k== 17 + data4.length() +data3.length()  + data1.length()||k==0||k==77 ) {
							System.out.print("+");
						}
						else{
							System.out.print("-");
						}

					}
					System.out.print("\n");
					i++;
				}
			}  
		}   
		catch (IOException e)   
		{  
			e.printStackTrace();  
		}  
	}  
} 



class Data{
	private boolean f;
	public String locationThisFile(){
		String location = "";
		try {
			File file =  new File(Covid19
				.class
				.getProtectionDomain()
				.getCodeSource()
				.getLocation()
				.toURI()
				.getPath());
			location = file.getAbsolutePath();
		} catch (Exception e1) {

			e1.printStackTrace();
		} 
		return location;
	}
	public boolean searchFile(){
		
		SearchFile searcher = new SearchFile(new File(locationThisFile()));
		searcher.dfs();
		if (searcher.isFounded()) {
			f = searcher.isFounded();
			return true;
		}

		return false;
	}
	public void request(String country){
		

		BufferedInputStream in;
		System.out.println(searchFile()+""+f);
		FileOutputStream fileOutputStream;
		if (!searchFile()) {
			try{
				ConsoleHelper consoleHelper = new ConsoleHelper();
				in = new BufferedInputStream(new URL("https://api.covidtracking.com/v1/states/"+country+"/daily.csv")
					.openStream()
					);
				fileOutputStream = new FileOutputStream("dataCovid19.csv");
				byte dataBuffer[] = new byte[1024];
				int bytesRead;
				int i = 0;
				while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
					consoleHelper.animate(i + "Kb");
					fileOutputStream.write(dataBuffer, 0, bytesRead);
					i++;
				}
				System.out.println("\nSukses Request data Covid");
			}catch (IOException e) {
				System.out.println("ups... sepertinya terjadi kesalahan :\n"+e);
			}
		}
		else{
			System.out.println("Data Covid sudah ada");
		}		
	}
}

class SearchFile extends Data{
	private File root;
	private boolean found;
	private Map<String, Boolean> map = new HashMap<String, Boolean>();

	public SearchFile(File root){
		this.root = root;
		found = false;
	}
	
	private String[] getTargetFiles(File directory){
		if(directory == null){
			return null;
		}

		String[] files = directory.list(new FilenameFilter(){

			@Override
			public boolean accept(File dir, String name) {

				return name.startsWith("dataCovid19") && name.endsWith(".csv");
			}

		});
		for(String target: files)
		{
			System.out.println();
		}
		return files;
	}

	private void respon(String[] targets){
		for(String target: targets){
			System.out.println(target);
			if (target.equals("dataCovid19.csv")){
				found = true;

			}
		}
		
	}
	public boolean isFounded(){

		return this.found;
	}
	public void dfs(){//algorithma Deept First Search

		if(this.root == null){
			return;
		}

		Stack<File> stack = new Stack<File>();
		stack.push(root);
		map.put(root.getAbsolutePath(), true);
		while(!stack.isEmpty()){
			File node = stack.peek();
			File child = getUnvisitedChild(node);

			if(child != null){
				stack.push(child);
				respon(getTargetFiles(child));
				map.put(child.getAbsolutePath(), true);
			}else{
				stack.pop();
			}

		}
	}
	private File getUnvisitedChild(File node){

		File[] childs = node.listFiles(new FileFilter(){

			@Override
			public boolean accept(File pathname) {
				if(pathname.isDirectory())
					return true;

				return false;
			}

		});

		if(childs == null){
			return null;
		}

		for(File child: childs){

			if(map.containsKey(child.getAbsolutePath()) == false){
				map.put(child.getAbsolutePath(), false);
			}

			if(map.get(child.getAbsolutePath()) == false){
				return child; 
			}
		}

		return null;
	}
}



public class Covid19{
	public static void menu(){
		System.out.println("   ___           _     _       _  ___  _                  _             		");
		System.out.println("  / __\\_____   _(_) __| |     / |/ _ \\| |_ _ __ __ _  ___| | _____ _ __ 		");
		System.out.println(" / /  / _ \\ \\ / / |/ _` |_____| | (_) | __| '__/ _` |/ __| |/ / _ \\ '__|		");
		System.out.println("/ /__| (_) \\ V /| | (_| |_____| |\\__, | |_| | | (_| | (__|   <  __/ |   		");
		System.out.println("\\____/\\___/ \\_/ |_|\\__,_|     |_|  /_/ \\__|_|  \\__,_|\\___|_|\\_\\___|_| 	\n");
		System.out.println("=========================================================");

	}
	public static void cl(){
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
	public static void br(){
		Scanner scanner = new Scanner(System.in);
		String readString = "";
		readString += scanner.nextLine();
		
	}
	public static void main(String[] args) {
		String[] menu = {
			"Download / Update data covid global ",
			"tampilkan data",
			"cari info berdasarkan negara (conto: Indonesia = id)",
			
			"exit()"
		};
		boolean exit = false;
		String negara = "";
		Data data = new Data();
		ReadCSV csv =new ReadCSV();
		Scanner input = new Scanner(System.in);
		while(!exit)
		{
			cl();
			menu();
			for (int i = 1;i<=menu.length ;i++ ) {
				System.out.println("[ "+i+" ]   "+ menu[i-1]);
			}

			System.out.print("masukan pilihan (angka):  ");
			int pilih = input.nextInt();

			switch(pilih){
				case 1 :
				if (negara.equals("")) {
					negara = "id";
				}
				System.out.println("memproses data..");
				data.request(negara);
				br();
				break;
				case 2 :
				cl();
				menu();
				if (negara.equals("")) {
					negara = "id";

				}
				System.out.println("Loading data covid 19 di negara \""+negara+"\"....(default Indonesia)");
				csv.read();
				br();
				break;
				case 3 :
				System.out.print("masukan inisial negara (misal Indonesia inisial nya id) :   ");
				negara = input.next();
				System.out.println("memproses data..");
				data.request(negara);
				br();
				break;
				case 4 :
				System.out.print("Terimakasih!");
				
				exit = true;
				break;
				default:{
					System.out.print("keyword yang kamu masukan sepertinya salah");
					br();
				}


			}
		}

		/*;*/

	}
}