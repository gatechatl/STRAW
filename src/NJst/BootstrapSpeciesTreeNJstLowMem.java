package NJst;

import java.io.* ;
import java.util.HashMap;
import java.util.Iterator;

/**
 * A simple class to demonstrate a recursive directory traversal
 * in Java.
 *
 * Error handling was left out to make the code easier to understand.
 * In production code, you should check if the arguments from the
 * command line are really file files or directories.
 */
public class BootstrapSpeciesTreeNJstLowMem
{
   /**
    * Works on a single file system entry and
    * calls itself recursively if it turns out
    * to be a directory.
    * @param file A file or a directory to process
    */
   public String traverse( File file )
   {
      // Print the name of the entry
      //System.out.println( file ) ;
      String fileName = file.getPath();
      // Check if it is a directory
      if( file.isDirectory() )
      {
         // Get a list of all the entries in the directory
         String entries[] = file.list() ;

         // Ensure that the list is not null
         if( entries != null )
         {
            // Loop over all the entries
            for( String entry : entries )
            {
               // Recursive call to traverse
               fileName += "\t" + traverse( new File(file,entry) ) ;
            }
         }
      }
      return fileName;
   }

	public static String type() {
		return "NJst";
	}
	public static String description() {
		return "Perform NJst Bootstrap.";
	}
	public static String parameter_info() {
		return "[ZipOutput/ folder] [Bootstrap/ folder] [FinalOutput.tre]";
	}
   /**
    * The program starts here.
    * @param args The arguments from the command line
    */
   public static void execute( String args[] )
   {
      // Create an object of this class
      BootstrapSpeciesTreeNJst rt = new BootstrapSpeciesTreeNJst() ;

      
      // If there are no arguments, traverse the current directory
      String bootstrapFolder = args[1];
      String outputFinalTree = args[2];
      String files = rt.traverse( new File(args[0]) ) ;
      System.out.println(files);
      String[] split = files.split("\t");
      try {
         HashMap map = new HashMap();
         int count = 0;
         //for (int i = 0; i < split.length; i++) {
	 //	System.out.println(split[i]);
         //}
         for (int i = 0; i < split.length; i++) {
            
            String fileName = split[i];
            File file = new File(fileName);
            
            if (!(fileName.contains("DS_Store") || fileName.contains("__MACOSX")) && !file.isDirectory()) {
                String[] split2 = fileName.split("\\.");
                String fileType = split2[split2.length - 1];
                if (fileType.contains("boot") || fileType.contains("bs") || fileType.contains("nwk") || fileType.contains("tre") || fileType.contains("new") || fileType.contains("txt") || fileType.contains("phylip") || fileType.contains("out") || fileType.contains("phy")) {
                                                        
                    int min = 0;
                    FileWriter fwriter = new FileWriter(fileName + ".oneline.tre");
                    BufferedWriter out = new BufferedWriter(fwriter);
                     
                    FileInputStream fstream = new FileInputStream(fileName);
                    DataInputStream din = new DataInputStream(fstream); 
                    BufferedReader in = new BufferedReader(new InputStreamReader(din));         
                    while (in.ready()) {
                        String str = in.readLine();
                        str = str.replaceAll(";", ";\n");
                        out.write(str);
                    }
                    in.close();
                    out.close();

                    String trees = "";
                    fstream = new FileInputStream(fileName + ".oneline.tre");
                    din = new DataInputStream(fstream); 
                    in = new BufferedReader(new InputStreamReader(din));            
                    while (in.ready()) {
                        String str = in.readLine();
                        min++;
                        if (trees.equals("")) {
                            trees += str;
                        } else {
                            trees += "\t" + str;
                        }
                    }
                    in.close();
                    if (count == 0) {
                        count = min;
                    }
                    if (count > min) {
                        count = min;
                    }
                    //String temp = trees.toString();
                    String[] split4 = trees.split("\t");

                    for (int k = 0; k < split4.length; k++) {
                    //FileWriter fwriter = new FileWriter(bootstrapFolder + "/speciesTree" + i + ".tre");
                    //BufferedWriter out = new BufferedWriter(fwriter);
                        PrintWriter out3 = new PrintWriter(new BufferedWriter(new FileWriter(bootstrapFolder + "/speciesTree" + k + ".tre", true)));
                        out3.println(split4[k]);
                        out3.close();
                    }


                    //map.put(fileName + ".oneline.tre", trees);                  
                }
            }
         }        
         String finalTrees = "";
         for (int i = 0; i < count; i++) {
            /*FileWriter fwriter = new FileWriter(bootstrapFolder + "/speciesTree" + i + ".tre");
            BufferedWriter out = new BufferedWriter(fwriter);

            FileWriter fwriter2 = new FileWriter(bootstrapFolder + "/format_input_pre.tre");
            BufferedWriter out2 = new BufferedWriter(fwriter2);
             Iterator itr = map.keySet().iterator();
             while (itr.hasNext()) {
                    String fileName = (String)itr.next();
                    String trees = (String)map.get(fileName);
                    String[] tree = trees.split("\t");
                    //System.out.println(trees);
                    out.write(tree[i] + "\n");
                    out2.write(tree[i] + "\n");
                    
             }
             out.close();
             out2.close();*/
	     executeCommand(bootstrapFolder, "cp -r " + bootstrapFolder + "/speciesTree" + i + ".tre " + bootstrapFolder + "/format_input_pre.tre");
             executeCommand(bootstrapFolder, "./inputtree_rewrite.py " + bootstrapFolder + "/format_input_pre.tre > " + bootstrapFolder + "/format_input.tre");
             executeCommand(bootstrapFolder, "cp script.r " + bootstrapFolder + "/script.r");
             executeCommand(bootstrapFolder, "cd " + bootstrapFolder + "\n" + "R --vanilla < script.r \ncp output.tre output_" + i + ".tre\n");

             
            FileInputStream fstream = new FileInputStream(bootstrapFolder + "/output.tre");
            DataInputStream din = new DataInputStream(fstream); 
            BufferedReader in = new BufferedReader(new InputStreamReader(din));         
            while (in.ready()) {
                String str = in.readLine();
                finalTrees += str;
            }
            in.close();
            finalTrees += "\n";
         }

          FileWriter fwriter = new FileWriter(outputFinalTree);
          BufferedWriter out = new BufferedWriter(fwriter);  
          out.write(finalTrees);
          out.close();
      } catch (Exception e) {
          e.printStackTrace();
      }      
   }
    public static void executeCommand(String folder, String executeThis) {
        try {
            writeFile(folder + "/tempexecuteCommand.sh", executeThis);
            String[] command = {"sh", folder + "/tempexecuteCommand.sh"};
            Process p1 = Runtime.getRuntime().exec(command);                
            BufferedReader inputn = new BufferedReader(new InputStreamReader(p1.getInputStream()));            
            String line=null;
            while((line=inputn.readLine()) != null) {}                        
            inputn.close();
             
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void writeFile(String fileName, String command) {
        try {
            FileWriter fwriter2 = new FileWriter(fileName);
            BufferedWriter out2 = new BufferedWriter(fwriter2);
            out2.write(command + "\n");                 
            out2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
