
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.stream.Stream;
import java.util.Iterator;

       public class DaurPattern{
       
           public static void main(String[] args) throws IOException{
            List<String> dirs = new ArrayList<>();
	    String pattern = "";  // by default	    
		for(int i=0;i<args.length;i++){
		    if(args[i].equals("-dir")){
		        dirs.add(args[++i]);
		    }

		    if(args[i].equals("-pattern")){	    
		        pattern = args[++i];
		    }
       	} 
           if(dirs.size()==0){
	      System.out.println("Error");
	      System.out.printf("Usage this pattern : java -jar myjar.jar -dir /dir1  -dir /dir2 -pattern %s", "*.{so,md}");
	   }

             DaurPattern daur = new DaurPattern();    
              String glob = "glob:**/"+pattern;

                for(int i=0;i<dirs.size();i++){
		    daur.match(glob,dirs.get(i));
		}  

             
	   
	   }
          

	    private void match(String pattern,String path) throws IOException{
	                      
	        Path currentPath = Paths.get(path); // Path file current directory
                 
                if(!Files.exists(currentPath)){
		       System.out.println("Invalid directory " + path);
	               System.exit(1);	       
                 }
	         try(Stream<Path> files = Files.list(currentPath)){
	              PathMatcher matcher = null;		 
		   try{

		       matcher = currentPath.getFileSystem().getPathMatcher(pattern);

		   }catch(IllegalArgumentException e){
                          System.out.println("Invalid pattern! " + pattern.substring(8,pattern.length()));
			  System.exit(1);
		  
		   }
		    Iterator<Path> iterator = files.iterator();
		     while(iterator.hasNext()){
		       Path nextPath = iterator.next();
		         if(Files.isDirectory(nextPath)){
			     match(pattern,nextPath.toString());
			 }else{
			      if(matcher.matches(nextPath)){
			        System.out.println(nextPath);
			         }
			      }
		            } 
		   
		        }	
         
	           }
         } 
       
   
