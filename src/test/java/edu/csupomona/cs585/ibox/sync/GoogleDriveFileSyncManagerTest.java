/*
 * CS585 Software Verification and validation  - Fall 2016
 * Programming Assignment 1
 * Auther: Kevin Ali
 * Professor:Yu Sun
 */


package edu.csupomona.cs585.ibox.sync;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.Drive.Files.List;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import edu.csupomona.cs585.ibox.sync.GoogleDriveFileSyncManager;

public class GoogleDriveFileSyncManagerTest {
	
	private Drive mDrive;
	private Files mfiles;
	private List mlist;
	private GoogleDriveFileSyncManager FileSyncManager;
	private java.io.File localFile;
	private File file;
	private FileList file_listing;
	
	@Before
	public void setup() {
		mDrive = mock(Drive.class);		
		mfiles = mock(Files.class);
		mlist = mock(List.class);
		FileSyncManager = new GoogleDriveFileSyncManager(mDrive);
		localFile = new java.io.File("C:\\Test\\test");
		
		//file is used for a fake file.	
		file = new File();
		file.setId("test");
		file.setTitle("test");
		
		//file listing is needed to fake the listing of the file.
		file_listing = new FileList();
		java.util.List<File> list = new java.util.ArrayList<File>();
		list.add(file);
		file_listing.setItems(list);
	}
	
	
	/*
	 * Checks if the service is up and not null.
	 */
	@Test
	public void testGoogleDriveFileSyncManager() {
		assertNotNull(FileSyncManager.service);
	}
	/*
	 * Test the addfile function checking if insert is executed properly 
	 */
	@Test
	public void testAddFile() throws IOException {
	
		Files.Insert insert = mock(Files.Insert.class);
		
		when(mDrive.files()).thenReturn(mfiles);
		when(mfiles.insert(any(File.class), any(FileContent.class))).thenReturn(insert);
		when(insert.execute()).thenReturn(file);
				
		FileSyncManager.addFile(localFile);
		verify(insert).execute();
	}
	
	/*
	 * Test the updatefile function checking if update is executed properly 
	 */
	@Test()
	public void testUpdateFile() throws IOException {
		
		Files.Update update = mock(Files.Update.class);
		
		when(mDrive.files()).thenReturn(mfiles);
		when(mfiles.list()).thenReturn(mlist);
		when(mlist.execute()).thenReturn(file_listing);
		when(mfiles.update(any(String.class),any(File.class), any(FileContent.class))).thenReturn(update);
		when(update.execute()).thenReturn(file);
				
		FileSyncManager.updateFile(localFile);
		verify(update).execute();
		
	}

	/*
	 * Test the deletefile function checking if delete is executed properly 
	 */
	@Test()
	public void testDeleteFile() throws IOException {
		
		Files.Delete delete = mock(Files.Delete.class);

		when(mDrive.files()).thenReturn(mfiles);
		when(mfiles.list()).thenReturn(mlist);
		when(mlist.execute()).thenReturn(file_listing);
		when(mfiles.delete(any(String.class))).thenReturn(delete);
		
		FileSyncManager.deleteFile(localFile);
		verify(delete).execute();
	}
	

	/*
	 * Test the getfileid function checking if it is able to retrieve the proper fileid. 
	 */
	@Test
	public void testGetFileId() throws IOException {

		when(mDrive.files()).thenReturn(mfiles);
		when(mfiles.list()).thenReturn(mlist);
		when(mlist.execute()).thenReturn(file_listing);
		
		assertEquals("test", FileSyncManager.getFileId("test"));
	}


}



	


