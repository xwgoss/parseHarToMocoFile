package com.xwgoss;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.Har;

public class HarReaderTest {
	private Har har;
	@BeforeClass
	public void init(){
		try {
			har=new HarReader().readFromFile(new File("E:\\hump\\1.har"));
		} catch (HarReaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void test() {
		
	}

}
