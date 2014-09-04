
public class KMPMatch {

	  private String text;
	  private static String pattern;
	  private static int[] failure;
	  private int matchPoint;
	  
	 
	  /**
	   * Finds the first occurrence of the pattern in the text.
	   */
	  public boolean match() {
	    int j = 0;
	    if (text.length() == 0) return false;
	    
	    for (int i = 0; i < text.length(); i++) {
	      while (j > 0 && pattern.charAt(j) != text.charAt(i)) {
	        j = failure[j - 1];
	      }
	      if (pattern.charAt(j) == text.charAt(i)) { j++; }
	      if (j == pattern.length()) {
	        matchPoint = i - pattern.length() + 1;
	        return true;
	      }
	    }
	    return false;
	  }
	  
	  /** 
	   * Computes the failure function using a boot-strapping process,
	   * where the pattern is matched against itself.
	   */
	  public static void computeFailure() {
	    int j = 0;
	    for (int i = 1; i < pattern.length(); i++) {
	      while (j > 0 && pattern.charAt(j) != pattern.charAt(i)) { 
	        j = failure[j - 1];
	      }
	      if (pattern.charAt(j) == pattern.charAt(i)) { 
	        j++; 
	      }
	      failure[i] = j;
	    }
	  }
	  
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		pattern = "ATCGATATAGCATCATCGAT";
		
		failure = new int[20];
		
		computeFailure();
		
		for(int k=0;k<failure.length;k++)
		{
			System.out.print(failure[k] + ",");
		}
	}

}