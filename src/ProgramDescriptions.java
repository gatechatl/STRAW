import mpest.CreateSNAFileMPEST;

/**
 * Using specific keyword to query the program information.
 * @author tshaw
 *
 */
public class ProgramDescriptions {

	
	public static String generateProgramInfo(String type) {
		String result = "#### List of programs in " + type + " ####\n";
		
		if (CreateSNAFileMPEST.type().equals(type)) {
			result += " -CreateSNAFileMPEST: " + CreateSNAFileMPEST.description() + "\n";
		}
		
		return result;
	}
}
