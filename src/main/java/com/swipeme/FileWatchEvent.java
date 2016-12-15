package com.swipeme;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.FileSystems;
import java.nio.file.WatchService;
import java.nio.file.WatchKey;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.io.IOException;
import static java.nio.file.StandardWatchEventKinds.*;
import java.util.List;

public class FileWatchEvent {

	 public static void main(String [] args)
	            throws Exception {
		
	        new FileWatchEvent().doWatch();
	    }

	    @SuppressWarnings("unchecked")	
	    private void doWatch()
	            throws IOException, InterruptedException {
		
	        WatchService watchService = FileSystems.getDefault().newWatchService();
			
	        Path path = Paths.get("E:\\SwipemeLatestApp\\SwipemeServices\\src\\main\\resources\\db");
	        WatchKey watchKey = path.register(watchService, ENTRY_DELETE, ENTRY_MODIFY);
										
	        System.out.println("Watch service registered dir: " + path.toString());
										
	        for (;;) {
			
	            WatchKey key; 

	            try {
	                System.out.println("Waiting for key to be signalled...");
	                key = watchService.take();
	            }
	            catch (InterruptedException ex) {
	                System.out.println("Interrupted Exception");
	                return;
	            }
				
	            List<WatchEvent<?>> eventList = key.pollEvents();
	            System.out.println("Process the pending events for the key: " + eventList.size());

	            for (WatchEvent<?> genericEvent: eventList) {

	                WatchEvent.Kind<?> eventKind = genericEvent.kind();
	                System.out.println("Event kind: " + eventKind);

	                if (eventKind == OVERFLOW) {

	                    continue; // pending events for loop
	                }

	                WatchEvent pathEvent = (WatchEvent) genericEvent;
	                Path file = (Path)pathEvent.context();
	                System.out.println("File name: " + file.toString());
	                if(eventKind != ENTRY_DELETE){
	                	StartBatchProcess.startJob();
	                }
	                
	                
	                
	                
	            } 

	            boolean validKey = key.reset();
	            System.out.println("Key reset");
	            System.out.println("");

	            if (! validKey) {
	                System.out.println("Invalid key");
	                break; // infinite for loop
	            }

	        } // end infinite for loop
			
	        watchService.close();
	        System.out.println("Watch service closed.");
	    }	
	}