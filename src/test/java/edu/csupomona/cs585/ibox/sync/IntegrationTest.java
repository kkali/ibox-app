/*
 * CS585 Software Verification and validation  - Fall 2016
 * Programming Assignment 1
 * Auther: Kevin Ali
 * Professor:Yu Sun
 */
package edu.csupomona.cs585.ibox.sync;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import edu.csupomona.cs585.ibox.sync.GoogleDriveFileSyncManager;
import edu.csupomona.cs585.ibox.sync.GoogleDriveServiceProvider;


public class IntegrationTest {
	private GoogleDriveFileSyncManager FileSyncManager;
	
	@Before
	public void setup() {
        FileSyncManager = new GoogleDriveFileSyncManager(GoogleDriveServiceProvider.get().getGoogleDriveClient());
	}
	@Ignore
	@Test
	public void testAddFile() throws InterruptedException {
		String fileName = "J:\\Document\\GitHubProject\\ibox-app\\test";
		java.io.File localFile = new java.io.File(fileName);
		
		try {
			localFile.createNewFile();
			FileSyncManager.addFile(localFile);
			assertTrue(FileSyncManager.fileExists("test"));
			FileSyncManager.deleteFile(localFile);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			localFile.delete();
		}
	}
	@Ignore
	@Test 
	public void testDeleteFile() {
		String fileName = "J:\\Document\\GitHubProject\\ibox-app\\test";
		java.io.File localFile = new java.io.File(fileName);
		
		try {
			localFile.createNewFile();
			if (!FileSyncManager.fileExists("test")) {
				FileSyncManager.addFile(localFile);
			}
			FileSyncManager.deleteFile(localFile);
			assertFalse(FileSyncManager.fileExists("test"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			localFile.delete();
		}
	}
	@Ignore
	@Test 
	public void testUpdateFile() {
		String fileName = "J:\\Document\\GitHubProject\\ibox-app\\test";
		java.io.File localFile = new java.io.File(fileName);
		try {
			localFile.createNewFile();
			if (!FileSyncManager.fileExists("test")) {
				FileSyncManager.addFile(localFile);
			}
			//Grabs the last modified time and will use to compare.
			long before_edit =localFile.lastModified();
			//edits the files so i may compare the modified time
			FileWriter fw = new FileWriter(localFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("Changing file");
			bw.close();

			FileSyncManager.updateFile(localFile);
			assertFalse(before_edit==localFile.lastModified());
			FileSyncManager.deleteFile(localFile);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			localFile.delete();
		}
	}
	

}
