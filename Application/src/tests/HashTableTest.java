import static org.junit.Assert.*;

import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.junit.Before;
import org.junit.Test;



public class HashTableTest {
	
	@Test
	public void test() {
		HashRouters<String,String> hash = new HashRouters<>();
		
		hash.addElement("1", "-90");
		hash.addElement("2", "-90");
		hash.addElement("1", "-86");
		hash.addElement("4", "-30");
		hash.addElement("1", "-90");
		
		ArrayList gets = hash.getKBest("1", 2, (x,y) -> {
			int xi = Integer.valueOf(x);
			int yi = Integer.valueOf(y);
			return yi-xi;
		});
		
		assertEquals(2,gets.size());
		assertEquals("-86", gets.get(0));
		assertEquals("-90", gets.get(1));
		System.out.println("Gets ->" + gets);
	}
}
