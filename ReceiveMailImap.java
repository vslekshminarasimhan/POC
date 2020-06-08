package com.tfg.redis.email;

import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

public class ReceiveMailImap {

	public static void doit() throws MessagingException, IOException {
	    Folder folder = null;
	    Store store = null;
	    try {
	      Properties props = System.getProperties();
	      props.setProperty("mail.store.protocol", "imaps");

	      Session session = Session.getDefaultInstance(props, null);
	      // session.setDebug(true);
	      store = session.getStore("imaps");
	      store.connect("imap.gmail.com","vslakshminarasimhan@gmail.com", "forestroad");

	      folder = store.getFolder("Inbox");	     
	      folder.open(Folder.READ_WRITE);

	      Flags seen = new Flags(Flags.Flag.SEEN);
	      FlagTerm unseenFlagTerm = new FlagTerm(seen, true);
	      
	      Message messages[] = folder.getMessages();
	      
	   //    Message messages[] = folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), true));
	      
	      System.out.println("No of Messages : " + messages.length);
	
			  for(int j=0; j<messages.length; j++) {
			  if(messages[j].getSubject().contentEquals("SpringBoot1")) {
			  System.out.println("Found one unread SpringBoot1"); } }
			 
			 SearchTerm subjectTerm = new SubjectTerm("SpringBoot1");
				Message[] foundMessages = folder.search(subjectTerm);

				for (int i=0; i < foundMessages.length; i++) {
					Message msg = foundMessages[i];
					System.out.println("The content from mail"+msg.getContentType());
					msg.setFlag(Flags.Flag.SEEN, true);
				}
	    }

	    finally {
	      if (folder != null) { folder.close(true); }
	      if (store != null) { store.close(); }
	    }
	    System.out.println("ReceiveEmail process is completed.");
	  }

	  public static void main(String args[]) throws Exception {
	    ReceiveMailImap.doit();
	  }
}
