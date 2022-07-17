import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) throws IOException{
		HashMap<String,String> val = new HashMap<>();
		File directoryPath = new File("C:\\Users\\sneha\\Desktop\\DEMO");
		PrintWriter writer = new PrintWriter("C:\\Users\\sneha\\Desktop\\test.csv");
		
		StringBuilder header = new StringBuilder();
        header.append("File");
        header.append(',');
        header.append("Sum of electronic and zero-point Energies");
        header.append(',');
        header.append("Sum of electronic and thermal Energies");
        header.append(',');
        header.append("Sum of electronic and thermal Enthalpies");
        header.append(',');
        header.append("Sum of electronic and thermal Free Energies");
        header.append(',');
        header.append("Zero-point correction");
        header.append(',');
        header.append("MP2");
        header.append(',');
        header.append("HF");
        header.append(',');
        header.append("Rx");
        header.append(',');
        header.append("Ry");
        header.append(',');
        header.append("Rz");
        header.append(',');
        header.append("Mu x");
        header.append(',');
        header.append("Mu y");
        header.append(',');
        header.append("Mu z");
        header.append(',');
        header.append("Frequencies");
        header.append(',');
        header.append("IR Inten");
        header.append('\n');
        writer.write(header.toString());
        
        
		File filesList[] = directoryPath.listFiles();
	      System.out.println("List of files and directories in the specified directory:");
	      Scanner sc = null;
	      
	      
	      for(File file : filesList) {
	         System.out.println("File name: "+file.getName());
	         System.out.println("File path: "+file.getAbsolutePath());
	         sc= new Scanner(file);
	         String input;
	         StringBuffer sb = new StringBuffer();
	         boolean flag = false;
	         int id = 0;
	         ArrayList<String> freq = new ArrayList();
	         ArrayList<String> ir = new ArrayList();
	         int idr=0;
	         while (sc.hasNextLine()) {
	            input = sc.nextLine();
	            if(input.contains("Sum of electronic and zero-point Energies")) {
	            	String a[] = input.split(" ");
	            	val.put("Sum of electronic and zero-point Energies", a[a.length-1]);
	            }
	            if(input.contains("Sum of electronic and thermal Energies")) {
	            	String a[] = input.split(" ");
	            	val.put("Sum of electronic and thermal Energies", a[a.length-1]);
	            }
	            if(input.contains("Sum of electronic and thermal Enthalpies")) {
	            	String a[] = input.split(" ");
	            	val.put("Sum of electronic and thermal Enthalpies",a[a.length-1]);
	            }
	            if(input.contains("Sum of electronic and thermal Free Energies")) {
	            	String a[] = input.split(" ");]
	            	val.put("Sum of electronic and thermal Free Energies",a[a.length-1]);
	            }
	            //Zero-point correction
	            if(input.contains("Zero-point correction")){
	            	String a[] = input.split(" ");
	            	val.put("Zero-point correction", a[a.length-2]);
	            }
	            if(input.contains("Rotational constants (GHZ):")) {
	            	String a[] = input.split("\\s+");
	            	if(val.containsKey("Rotational constants (GHZ):")==false) {
	            		val.put("Rotational constants (GHZ):", (a[4].concat(" "+a[5])).concat(" "+a[6]));
	            	}else {
	            		val.replace("Rotational constants (GHZ):", (a[4].concat(" "+a[5])).concat(" "+a[6]));
	            	}
	            }
	            if(input.contains("Harmonic frequencies")) {
	            	flag = true;
	            }
	            if(flag && input.contains("Frequencies -- ")) {
	            	String a[] = input.split("\\s+");
	            	freq.add(id++, a[3]);
	            	freq.add(id++, a[4]);
	            	freq.add(id++, a[5]);
	            }
	            if(flag && input.contains("IR Inten")) {
	            	String a[] = input.split("\\s+");
	            	ir.add(idr++, a[4]);
	            	ir.add(idr++, a[5]);
	            	ir.add(idr++, a[6]);
	            }
	            if(input.contains("Version=")) {
	            	String temp = input;
	            	boolean hf = false;
	            	boolean mp2 = false;
	            	boolean dipole = false;
	            	while(!(hf&&mp2&&dipole) && sc.hasNextLine()) {
	            		String t1 = sc.nextLine();
	            		if(t1.contains("HF"))
	            			hf = true;
	            		if(t1.contains("MP2"))
	            			mp2 = true;
	            		if(t1.contains("Dipole"))
	            			dipole=true;
	            		temp = temp.concat(t1);
	            	}
	            	ArrayList<String> a = new ArrayList<String>(Arrays.asList(temp.split("\\\\")));
	            	for(int i = 0; i<a.size(); i++) {
	            		if(a.get(i).contains("HF")) {
	            			String t[] = a.get(i).split("=");
	            			t[1] = t[1].replaceAll("\\s", "");
	            			if(val.containsKey("Dipole")==false) {
	            				val.put("HF", t[1]);
	            			}
	            		}
	            		if(a.get(i).contains("MP2")) {
	            			String t[] = a.get(i).split("=");
	            			t[1] = t[1].replaceAll("\\s", "");
	            			if(val.containsKey("Dipole")==false) {
	            				val.put("MP2", t[1]);
	            			}
	            		}
	            		if(a.get(i).contains("Dipole=")) {
	            			String t[] = a.get(i).split("=");
	            			if(val.containsKey("Dipole")==false) {
	            				val.put("Dipole",t[1]);
	            			}
	            			break;
	            		}
	            	}
	            }
	            
	         }
	         //Console Output
	         val.entrySet().forEach(e -> {
	        	 System.out.println(e.getKey()+" "+e.getValue());
	         });
	         System.out.println("\nFrequencies\n");
	         for(int i = 0; i<freq.size(); i++) {
	        	 System.out.println(i+1+"\t"+freq.get(i)+"\t"+ir.get(i));
	         }
	         //Adding to CSV
	         StringBuilder str = new StringBuilder();
	         str.append(file.getName());
	         str.append(',');
	         str.append(val.get("Sum of electronic and zero-point Energies"));
	         str.append(',');
	         str.append(val.get("Sum of electronic and thermal Energies"));
	         str.append(',');
	         str.append(val.get("Sum of electronic and thermal Enthalpies"));
	         str.append(',');
	         str.append(val.get("Sum of electronic and thermal Free Energies"));
	         str.append(',');
	         str.append(val.get("Zero-point correction"));
	         str.append(',');
	         str.append(val.get("MP2"));
	         str.append(',');
	         str.append(val.get("HF"));
	         str.append(',');
	         String temp[] = val.get("Rotational constants (GHZ):").split("\\s");
	         str.append(temp[0]);
	         str.append(',');
	         str.append(temp[1]);
	         str.append(',');
	         str.append(temp[2]);
	         str.append(',');
	         str.append(val.get("Dipole"));
	         str.append(',');
	      
	         for(int i = 0; i<freq.size(); i++) {
	        	 if(i==0)
	        	 {
	        		 str.append(freq.get(i));
	        		 str.append(',');
	        		 str.append(ir.get(i));
		        	 str.append('\n');
		        	 continue;
	        	 }
	        	 for(int j=0; j<14; j++) {
	        		 str.append(',');
	        	 }
	        	 str.append(freq.get(i));
	        	 str.append(',');
	        	 str.append(ir.get(i));
	        	 str.append('\n');
	         }
	         str.append('\n');
	         writer.write(str.toString());
	         val.clear();
	         
	}
	      writer.close();
	      

}
}