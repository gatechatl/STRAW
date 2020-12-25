import java.util.Iterator;
import java.util.LinkedList;

import NJst.BootstrapSpeciesTreeNJstLowMem;
import STAR.BootstrapSpeciesTreeSTARLowMem;
import TreeManipulation.RemoveEdgeDistance;
import TreeVector.TreeVector;
import general.CalculateTripleDistant;
import general.CreateInputMatrix;
import general.CreateSNAFile;
import general.CreateSNAFileComplex;
import general.GenerateTable;
import general.GenerateTableMPEST;
import general.ReplaceHTMLFile;
import mpest.BootstrapSpeciesTreeMPESTLowMem;
import mpest.CreateSNAFileMPEST;

/**
 * Collection of scripts for the STRAW server
 * Last updated 2016-10-24
 * 
 * @author Timothy Shaw
 * 
 */
public class STRAW {

	public static void main(String[] args) {
		try {

			if (args.length <= 0) {
				System.out.println("Not enough argument");
				printProgramInfo();
				System.exit(0);
			}

			String type = args[0];
			if (type.equals("-CreateSNAFileMPEST")) {
				String[] args_remain = getRemaining(args);
				if (args_remain.length == 0) {
					System.out.println(CreateSNAFileMPEST
							.parameter_info());
					System.exit(0);
				}
				CreateSNAFileMPEST.execute(args_remain);
			} else if (type.equals("-GenerateTableMPEST")) {
				String[] args_remain = getRemaining(args);
				if (args_remain.length == 0) {
					System.out.println(GenerateTableMPEST
							.parameter_info());
					System.exit(0);
				}
				GenerateTableMPEST.execute(args_remain);			
			} else if (type.equals("-GenerateTable")) {
				String[] args_remain = getRemaining(args);
				if (args_remain.length == 0) {
					System.out.println(GenerateTable
							.parameter_info());
					System.exit(0);
				}
				GenerateTable.execute(args_remain);			
			} else if (type.equals("-CalculateTripleDistant")) {
				String[] args_remain = getRemaining(args);
				if (args_remain.length == 0) {
					System.out.println(CalculateTripleDistant
							.parameter_info());
					System.exit(0);
				}
				CalculateTripleDistant.execute(args_remain);			
			} else if (type.equals("-ReplaceHTMLFile")) {
				String[] args_remain = getRemaining(args);
				if (args_remain.length == 0) {
					System.out.println(ReplaceHTMLFile
							.parameter_info());
					System.exit(0);
				}
				ReplaceHTMLFile.execute(args_remain);			
			} else if (type.equals("-CreateSNAFile")) {
				String[] args_remain = getRemaining(args);
				if (args_remain.length == 0) {
					System.out.println(CreateSNAFile
							.parameter_info());
					System.exit(0);
				}
				CreateSNAFile.execute(args_remain);			
			} else if (type.equals("-CreateInputMatrix")) {
				String[] args_remain = getRemaining(args);
				if (args_remain.length == 0) {
					System.out.println(CreateInputMatrix
							.parameter_info());
					System.exit(0);
				}
				CreateInputMatrix.execute(args_remain);			
			} else if (type.equals("-BootstrapSpeciesTreeMPESTLowMem")) {
				String[] args_remain = getRemaining(args);
				if (args_remain.length == 0) {
					System.out.println(BootstrapSpeciesTreeMPESTLowMem
							.parameter_info());
					System.exit(0);
				}
				BootstrapSpeciesTreeMPESTLowMem.execute(args_remain);			
				// TreeVector
			} else if (type.equals("-TreeVector")) {
				String[] args_remain = getRemaining(args);
				if (args_remain.length == 0) {
					System.out.println(TreeVector
							.parameter_info());
					System.exit(0);
				}
				TreeVector.execute(args_remain);			
				// BootstrapSpeciesTreeNJstLowMem
			} else if (type.equals("-BootstrapSpeciesTreeNJstLowMem")) {
				String[] args_remain = getRemaining(args);
				if (args_remain.length == 0) {
					System.out.println(BootstrapSpeciesTreeNJstLowMem
							.parameter_info());
					System.exit(0);
				}
				BootstrapSpeciesTreeNJstLowMem.execute(args_remain);			
				// BootstrapSpeciesTreeSTARLowMem
			} else if (type.equals("-BootstrapSpeciesTreeSTARLowMem")) {
				String[] args_remain = getRemaining(args);
				if (args_remain.length == 0) {
					System.out.println(BootstrapSpeciesTreeSTARLowMem
							.parameter_info());
					System.exit(0);
				}
				BootstrapSpeciesTreeSTARLowMem.execute(args_remain);			
				// CreateSNAFileComplex
			} else if (type.equals("-CreateSNAFileComplex")) {
				String[] args_remain = getRemaining(args);
				if (args_remain.length == 0) {
					System.out.println(CreateSNAFileComplex
							.parameter_info());
					System.exit(0);
				}
				CreateSNAFileComplex.execute(args_remain);			
				// RemoveEdgeDistance
			} else if (type.equals("-RemoveEdgeDistance")) {
				String[] args_remain = getRemaining(args);
				if (args_remain.length == 0) {
					System.out.println(RemoveEdgeDistance
							.parameter_info());
					System.exit(0);
				}
				RemoveEdgeDistance.execute(args_remain);			
				// 
			} else {
				System.out.println("Here are the available programs");
				printProgramInfo();
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static String[] getRemaining(String[] args) {
		String[] args_remain = new String[args.length - 1];
		boolean startQuotation = false;
		LinkedList list = new LinkedList();
		String longStr = "";

		for (int i = 1; i < args.length; i++) {
			// System.out.println("args: " + args[i]);
			if (args[i].contains("\"") && !startQuotation) {
				startQuotation = true;
				longStr = args[i];
			} else if (args[i].contains("\"") && startQuotation) {
				longStr += " " + args[i];
				longStr.replaceAll("\"", "");
				list.add(longStr);
				startQuotation = false;
			} else {
				list.add(args[i]);
			}
		}

		int i = 0;
		Iterator itr = list.iterator();
		while (itr.hasNext()) {
			String str = (String) itr.next();
			args_remain[i] = str;
			// System.out.println(args_remain[i]);
			i++;
		}
		return args_remain;
	}
	public static void printProgramInfo() {
		System.out.println("STRAW Version Number: " + ProgramInfo.VERSION);
		System.out
				.println("Main Categories of STRAW functions");
		
	}
	
	
}

